package com.example.Franchise.Management.System.aspect;

import com.example.Franchise.Management.System.dto.Franchise;
import com.example.Franchise.Management.System.dto.Product;
import com.example.Franchise.Management.System.dto.Stock;
import com.example.Franchise.Management.System.dto.User;
import com.example.Franchise.Management.System.enums.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SuperAdminLoggingAspect {
    private static final Logger logger = LogManager.getLogger(SuperAdminLoggingAspect.class);

    @Before("execution(* com.example.Franchise.Management.System.service.SuperAdminService.authenticateSuperAdmin(..)) && args(user)")
    public void logUserLoginAttempt(User user) {
        logger.info("Super Admin login attempt::User ID: {}", user.getUserId());
    }

    @AfterReturning("execution(* com.example.Franchise.Management.System.service.SuperAdminService.authenticateSuperAdmin(..))")
    public void logUserLoginSuccess(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        User user = (User) args[0];
        logger.info("Super Admin login successful::User ID: {}", user.getUserId());
    }

    @AfterThrowing(value = "execution(* com.example.Franchise.Management.System.service.SuperAdminService.authenticateSuperAdmin(..)))", throwing = "ex")
    public void logUserLoginFailure(JoinPoint joinPoint, Exception ex) {
        Object[] args = joinPoint.getArgs();
        User user = (User) args[0];
        logger.error("User login failed. User ID: {} Error: {}", user.getUserId(), ex.getMessage());
    }

    @AfterReturning(value = "execution(* com.example.Franchise.Management.System.service.SuperAdminService.addFranchise(..))", returning = "result")
    public void logAddFranchise(JoinPoint joinPoint, boolean result) {
        if (result) {
            Object[] args = joinPoint.getArgs();
            Franchise franchise = (Franchise) args[0];
            logger.info("Added New Franchise::Location: {}::Building Name: {}", franchise.getLocation(), franchise.getBuildingName());
        } else {
            logger.error("Failed To Add New Franchise");
        }
    }

    @AfterReturning(value = "execution(* com.example.Franchise.Management.System.service.SuperAdminService.deleteFranchise(..))", returning = "result")
    public void logDeleteFranchise(JoinPoint joinPoint, boolean result) {
        if (result) {
            Object[] args = joinPoint.getArgs();
            int franchiseId = (int) args[0];
            logger.info("Deleted  Franchise::Franchise ID: {}", franchiseId);
        } else {
            logger.error("Failed To Delete  Franchise");
        }
    }

    @AfterReturning(pointcut = "execution(* com.example.Franchise.Management.System.service.SuperAdminService.addProduct(..))", returning = "result")
    public void logAddProduct(JoinPoint joinPoint, boolean result) {
        if (result) {
            Object[] args = joinPoint.getArgs();
            Product product = (Product) args[0];
            logger.info("Added New Product::Product Name: {}::Company Name: {}",product.getProductName(), product.getProductCompany());
        } else {
            logger.error("Failed To Add  Product");
        }
    }

    @AfterReturning(value = "execution(* com.example.Franchise.Management.System.service.SuperAdminService.addStock(..))", returning = "result")
    public void logAddStock(JoinPoint joinPoint, boolean result) {
        if (result) {
            Object[] args = joinPoint.getArgs();
            Stock stock = (Stock) args[0];
            logger.info("Added Stock::Product ID: {}::Franchise ID: {}::Quantity: {}", stock.getProductId(), stock.getFranchiseId(), stock.getQuantity());
        } else {
            logger.error("Failed To Add  Stock");
        }
    }

    @AfterReturning(value = "execution(* com.example.Franchise.Management.System.service.SuperAdminService.updateRequest(..))", returning = "result")
    public void logUpdateRequest(JoinPoint joinPoint, boolean result) {
        Object[] args = joinPoint.getArgs();
        int requestId = (int) args[0];
        Status status = (Status) args[1];

        if (result && status.equals(Status.ACCEPTED)) {
            logger.info("Accepted Request: {}", requestId);
        } else if (result && status.equals(Status.REJECTED)) {
            logger.info("Rejected Request: {}", requestId);
        } else {
            logger.error("Failed To Update Request");
        }
    }
}
