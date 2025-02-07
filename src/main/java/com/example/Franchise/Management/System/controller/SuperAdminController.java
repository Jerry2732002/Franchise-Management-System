package com.example.Franchise.Management.System.controller;

import com.example.Franchise.Management.System.dto.User;
import com.example.Franchise.Management.System.enums.Role;
import com.example.Franchise.Management.System.service.SuperAdminService;
import com.example.Franchise.Management.System.service.TokenHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("super_admin")
public class SuperAdminController {
    private final SuperAdminService superAdminService;
    private final TokenHandler tokenHandler;

    @Autowired
    public SuperAdminController(SuperAdminService superAdminService, TokenHandler tokenHandler) {
        this.superAdminService = superAdminService;
        this.tokenHandler = tokenHandler;
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
            response.put("message","Login Unsuccessful");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
