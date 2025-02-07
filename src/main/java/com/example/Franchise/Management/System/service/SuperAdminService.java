package com.example.Franchise.Management.System.service;

import com.example.Franchise.Management.System.dao.UserRepository;
import com.example.Franchise.Management.System.dto.User;
import com.example.Franchise.Management.System.exception.UnauthorizedException;
import com.example.Franchise.Management.System.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SuperAdminService {
    private final UserRepository userRepository;
    private final Authenticate authenticate;


    @Autowired
    public SuperAdminService(UserRepository userRepository, Authenticate authenticate) {
        this.userRepository = userRepository;
        this.authenticate = authenticate;
    }

    public boolean authenticateSuperAdmin(User user) {
        User existingUser = userRepository.getSuperAdminById(user.getUserId());

        if (existingUser == null) {
            throw new UserNotFoundException("User: " + user.getUserId() + " does not exist");
        }

        if (!existingUser.getRole().name().equals("SADMIN")) {
            throw new UnauthorizedException("User: " + user.getUserId() + " is not a Super Admin");
        }

        return authenticate.checkPassword(user.getPassword(), existingUser.getPassword());
    }

}
