package com.example.Franchise.Management.System.service;

import com.example.Franchise.Management.System.dao.PurchaseRepository;
import com.example.Franchise.Management.System.dao.StockRepository;
import com.example.Franchise.Management.System.dao.UserRepository;
import com.example.Franchise.Management.System.dto.Purchases;
import com.example.Franchise.Management.System.dto.Stock;
import com.example.Franchise.Management.System.dto.User;
import com.example.Franchise.Management.System.enums.Role;
import com.example.Franchise.Management.System.exception.OutOfStockException;
import com.example.Franchise.Management.System.exception.UnauthorizedException;
import com.example.Franchise.Management.System.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authenticate authenticate;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testAuthenticateEmployee_Success() {
        User employee = new User();
        employee.setUserId("TEMP000");
        employee.setPassword("password123");

        User storedUser = new User();
        storedUser.setUserId("TEMP000");
        storedUser.setPassword("encoded_password");
        storedUser.setRole(Role.valueOf("EMPLOYEE"));

        when(userRepository.getUserById("TEMP000")).thenReturn(storedUser);
        when(authenticate.checkPassword("password123", "encoded_password")).thenReturn(true);

        boolean result = employeeService.authenticateEmployee(employee);

        verify(userRepository).getUserById("TEMP000");
        verify(authenticate).checkPassword("password123", "encoded_password");

        assertTrue(result);
    }

    @Test
    void testAuthenticateEmployee_UserNotFound() {
        User employee = new User();
        employee.setUserId("TEMP000");

        when(userRepository.getUserById("TEMP000")).thenReturn(null);

        Exception exception = assertThrows(UserNotFoundException.class, () -> employeeService.authenticateEmployee(employee));

        assertEquals("User: TEMP000 does not exist", exception.getMessage());
    }

    @Test
    void testAuthenticateEmployee_Unauthorized() {
        User employee = new User();
        employee.setUserId("TEMP000");
        employee.setPassword("password123");

        User storedUser = new User();
        storedUser.setUserId("TEMP000");
        storedUser.setPassword("encoded_password");
        storedUser.setRole(Role.valueOf("ADMIN"));

        when(userRepository.getUserById("TEMP000")).thenReturn(storedUser);
//        when(authenticate.checkPassword("password123", "encoded_password")).thenReturn(true);

        Exception exception = assertThrows(UnauthorizedException.class, () -> employeeService.authenticateEmployee(employee));

        assertEquals("User: TEMP000 is not an employee", exception.getMessage());
    }

    @Test
    void testGetStocksAvailable_Success() {
        int franchiseId = 1;
        Stock stock = new Stock();
        stock.setProductId(1);
        stock.setProductName("Product A");
        stock.setQuantity(10);

        when(stockRepository.getAllStock(franchiseId)).thenReturn(Collections.singletonList(stock));

        var stocks = employeeService.getStocksAvailable(franchiseId);

        verify(stockRepository).getAllStock(franchiseId);
        assertEquals(1, stocks.size());
        assertEquals("Product A", stocks.get(0).getProductName());
    }

    @Test
    void testBillPurchase_Success() {
        Purchases purchase = new Purchases();
        purchase.setUserId("TEMP000");
        purchase.setProductId(1);
        purchase.setQuantity(2);

        User employee = new User();
        employee.setUserId("TEMP000");
        employee.setFranchiseId(1);

        Stock stock = new Stock();
        stock.setProductId(1);
        stock.setFranchiseId(1);
        stock.setQuantity(10);

        when(userRepository.getUserById("TEMP000")).thenReturn(employee);
        when(stockRepository.getStockById(1, 1)).thenReturn(stock);
        when(purchaseRepository.addPurchase(purchase)).thenReturn(true);
        when(stockRepository.addOrUpdateStock(stock)).thenReturn(true);

        boolean result = employeeService.billPurchase(purchase);

        verify(userRepository).getUserById("TEMP000");
        verify(stockRepository).getStockById(1, 1);
        verify(purchaseRepository).addPurchase(purchase);
        verify(stockRepository).addOrUpdateStock(stock);

        assertTrue(result);
        assertEquals(8, stock.getQuantity());  // Stock quantity should be reduced by 2
    }

    @Test
    void testBillPurchase_OutOfStock() {
        Purchases purchase = new Purchases();
        purchase.setUserId("TEMP000");
        purchase.setProductId(1);
        purchase.setQuantity(20);  // Attempting to purchase more than available stock

        User employee = new User();
        employee.setUserId("TEMP000");
        employee.setFranchiseId(1);

        Stock stock = new Stock();
        stock.setProductId(1);
        stock.setFranchiseId(1);
        stock.setQuantity(10);  // Only 10 in stock

        when(userRepository.getUserById("TEMP000")).thenReturn(employee);
        when(stockRepository.getStockById(1, 1)).thenReturn(stock);

        Exception exception = assertThrows(OutOfStockException.class, () -> employeeService.billPurchase(purchase));

        assertEquals("Out of stock", exception.getMessage());
    }

    @Test
    void testBillPurchase_ProductNotFound() {
        Purchases purchase = new Purchases();
        purchase.setUserId("TEMP000");
        purchase.setProductId(1);
        purchase.setQuantity(2);

        User employee = new User();
        employee.setUserId("TEMP000");
        employee.setFranchiseId(1);

        when(userRepository.getUserById("TEMP000")).thenReturn(employee);
        when(stockRepository.getStockById(1, 1)).thenReturn(null);  // Product not found

        Exception exception = assertThrows(RuntimeException.class, () -> employeeService.billPurchase(purchase));

        assertEquals("Stock does not exist for product", exception.getMessage());
    }

    @Test
    void testBillPurchase_UserNotFound() {
        Purchases purchase = new Purchases();
        purchase.setUserId("TEMP000");
        purchase.setProductId(1);
        purchase.setQuantity(2);

        when(userRepository.getUserById("TEMP000")).thenReturn(null);  // User not found

        Exception exception = assertThrows(RuntimeException.class, () -> employeeService.billPurchase(purchase));

        assertEquals("No employee: TEMP000 exist", exception.getMessage());
    }
}
