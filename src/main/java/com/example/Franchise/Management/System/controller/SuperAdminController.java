package com.example.Franchise.Management.System.controller;

import com.example.Franchise.Management.System.dto.*;
import com.example.Franchise.Management.System.enums.Role;
import com.example.Franchise.Management.System.enums.Status;
import com.example.Franchise.Management.System.exception.UnauthorizedException;
import com.example.Franchise.Management.System.service.SuperAdminService;
import com.example.Franchise.Management.System.service.TokenHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("super-admin")
public class SuperAdminController {
    private final SuperAdminService superAdminService;
    private final TokenHandler tokenHandler;

    @Autowired
    public SuperAdminController(SuperAdminService superAdminService, TokenHandler tokenHandler) {
        this.superAdminService = superAdminService;
        this.tokenHandler = tokenHandler;
    }

    private void validateUser(String token) {
        tokenHandler.validateToken(token);
        if(!tokenHandler.extractRole(token).equals("SADMIN")) {
            throw new UnauthorizedException("User is not a valid Super Admin");
        }
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> superAdminLogin(@RequestBody User user, HttpServletRequest request) {
        Map<String, String> response;
        if (superAdminService.authenticateSuperAdmin(user)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            session = request.getSession(true);
            String token = tokenHandler.generateToken(session.getId(), user.getUserId(), Role.SADMIN);

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

    @PostMapping("register-user")
    public ResponseEntity<Map<String, String>> addAdmin(@RequestBody User admin, @CookieValue("token") String token) {
        validateUser(token);

        Map<String, String> response = new HashMap<>();

        if (superAdminService.addAdmin(admin)) {
            response.put("message", "Admin added successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Failed to add admin");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("add-franchise")
    public ResponseEntity<Map<String, String>> addFranchise(@RequestBody Franchise franchise, @CookieValue("token") String token) {
        validateUser(token);

        Map<String, String> response = new HashMap<>();

        if (superAdminService.addFranchise(franchise)) {
            response.put("message", "Franchise added successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Failed to add franchise");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("list-franchise")
    public ResponseEntity<Map<String, List<Franchise>>> getAllFranchise(@CookieValue("token") String token) {
        validateUser(token);

        Map<String, List<Franchise>> response = new HashMap<>();
        response.put("franchises", superAdminService.getAllFranchise());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("remove-franchise")
    public ResponseEntity<Map<String, String>> deleteFranchise(@CookieValue("token") String token, @RequestParam("id") int franchiseId) {
        validateUser(token);

        Map<String, String> response = new HashMap<>();

        if (superAdminService.deleteFranchise(franchiseId)) {
            response.put("message", "Franchise deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Failed to delete franchise");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("add-product")
    public ResponseEntity<Map<String, String>> addProduct(@CookieValue("token") String token, @RequestBody Product product) {
        validateUser(token);

        Map<String, String> response = new HashMap<>();

        if (superAdminService.addProduct(product)) {
            response.put("message", "Product added successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Failed to add product");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("list-product")
    public ResponseEntity<Map<String, List<Product>>> getAllProduct(@CookieValue("token") String token) {
        validateUser(token);

        Map<String, List<Product>> response = new HashMap<>();
        response.put("products", superAdminService.getAllProduct());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("add-stock")
    public ResponseEntity<Map<String, String>> addStock(@CookieValue("token") String token, @RequestBody Stock stock) {
        validateUser(token);

        Map<String, String> response = new HashMap<>();

        if (superAdminService.addStock(stock)) {
            response.put("message", "Stock added successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Failed to add stock");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("list-company-stock")
    public ResponseEntity<Map<String, List<CompanyStock>>> listStock(@CookieValue("token") String token) {
        validateUser(token);

        Map<String, List<CompanyStock>> response = new HashMap<>();
        response.put("company-stock",superAdminService.getAllCompanyStock());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("view-request")
    public ResponseEntity<Map<String, List<Request>>> getAllRequest(@CookieValue("token") String token, @RequestParam("status") String status) {
        validateUser(token);

        Map<String, List<Request>> response = new HashMap<>();
        response.put("requests", superAdminService.getAllRequest(status.toUpperCase()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("reject-request")
    public ResponseEntity<Map<String, String>> rejectRequest(@CookieValue("token") String token, @RequestParam("id") int requestId) {
        validateUser(token);

        Map<String, String> response = new HashMap<>();

        if (superAdminService.updateRequest(requestId, Status.REJECTED)) {
            response.put("message", "Request updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Failed to update request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("accept-request")
    public ResponseEntity<Map<String, String>> acceptRequest(@CookieValue("token") String token, @RequestParam("id") int requestId) {
        validateUser(token);

        Map<String, String> response = new HashMap<>();

        if (superAdminService.updateRequest(requestId, Status.ACCEPTED)) {
            response.put("message", "Request updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Failed to update request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("company-purchase")
    public ResponseEntity<Map<String, String>> companyPurchase(@CookieValue("token") String token, @RequestParam("id") int productId, @RequestParam("quantity") int quantity) {
        validateUser(token);

        Map<String, String> response = new HashMap<>();
        if (superAdminService.addCompanyPurchase(productId, quantity)) {
            response.put("message", "Company Purchase added successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Failed to add company purchase");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("download-company-report")
    public ResponseEntity<byte[]> downloadSupplyReport(@CookieValue("token") String token, @RequestParam("start") Date startDate, @RequestParam("end") Date endDate) throws IOException {
        validateUser(token);

        byte[] excelFile = superAdminService.generateCompanyReport(startDate, endDate);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=company_report.xlsx");

        return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
    }

    @GetMapping("download-franchise-report")
    public ResponseEntity<byte[]> downloadFranchiseReport(@CookieValue("token") String token, @RequestParam("start") Date startDate, @RequestParam("end") Date endDate) throws IOException {
        validateUser(token);

        byte[] excelFile = superAdminService.generateFranchiseReport(startDate, endDate);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=company_report.xlsx");

        return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
    }

}
