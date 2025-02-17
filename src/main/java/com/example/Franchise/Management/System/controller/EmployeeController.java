package com.example.Franchise.Management.System.controller;

import com.example.Franchise.Management.System.dto.Purchases;
import com.example.Franchise.Management.System.dto.Stock;
import com.example.Franchise.Management.System.dto.User;
import com.example.Franchise.Management.System.enums.Role;
import com.example.Franchise.Management.System.exception.UnauthorizedException;
import com.example.Franchise.Management.System.service.EmployeeService;
import com.example.Franchise.Management.System.service.TokenHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final TokenHandler tokenHandler;

    @Autowired
    public EmployeeController(EmployeeService employeeService, TokenHandler tokenHandler) {
        this.employeeService = employeeService;
        this.tokenHandler = tokenHandler;
    }

    private void validateUser(String token) {
        tokenHandler.validateToken(token);
        if (!tokenHandler.extractRole(token).equals("EMPLOYEE")) {
            throw new UnauthorizedException("User is not a valid Employee");
        }
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> employeeLogin(@RequestBody User user, HttpServletRequest request) {
        Map<String, String> response;
        if (employeeService.authenticateEmployee(user)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            session = request.getSession(true);
            String token = tokenHandler.generateToken(session.getId(), user.getUserId(), Role.EMPLOYEE);

            response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", token);
        } else {
            response = new HashMap<>();
            response.put("message", "Login Unsuccessful, Incorrect user id or password");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("available-stocks")
    public ResponseEntity<Map<String, List<Stock>>> getAllAvailableStocks(@CookieValue("token") String token, @RequestParam("id") int franchiseId) {
        validateUser(token);

        Map<String, List<Stock>> response = new HashMap<>();
        response.put("available-stocks", employeeService.getStocksAvailable(franchiseId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("bill")
    public ResponseEntity<Map<String, String>> billPurchase(@CookieValue("token") String token, @RequestBody List<Purchases> purchases) {
        validateUser(token);

        Map<String, String> response = new HashMap<>();
        String userId = tokenHandler.extractUserId(token);
        purchases.forEach(purchase -> purchase.setUserId(userId));
        if (employeeService.billPurchase(purchases)) {
            response.put("message", "Purchase added successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Failed to add purchase");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("return")
    public ResponseEntity<Map<String, String>> returnPurchase(@CookieValue("token") String token, @RequestBody List<Integer> purchaseIds) {
        validateUser(token);

        Map<String, String> response = new HashMap<>();

        if (employeeService.returnPurchase(purchaseIds)) {
            response.put("message", "Purchase returned successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Failed to return purchase");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
