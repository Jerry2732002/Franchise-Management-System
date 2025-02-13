package com.example.Franchise.Management.System.service;

import com.example.Franchise.Management.System.dao.*;
import com.example.Franchise.Management.System.dto.*;
import com.example.Franchise.Management.System.enums.Role;
import com.example.Franchise.Management.System.enums.Status;
import com.example.Franchise.Management.System.exception.OutOfStockException;
import com.example.Franchise.Management.System.exception.UnauthorizedException;
import com.example.Franchise.Management.System.exception.UserNotFoundException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
    private final PurchaseRepository purchaseRepository;


    @Autowired
    public SuperAdminService(UserRepository userRepository, Authenticate authenticate, FranchiseRepository franchiseRepository, ProductsRepository productsRepository, StockRepository stockRepository, RequestRepository requestRepository, SupplyRepository supplyRepository, CompanyPurchaseRepository companyPurchaseRepository, CompanyStockRepository companyStockRepository, PurchaseRepository purchaseRepository) {
        this.userRepository = userRepository;
        this.authenticate = authenticate;
        this.franchiseRepository = franchiseRepository;
        this.productsRepository = productsRepository;
        this.stockRepository = stockRepository;
        this.requestRepository = requestRepository;
        this.supplyRepository = supplyRepository;
        this.companyPurchaseRepository = companyPurchaseRepository;
        this.companyStockRepository = companyStockRepository;
        this.purchaseRepository = purchaseRepository;
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
        if (admin.getRole().equals(Role.SADMIN)) {
            throw new UnauthorizedException("Cannot add Super Admin");
        }
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
        Stock existingStock = stockRepository.getStockById(stock.getProductId(), stock.getFranchiseId());
        if (existingStock != null) {
            stock.setQuantity(existingStock.getQuantity() + stock.getQuantity());
        }
        return stockRepository.addOrUpdateStock(stock);
    }

    public List<Request> getAllRequest(String status) {
        return requestRepository.getAllRequest().stream().filter(request -> {
                    if (status.equals("PENDING")) {
                        return request.getStatus().equals(Status.PENDING) || request.getStatus().equals(Status.PARTIALLY_ACCEPTED);
                    }
                    return request.getStatus().name().equals(status.toUpperCase());
                }
        ).toList();
    }

    public boolean updateRequest(int requestId, Status status) {
        if (status.equals(Status.REJECTED)) {
            return requestRepository.updateRequestStatus(requestId, status);
        }

        Request request = requestRepository.getRequestById(requestId);
        CompanyStock existingCompanyStock = companyStockRepository.getCompanyStockById(request.getProductId());
        int companyStockQuantity = existingCompanyStock.getQuantity();

        if (companyStockQuantity <= 0) {
            throw new OutOfStockException("Out Of Stock. Available stock: " + companyStockQuantity);
        }

        Date currentDate = new Date(System.currentTimeMillis());

        if (companyStockQuantity < request.getQuantity()) {
            status = Status.PARTIALLY_ACCEPTED;
            handlePartialStockUpdate(request, existingCompanyStock, currentDate);
        } else {
            handleFullStockUpdate(request, existingCompanyStock, currentDate);
        }

        return requestRepository.updateRequestStatus(requestId, status);
    }

    private void handlePartialStockUpdate(Request request, CompanyStock existingCompanyStock, Date currentDate) {
        Stock existingStock = stockRepository.getStockById(request.getProductId(), request.getFranchiseId());
        int existingStockQuantity = (existingStock != null) ? existingStock.getQuantity() : 0;

        stockRepository.addOrUpdateStock(new Stock(request.getFranchiseId(), request.getProductId(), existingCompanyStock.getQuantity() + existingStockQuantity));

        supplyRepository.addSupply(new Supply(request.getProductId(), request.getFranchiseId(), existingCompanyStock.getQuantity(), currentDate));

        companyStockRepository.addOrUpdateCompanyStock(new CompanyStock(request.getProductId(), 0));

        requestRepository.updateRequestQuantity(request.getRequestID(), request.getQuantity() - existingCompanyStock.getQuantity());
    }

    private void handleFullStockUpdate(Request request, CompanyStock existingCompanyStock, Date currentDate) {
        Stock existingStock = stockRepository.getStockById(request.getProductId(), request.getFranchiseId());
        int existingStockQuantity = (existingStock != null) ? existingStock.getQuantity() : 0;

        stockRepository.addOrUpdateStock(new Stock(request.getFranchiseId(), request.getProductId(), request.getQuantity() + existingStockQuantity));

        supplyRepository.addSupply(new Supply(request.getProductId(), request.getFranchiseId(), request.getQuantity(), currentDate));

        companyStockRepository.addOrUpdateCompanyStock(new CompanyStock(request.getProductId(), existingCompanyStock.getQuantity() - request.getQuantity()));
    }


    public boolean addCompanyPurchase(int productId, int quantity) {
        if (companyPurchaseRepository.addPurchase(new CompanyPurchase(productId, quantity, new Date(System.currentTimeMillis())))) {
            CompanyStock existingCompanyStock = companyStockRepository.getCompanyStockById(productId);
            return companyStockRepository.addOrUpdateCompanyStock(new CompanyStock(productId, existingCompanyStock.getQuantity() + quantity));
        }
        return false;
    }

    public List<CompanyStock> getAllCompanyStock() {
        return companyStockRepository.getAllCompanyStock();
    }

    public byte[] generateCompanyReport(Date startDate, Date endDate) throws IOException {
        List<Report> reports = supplyRepository.getCompanyReport(startDate, endDate);
        List<Report> reports2 = companyPurchaseRepository.getCompanyReport(startDate, endDate);
        reports.addAll(reports2);

        reports.sort(Comparator.comparing(Report::getSupplyPurchaseDate));

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Supply Report");

        CellStyle buyStyle = workbook.createCellStyle();
        buyStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        buyStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle sellStyle = workbook.createCellStyle();
        sellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        sellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Product Name", "Product Company", "Quantity", "Supply Purchase Date", "Price", "Total Price", "Buy/Sell"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        double totalCompanyProfit = reports.stream().map(report -> {
            if (report.getBuyOrSell().equals("BUY")) {
                return -report.getTotalPrice();
            }
            return report.getTotalPrice();
        }).reduce(Double::sum).get();

        int rowNum = 1;
        for (Report report : reports) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(report.getProductName());
            row.createCell(1).setCellValue(report.getProductCompany());
            row.createCell(2).setCellValue(report.getQuantity());
            row.createCell(3).setCellValue(report.getSupplyPurchaseDate().toString());
            row.createCell(4).setCellValue(report.getPrice());
            row.createCell(5).setCellValue(report.getTotalPrice());
            Cell cell = row.createCell(6);
            cell.setCellValue(report.getBuyOrSell());

            if (report.getBuyOrSell().equals("BUY")) {
                cell.setCellStyle(buyStyle);
            } else {
                cell.setCellStyle(sellStyle);
            }

        }
        Row row = sheet.createRow(rowNum);
        row.createCell(4).setCellValue("Total Company Profit");
        row.createCell(5).setCellValue(totalCompanyProfit);

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        workbook.write(outStream);
        workbook.close();

        return outStream.toByteArray();
    }

    public byte[] generateFranchiseReport(Date startDate, Date endDate) throws IOException {
        List<Integer> franchiseIds = franchiseRepository.getAllFranchiseId();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Franchise Report");
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Franchise Id", "Location", "Building Name", "Total Income"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;

        for (int franchiseId : franchiseIds) {
            List<Report> reports = supplyRepository.getFranchiseReport(startDate, endDate, franchiseId);
            List<Report> reports2 = purchaseRepository.getFranchiseReport(startDate, endDate, franchiseId);
            reports.addAll(reports2);
            Franchise franchise = franchiseRepository.getFranchiseById(franchiseId);
            double totalCompanyProfit = 0;
            if (!reports.isEmpty()) {
                totalCompanyProfit = reports.stream().map(report -> {
                    if (report.getBuyOrSell().equals("BUY")) {
                        return -report.getTotalPrice();
                    }
                    return report.getTotalPrice();
                }).reduce(Double::sum).get();
            }

            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(franchiseId);
            row.createCell(1).setCellValue(franchise.getLocation());
            row.createCell(2).setCellValue(franchise.getBuildingName());
            row.createCell(3).setCellValue(totalCompanyProfit);

        }

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        workbook.write(outStream);
        workbook.close();

        return outStream.toByteArray();
    }
}

