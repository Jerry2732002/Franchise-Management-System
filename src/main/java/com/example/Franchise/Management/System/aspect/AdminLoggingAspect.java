package com.example.Franchise.Management.System.aspect;

import com.example.Franchise.Management.System.dto.Request;
import com.example.Franchise.Management.System.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminLoggingAspect {
    private static final Logger logger = LogManager.getLogger(AdminLoggingAspect.class);

    @Before("execution(* com.example.Franchise.Management.System.service.AdminService.authenticateAdmin(..)) && args(user)")
    public void logUserLoginAttempt(User user) {
        logger.info("Admin login attempt::User ID: {}", user.getUserId());
    }

    @AfterReturning("execution(* com.example.Franchise.Management.System.service.AdminService.authenticateAdmin(..))")
    public void logUserLoginSuccess(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        User user = (User) args[0];
        logger.info("Admin login successful::User ID: {}", user.getUserId());
    }

    @AfterThrowing(pointcut = "execution(com.example.Franchise.Management.System.service.AdminService.authenticateAdmin(..)) && args(user))", throwing = "ex")
    public void logUserLoginFailure(JoinPoint joinPoint, Exception ex) {
        Object[] args = joinPoint.getArgs();
        User user = (User) args[0];
        logger.error("User login failed::User ID: {}::Error: {}", user.getUserId(), ex.getMessage());

    }

    @AfterReturning(pointcut = "execution(com.example.Franchise.Management.System.service.AdminService.addEmployee(..))", returning = "result")
    public void logAddEmployee(JoinPoint joinPoint, boolean result) {
        if (result) {
            Object[] args = joinPoint.getArgs();
            User employee = (User) args[0];
            logger.info("Added New Employee::Employee ID: {}::Full Name: {}::FranchiseID: {}", employee.getUserId(), employee.getName(), employee.getFranchiseId());
        } else {
            logger.error("Failed To Add New Employee");
        }
    }

    @AfterReturning(pointcut = "execution(com.example.Franchise.Management.System.service.AdminService.removeEmployee(..))", returning = "result")
    public void logDeleteEmployee(JoinPoint joinPoint, boolean result) {
        if (result) {
            Object[] args = joinPoint.getArgs();
            int employeeId = (int) args[0];
            logger.info("Removed  Employee::Employee ID: {}", employeeId);
        } else {
            logger.error("Failed To Delete  Employee");
        }
    }

    @AfterReturning(pointcut = "execution(com.example.Franchise.Management.System.service.AdminService.addRequest(..))", returning = "result")
    public void logAddRequest(JoinPoint joinPoint, boolean result) {
        if (result) {
            Object[] args = joinPoint.getArgs();
            Request request = (Request) args[0];
            logger.info("Added New Employee::Product ID: {}::Franchise ID: {}::Quantity: {}", request.getProductId(), request.getFranchiseId(), request.getQuantity());
        } else {
            logger.error("Failed To Add New Request");
        }
    }
}
