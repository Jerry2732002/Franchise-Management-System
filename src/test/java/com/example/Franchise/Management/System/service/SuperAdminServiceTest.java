//package com.example.Franchise.Management.System.service;
//
//import com.example.Franchise.Management.System.dao.*;
//import com.example.Franchise.Management.System.dto.*;
//import com.example.Franchise.Management.System.enums.Role;
//import com.example.Franchise.Management.System.enums.Status;
//import com.example.Franchise.Management.System.exception.OutOfStockException;
//import com.example.Franchise.Management.System.exception.UnauthorizedException;
//import com.example.Franchise.Management.System.exception.UserNotFoundException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//
//@ExtendWith(MockitoExtension.class)
//public class SuperAdminServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private Authenticate authenticate;
//    @Mock
//    private FranchiseRepository franchiseRepository;
//    @Mock
//    private ProductsRepository productsRepository;
//    @Mock
//    private StockRepository stockRepository;
//    @Mock
//    private RequestRepository requestRepository;
//    @Mock
//    private SupplyRepository supplyRepository;
//    @Mock
//    private CompanyPurchaseRepository companyPurchaseRepository;
//    @Mock
//    private CompanyStockRepository companyStockRepository;
//
//    @InjectMocks
//    private SuperAdminService superAdminService;
//
//    @Test
//    void testAuthenticateAdmin_Success() {
//        User admin = new User();
//        admin.setUserId("TEMP001");
//        admin.setPassword("adminPassword");
//
//        User storedAdmin = new User();
//        storedAdmin.setUserId("TEMP001");
//        storedAdmin.setPassword("encoded_adminPassword");
//        storedAdmin.setRole(Role.SADMIN);
//
//        when(userRepository.getSuperAdminById("TEMP001")).thenReturn(storedAdmin);
//        when(authenticate.checkPassword("adminPassword", "encoded_adminPassword")).thenReturn(true);
//
//        boolean result = superAdminService.authenticateSuperAdmin(admin);
//
//        verify(userRepository).getSuperAdminById("TEMP001");
//        verify(authenticate).checkPassword("adminPassword", "encoded_adminPassword");
//
//        assertTrue(result);
//    }
//
//    @Test
//    void testAuthenticateAdmin_UserNotFound() {
//        User admin = new User();
//        admin.setUserId("TEMP001");
//
//        when(userRepository.getSuperAdminById("TEMP001")).thenReturn(null);
//
//        Exception exception = assertThrows(UserNotFoundException.class, () -> superAdminService.authenticateSuperAdmin(admin));
//
//        String actualMessage = "User: TEMP001 does not exist";
//        assertEquals(actualMessage, exception.getMessage());
//    }
//
//    @Test
//    void testAuthenticateAdmin_Unauthorized() {
//        User admin = new User();
//        admin.setUserId("TEMP001");
//        admin.setPassword("adminPassword");
//
//        User storedAdmin = new User();
//        storedAdmin.setUserId("TEMP001");
//        storedAdmin.setPassword("encoded_adminPassword");
//        storedAdmin.setRole(Role.EMPLOYEE);
//
//        when(userRepository.getSuperAdminById("TEMP001")).thenReturn(storedAdmin);
//
//
//        Exception exception = assertThrows(UnauthorizedException.class, () -> superAdminService.authenticateSuperAdmin(admin));
//
//        String actualMessage = "User: TEMP001 is not a Super Admin";
//        assertEquals(actualMessage, exception.getMessage());
//    }
//
//    @Test
//    void testAddAdmin_Success() {
//        User admin = new User();
//        admin.setUserId("TEMP001");
//        admin.setRole(Role.ADMIN);
//        admin.setPassword("password");
//
//        when(userRepository.addUser(admin)).thenReturn(true);
//
//        boolean result = superAdminService.addAdmin(admin);
//
//        verify(userRepository).addUser(admin);
//        verify(authenticate).encodePassword("password");
//
//        assertTrue(result);
//    }
//
//    @Test
//    void testAddAdmin_Fail() {
//        User admin = new User();
//        admin.setUserId("TEMP001");
//        admin.setRole(Role.ADMIN);
//        admin.setPassword("password");
//
//        when(userRepository.addUser(admin)).thenReturn(false);
//
//        boolean result = superAdminService.addAdmin(admin);
//
//        verify(userRepository).addUser(admin);
//        verify(authenticate).encodePassword("password");
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testAddFranchise_Success() {
//        Franchise franchise = new Franchise(1, "TestLocation", "TestBuilding");
//
//        when(franchiseRepository.addFranchise(franchise)).thenReturn(true);
//
//        boolean result = superAdminService.addFranchise(franchise);
//
//        verify(franchiseRepository).addFranchise(franchise);
//
//        assertTrue(result);
//    }
//
//    @Test
//    void testAddFranchise_Fail() {
//        Franchise franchise = new Franchise(1, "TestLocation", "TestBuilding");
//
//        when(franchiseRepository.addFranchise(franchise)).thenReturn(false);
//
//        boolean result = superAdminService.addFranchise(franchise);
//
//        verify(franchiseRepository).addFranchise(franchise);
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testDeleteFranchise_Success() {
//
//
//        when(franchiseRepository.deleteFranchise(1)).thenReturn(true);
//
//        boolean result = superAdminService.deleteFranchise(1);
//
//        verify(franchiseRepository).deleteFranchise(1);
//
//        assertTrue(result);
//    }
//
//    @Test
//    void testDeleteFranchise_Fail() {
//
//
//        when(franchiseRepository.deleteFranchise(1)).thenReturn(false);
//
//        boolean result = superAdminService.deleteFranchise(1);
//
//        verify(franchiseRepository).deleteFranchise(1);
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testGetAllFranchise_Success() {
//        List<Franchise> franchiseList = List.of(
//                new Franchise(1, "TestLocation1", "TestBuilding1"),
//                new Franchise(2, "TestLocation2", "TestBuilding2"),
//                new Franchise(3, "TestLocation3", "TestBuilding3"));
//
//        when(franchiseRepository.getAllFranchise()).thenReturn(franchiseList);
//
//        List<Franchise> result = superAdminService.getAllFranchise();
//
//        verify(franchiseRepository).getAllFranchise();
//
//        assertEquals(result, franchiseList);
//    }
//
//    @Test
//    void testAddProduct_Success() {
//        Product product = new Product(0, "TestProduct", "TestCompany", 100, 120, 150);
//
//        when(productsRepository.addProduct(product)).thenReturn(true);
//
//        boolean result = superAdminService.addProduct(product);
//
//        verify(productsRepository).addProduct(product);
//
//        assertTrue(result);
//    }
//
//    @Test
//    void testAddProduct_Fail() {
//        Product product = new Product(0, "TestProduct", "TestCompany", 100, 120, 150);
//
//        when(productsRepository.addProduct(product)).thenReturn(false);
//
//        boolean result = superAdminService.addProduct(product);
//
//        verify(productsRepository).addProduct(product);
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testGetAllProduct_Success() {
//        List<Product> products = List.of(
//                new Product(0, "TestProduct1", "TestCompany1", 100, 120, 150),
//                new Product(0, "TestProduct2", "TestCompany2", 100, 120, 150),
//                new Product(0, "TestProduct3", "TestCompany3", 100, 120, 150));
//
//        when(productsRepository.getAllProducts()).thenReturn(products);
//
//        List<Product> result = superAdminService.getAllProduct();
//
//        verify(productsRepository).getAllProducts();
//
//        assertEquals(result, products);
//    }
//
//    @Test
//    void testAddStock_Success() {
//        Stock stock = new Stock(1, 1, 100);
//
//        when(stockRepository.addOrUpdateStock(stock)).thenReturn(true);
//        when(stockRepository.getStockById(1, 1)).thenReturn(null);
//        boolean result = superAdminService.addStock(stock);
//
//        verify(stockRepository).addOrUpdateStock(stock);
//        verify(stockRepository).getStockById(1, 1);
//
//        assertTrue(result);
//    }
//
//    @Test
//    void testAddStock_Fail() {
//        Stock stock = new Stock(1, 1, 100);
//
//        when(stockRepository.addOrUpdateStock(stock)).thenReturn(false);
//        when(stockRepository.getStockById(1, 1)).thenReturn(null);
//        boolean result = superAdminService.addStock(stock);
//
//        verify(stockRepository).addOrUpdateStock(stock);
//        verify(stockRepository).getStockById(1, 1);
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testUpdateRequest_Success() {
//        Request request = new Request(1, 1, 10, Status.PENDING);
//        when(requestRepository.getRequestById(1)).thenReturn(request);
//        when(companyStockRepository.getCompanyStockById(1)).thenReturn(new CompanyStock(1, 100));
//        when(stockRepository.getStockById(1, 1)).thenReturn(null);
//        when(requestRepository.updateRequestStatus(1, Status.ACCEPTED)).thenReturn(true);
//        when(stockRepository.addOrUpdateStock(any(Stock.class))).thenReturn(true);
//        when(supplyRepository.addSupply(any(Supply.class))).thenReturn(true);
//        when(companyStockRepository.addOrUpdateCompanyStock(any(CompanyStock.class))).thenReturn(true);
//
//        boolean result = superAdminService.updateRequest(1, Status.ACCEPTED);
//
//        verify(requestRepository).getRequestById(1);
//        verify(companyStockRepository).getCompanyStockById(1);
//        verify(stockRepository).addOrUpdateStock(any(Stock.class));
//        verify(supplyRepository).addSupply(any(Supply.class));
//        verify(companyStockRepository).addOrUpdateCompanyStock(any(CompanyStock.class));
//
//        assertTrue(result);
//    }
//
//    @Test
//    void testUpdateRequest_OutOfStock() {
//        Request request = new Request(1, 1, 10, Status.PENDING);
//        when(requestRepository.getRequestById(1)).thenReturn(request);
//        when(companyStockRepository.getCompanyStockById(1)).thenReturn(new CompanyStock(1, 5));
//
//        Exception exception = assertThrows(OutOfStockException.class, () -> superAdminService.updateRequest(1, Status.ACCEPTED));
//
//        assertEquals("Out Of Stock. Available stock: 5", exception.getMessage());
//    }
//
//}
//
//
//
