package com.example.Franchise.Management.System.service;

import com.example.Franchise.Management.System.dao.*;
import com.example.Franchise.Management.System.dto.*;
import com.example.Franchise.Management.System.enums.Status;
import com.example.Franchise.Management.System.exception.OutOfStockException;
import com.example.Franchise.Management.System.exception.UnauthorizedException;
import com.example.Franchise.Management.System.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class SuperAdminService {
    private final UserRepository userRepository;
    private final Authenticate authenticate;
    private final FranchiseRepository franchiseRepository;
    private final ProductsRepository productsRepository;
    private final StockRepository stockRepository;
    private final RequestRepository requestRepository;
    private final SupplyRepository supplyRepository;
    private final CompanyPurchaseRepository companyPurchaseRepository;
    private final CompanyStockRepository companyStockRepository;


    @Autowired
    public SuperAdminService(UserRepository userRepository, Authenticate authenticate, FranchiseRepository franchiseRepository, ProductsRepository productsRepository, StockRepository stockRepository, RequestRepository requestRepository, SupplyRepository supplyRepository, CompanyPurchaseRepository companyPurchaseRepository, CompanyStockRepository companyStockRepository) {
        this.userRepository = userRepository;
        this.authenticate = authenticate;
        this.franchiseRepository = franchiseRepository;
        this.productsRepository = productsRepository;
        this.stockRepository = stockRepository;
        this.requestRepository = requestRepository;
        this.supplyRepository = supplyRepository;
        this.companyPurchaseRepository = companyPurchaseRepository;
        this.companyStockRepository = companyStockRepository;
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

    public boolean addAdmin(User admin) {
        admin.setPassword(authenticate.encodePassword(admin.getPassword()));
        return userRepository.addUser(admin);
    }

    public boolean addFranchise(Franchise franchise) {
        return franchiseRepository.addFranchise(franchise);
    }

    public List<Franchise> getAllFranchise() {
        return franchiseRepository.getAllFranchise();
    }

    public boolean deleteFranchise(int franchiseId) {
        return franchiseRepository.deleteFranchise(franchiseId);
    }

    public boolean addProduct(Product product) {
        return productsRepository.addProduct(product);
    }

    public List<Product> getAllProduct() {
        return productsRepository.getAllProducts();
    }

    public boolean addStock(Stock stock) {
        int existingQuantity = stockRepository.getStockById(stock.getProductId(), stock.getFranchiseId()).getQuantity();
        stock.setQuantity(existingQuantity + stock.getQuantity());
        return stockRepository.addOrUpdateStock(stock);
    }

    public List<Request> getAllRequest() {
        return requestRepository.getAllRequest();
    }

    public boolean updateRequest(int requestId, Status status) {
        if (status.equals(Status.ACCEPTED)) {
            Request request = requestRepository.getRequestById(requestId);

            int existingCompanyStockQuantity = companyStockRepository.getCompanyStockById(request.getProductId()).getQuantity();

            if (existingCompanyStockQuantity < request.getQuantity()) {
                throw new OutOfStockException("Available stock: " + existingCompanyStockQuantity);
            }

            int existingStockQuantity = stockRepository.getStockById(request.getProductId(), request.getFranchiseId()).getQuantity();
            //updates stock to add the request amount of product
            stockRepository.addOrUpdateStock(new Stock(request.getFranchiseId(), request.getProductId(), request.getQuantity() + existingStockQuantity));
            //add a new record to supply to reflect the transfer
            supplyRepository.addSupply(new Supply(request.getProductId(), request.getFranchiseId(), request.getQuantity(), new Date(System.currentTimeMillis())));
            //removes the requested amount of product from company stock
            companyStockRepository.addOrUpdateCompanyStock(new CompanyStock(request.getProductId(), existingCompanyStockQuantity - request.getQuantity()));
        }

        return requestRepository.updateRequest(requestId, status);
    }

    public boolean addCompanyPurchase(int productId, int quantity) {
        if (companyPurchaseRepository.addPurchase(new CompanyPurchase(productId, quantity, new Date(System.currentTimeMillis())))) {
            int existingCompanyStockQuantity = companyStockRepository.getCompanyStockById(productId).getQuantity();
            return companyStockRepository.addOrUpdateCompanyStock(new CompanyStock(productId, existingCompanyStockQuantity + quantity));
        }
        return false;
    }
}
