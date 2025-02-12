package com.example.Franchise.Management.System.service;

import com.example.Franchise.Management.System.dao.PurchaseRepository;
import com.example.Franchise.Management.System.dao.RequestRepository;
import com.example.Franchise.Management.System.dao.SupplyRepository;
import com.example.Franchise.Management.System.dao.UserRepository;
import com.example.Franchise.Management.System.dto.Report;
import com.example.Franchise.Management.System.dto.Request;
import com.example.Franchise.Management.System.dto.User;
import com.example.Franchise.Management.System.enums.Role;
import com.example.Franchise.Management.System.enums.Status;
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

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final Authenticate authenticate;
    private final RequestRepository requestRepository;
    private final SupplyRepository supplyRepository;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public AdminService(UserRepository userRepository, Authenticate authenticate, RequestRepository requestRepository, SupplyRepository supplyRepository, PurchaseRepository purchaseRepository) {
        this.userRepository = userRepository;
        this.authenticate = authenticate;
        this.requestRepository = requestRepository;
        this.supplyRepository = supplyRepository;
        this.purchaseRepository = purchaseRepository;
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

    public byte[] generateFranchiseReport(Date startDate, Date endDate, int franchiseId) throws IOException {
        List<Report> reports = supplyRepository.getFranchiseReport(startDate, endDate, franchiseId);
        List<Report> reports2 = purchaseRepository.getFranchiseReport(startDate, endDate,franchiseId);
        reports.addAll(reports2);

        reports2.sort(Comparator.comparing(Report::getSupplyPurchaseDate));

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
}
