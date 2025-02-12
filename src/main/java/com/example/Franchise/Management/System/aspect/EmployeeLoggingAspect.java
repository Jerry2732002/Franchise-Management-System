package com.example.Franchise.Management.System.aspect;

import com.example.Franchise.Management.System.dto.Purchases;
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
public class EmployeeLoggingAspect {

    private static final Logger logger = LogManager.getLogger(EmployeeLoggingAspect.class);

    @Before("execution(* com.example.Franchise.Management.System.service.EmployeeService.authenticateEmployee(..)) && args(user)")
    public void logUserLoginAttempt(User user) {
        logger.info("Admin login attempt::User ID: {}", user.getUserId());
    }

    @AfterReturning("execution(* com.example.Franchise.Management.System.service.EmployeeService.authenticateEmployee(..))")
    public void logUserLoginSuccess(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        User user = (User) args[0];
        logger.info("Admin login successful::User ID: {}", user.getUserId());
    }

    @AfterThrowing(pointcut = "execution(com.example.Franchise.Management.System.service.EmployeeService.authenticateEmployee(..)))", throwing = "ex")
    public void logUserLoginFailure(JoinPoint joinPoint, Exception ex) {
        Object[] args = joinPoint.getArgs();
        User user = (User) args[0];
        logger.error("User login failed::User ID: {}::Error: {}", user.getUserId(), ex.getMessage());
    }

    @AfterReturning(pointcut = "execution(com.example.Franchise.Management.System.service.EmployeeService.billPurchase(..))", returning = "result")
    public void logAddEmployee(JoinPoint joinPoint, boolean result) {
        if (result) {
            Object[] args = joinPoint.getArgs();
            Purchases purchases = (Purchases) args[0];
            logger.info("Added New Purchase::Product ID: {}::Quantity: {}", purchases.getProductId(), purchases.getQuantity());
        } else {
            logger.error("Failed To Add New Purchase");
        }
    }
}
