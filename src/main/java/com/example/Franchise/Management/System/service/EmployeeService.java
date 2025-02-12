package com.example.Franchise.Management.System.service;

import com.example.Franchise.Management.System.dao.PurchaseRepository;
import com.example.Franchise.Management.System.dao.StockRepository;
import com.example.Franchise.Management.System.dao.UserRepository;
import com.example.Franchise.Management.System.dto.Purchases;
import com.example.Franchise.Management.System.dto.Stock;
import com.example.Franchise.Management.System.dto.User;
import com.example.Franchise.Management.System.exception.OutOfStockException;
import com.example.Franchise.Management.System.exception.UnauthorizedException;
import com.example.Franchise.Management.System.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class EmployeeService {

    private final UserRepository userRepository;
    private final Authenticate authenticate;
    private final StockRepository stockRepository;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public EmployeeService(UserRepository userRepository, Authenticate authenticate, StockRepository stockRepository, PurchaseRepository purchaseRepository) {
        this.userRepository = userRepository;
        this.authenticate = authenticate;
        this.stockRepository = stockRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public boolean authenticateEmployee(User employee) {
        User existingUser = userRepository.getUserById(employee.getUserId());

        if (existingUser == null) {
            throw new UserNotFoundException("User: " + employee.getUserId() + " does not exist");
        }

        if (!existingUser.getRole().name().equals("EMPLOYEE")) {
            throw new UnauthorizedException("User: " + employee.getUserId() + " is not an employee");
        }

        return authenticate.checkPassword(employee.getPassword(), existingUser.getPassword());
    }

    public List<Stock> getStocksAvailable(int franchiseId) {
        return stockRepository.getAllStock(franchiseId);
    }

    public boolean billPurchase(Purchases purchase) {
        purchase.setDateOfPurchase(new Date(System.currentTimeMillis()));
        User employee = userRepository.getUserById(purchase.getUserId());

        if (employee == null) {
            throw new RuntimeException("No employee: " + purchase.getUserId() + "exist");
        }

        Stock stock = stockRepository.getStockById(purchase.getProductId(), employee.getFranchiseId());

        if (stock == null) {
            throw new RuntimeException("Stock does not exist for product");
        }

        if (stock.getQuantity() < purchase.getQuantity()) {
            throw new OutOfStockException("Out of stock");
        }

        stock.setQuantity(stock.getQuantity() - purchase.getQuantity());
        stockRepository.addOrUpdateStock(stock);

        return purchaseRepository.addPurchase(purchase);
    }
}
