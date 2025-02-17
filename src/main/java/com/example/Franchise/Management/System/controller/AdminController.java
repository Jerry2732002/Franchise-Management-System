package com.example.Franchise.Management.System.controller;

import com.example.Franchise.Management.System.dto.Request;
import com.example.Franchise.Management.System.dto.User;
import com.example.Franchise.Management.System.enums.Role;
import com.example.Franchise.Management.System.exception.UnauthorizedException;
import com.example.Franchise.Management.System.service.AdminService;
import com.example.Franchise.Management.System.service.TokenHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final AdminService adminService;
    private final TokenHandler tokenHandler;

    public AdminController(AdminService adminService, TokenHandler tokenHandler) {
        this.adminService = adminService;
        this.tokenHandler = tokenHandler;
    }

    private void validateUser(String token) {
        tokenHandler.validateToken(token);
        if (!tokenHandler.extractRole(token).equals("ADMIN")) {
            throw new UnauthorizedException("User is not a valid Admin");
        }
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> adminLogin(@RequestBody User user, HttpServletRequest request) {
        Map<String, String> response;
        if (adminService.authenticateAdmin(user)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            session = request.getSession(true);
            String token = tokenHandler.generateToken(session.getId(), user.getUserId(), Role.ADMIN);

            response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new HashMap<>();
            response.put("message", "Login Unsuccessful, Incorrect user id or password");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("register-employee")
    public ResponseEntity<Map<String, String>> addEmployee(@RequestBody User employee, @CookieValue("token") String token) {
        validateUser(token);

        Map<String, String> response = new HashMap<>();

        if (adminService.addEmployee(employee, token)) {
            response.put("message", "Employee added successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Failed to add employee");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("remove-employee")
    public ResponseEntity<Map<String, String>> removeEmployee(@CookieValue("token") String token, @RequestParam("employeeId") String employeeId) {
        validateUser(token);

        Map<String, String> response = new HashMap<>();

        if (adminService.removeEmployee(employeeId)) {
            response.put("message", "Employee deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Failed to remove employee");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("request-stock")
    public ResponseEntity<Map<String, String>> addRequest(@CookieValue("token") String token, @RequestBody Request request) {
        validateUser(token);

        Map<String, String> response = new HashMap<>();

        if (adminService.addRequest(request, token)) {
            response.put("message", "Request added successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Failed to add request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("download-franchise-report")
    public ResponseEntity<byte[]> downloadSupplyReport(@CookieValue("token") String token, @RequestParam("start") Date startDate, @RequestParam("end") Date endDate, @RequestParam("id") int franchiseId) throws IOException {
        validateUser(token);
        byte[] excelFile = adminService.generateFranchiseReport(startDate, endDate, franchiseId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=franchise_report.xlsx");

        return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
    }
}
