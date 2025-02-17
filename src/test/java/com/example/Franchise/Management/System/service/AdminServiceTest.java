//package com.example.Franchise.Management.System.service;
//
//import com.example.Franchise.Management.System.dao.PurchaseRepository;
//import com.example.Franchise.Management.System.dao.RequestRepository;
//import com.example.Franchise.Management.System.dao.SupplyRepository;
//import com.example.Franchise.Management.System.dao.UserRepository;
//import com.example.Franchise.Management.System.dto.Request;
//import com.example.Franchise.Management.System.dto.User;
//import com.example.Franchise.Management.System.enums.Role;
//import com.example.Franchise.Management.System.enums.Status;
//import com.example.Franchise.Management.System.exception.UnauthorizedException;
//import com.example.Franchise.Management.System.exception.UserNotFoundException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class AdminServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private Authenticate authenticate;
//
//    @Mock
//    private RequestRepository requestRepository;
//
//    @Mock
//    private SupplyRepository supplyRepository;
//
//    @Mock
//    private PurchaseRepository purchaseRepository;
//
//    @Mock
//    private TokenHandler tokenHandler;
//
//    @InjectMocks
//    private AdminService adminService;
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
//        storedAdmin.setRole(Role.ADMIN);
//
//        when(userRepository.getUserById("TEMP001")).thenReturn(storedAdmin);
//        when(authenticate.checkPassword("adminPassword", "encoded_adminPassword")).thenReturn(true);
//
//        boolean result = adminService.authenticateAdmin(admin);
//
//        verify(userRepository).getUserById("TEMP001");
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
//        when(userRepository.getUserById("TEMP001")).thenReturn(null);
//
//        Exception exception = assertThrows(UserNotFoundException.class, () -> adminService.authenticateAdmin(admin));
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
//        when(userRepository.getUserById("TEMP001")).thenReturn(storedAdmin);
////        when(authenticate.checkPassword("adminPassword", "encoded_adminPassword")).thenReturn(true);
//
//        Exception exception = assertThrows(UnauthorizedException.class, () -> adminService.authenticateAdmin(admin));
//
//        String actualMessage = "User: TEMP001 is not a Admin";
//        assertEquals(actualMessage, exception.getMessage());
//    }
//
//    @Test
//    void testAddEmployee_Success() {
//        User employee = new User();
//        employee.setUserId("TEMP001");
//        employee.setRole(Role.EMPLOYEE);
//        employee.setPassword("newEmployeePassword");
//
//        User admin = new User();
//        admin.setUserId("TEMP001");
//        admin.setRole(Role.ADMIN);
//        admin.setFranchiseId(1);
//
//        when(tokenHandler.extractUserId("token")).thenReturn("TEMP001");
//        when(userRepository.getUserById("TEMP001")).thenReturn(admin);
//
//        when(userRepository.addUser(employee)).thenReturn(true);
//
//        boolean result = adminService.addEmployee(employee,"token");
//
//        verify(userRepository).addUser(employee);
//        verify(authenticate).encodePassword("newEmployeePassword");
//
//        assertTrue(result);
//    }
//
//
//    @Test
//    void testRemoveEmployee_Success() {
//        String employeeId = "TEMP002";
//
//        when(userRepository.deleteUser(employeeId)).thenReturn(true);
//
//        boolean result = adminService.removeEmployee(employeeId);
//
//        verify(userRepository).deleteUser(employeeId);
//
//        assertTrue(result);
//    }
//
//    @Test
//    void testRemoveEmployee_Failure() {
//        String employeeId = "TEMP002";
//
//        when(userRepository.deleteUser(employeeId)).thenReturn(false);
//
//        boolean result = adminService.removeEmployee(employeeId);
//
//        verify(userRepository).deleteUser(employeeId);
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testAddRequest_Success() {
//        Request request = new Request();
//        request.setStatus(Status.PENDING);
//        User user = new User();
//        user.setUserId("TEMP001");
//        user.setRole(Role.ADMIN);
//        user.setFranchiseId(1);
//        when(requestRepository.addRequest(request)).thenReturn(true);
//        when(tokenHandler.extractUserId("token")).thenReturn("TEMP001");
//        when(userRepository.getUserById("TEMP001")).thenReturn(user);
//        boolean result = adminService.addRequest(request,"token");
//
//        verify(requestRepository).addRequest(request);
//
//        assertTrue(result);
//    }
//
//    @Test
//    void testAddRequest_Failure() {
//        Request request = new Request();
//        request.setStatus(Status.PENDING);
//        User user = new User();
//        user.setUserId("TEMP001");
//        user.setRole(Role.ADMIN);
//        user.setFranchiseId(1);
//        when(requestRepository.addRequest(request)).thenReturn(false);
//        when(tokenHandler.extractUserId("token")).thenReturn("TEMP001");
//        when(userRepository.getUserById("TEMP001")).thenReturn(user);
//        boolean result = adminService.addRequest(request,"token");
//
//        verify(requestRepository).addRequest(request);
//
//        assertFalse(result);
//    }
//}
