package com.example.Franchise.Management.System.service;

import com.example.Franchise.Management.System.dao.RequestRepository;
import com.example.Franchise.Management.System.dao.UserRepository;
import com.example.Franchise.Management.System.dto.Request;
import com.example.Franchise.Management.System.dto.User;
import com.example.Franchise.Management.System.enums.Role;
import com.example.Franchise.Management.System.enums.Status;
import com.example.Franchise.Management.System.exception.UnauthorizedException;
import com.example.Franchise.Management.System.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final Authenticate authenticate;
    private final RequestRepository requestRepository;

    @Autowired
    public AdminService(UserRepository userRepository, Authenticate authenticate, RequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.authenticate = authenticate;
        this.requestRepository = requestRepository;
    }

    public boolean authenticateAdmin(User user) {
        User existingUser = userRepository.getUserById(user.getUserId());

        if (existingUser == null) {
            throw new UserNotFoundException("User: " + user.getUserId() + " does not exist");
        }

        if (!existingUser.getRole().name().equals("ADMIN")) {
            throw new UnauthorizedException("User: " + user.getUserId() + " is not a Admin");
        }

        return authenticate.checkPassword(user.getPassword(), existingUser.getPassword());
    }

    public boolean addEmployee(User employee) {
        if (!employee.getRole().equals(Role.EMPLOYEE)) {
            throw new RuntimeException("Admin can only add employees");
        }
        employee.setPassword(authenticate.encodePassword(employee.getPassword()));
        return userRepository.addUser(employee);
    }

    public boolean removeEmployee(String employeeId) {
        return userRepository.deleteUser(employeeId);
    }

    public boolean addRequest(Request request) {
        request.setStatus(Status.PENDING);
        return requestRepository.addRequest(request);
    }
}
