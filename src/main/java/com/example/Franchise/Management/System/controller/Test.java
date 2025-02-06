package com.example.Franchise.Management.System.controller;

import com.example.Franchise.Management.System.dao.FranchiseRepository;
import com.example.Franchise.Management.System.dto.Franchise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Test {

    final FranchiseRepository franchiseRepository;

    @Autowired
    public Test(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    @GetMapping("franchise")
    public List<Franchise> getAllFranchise() {
        return franchiseRepository.getAllFranchise();
    }

    @PostMapping("franchise")
    public boolean addFranchise(@RequestBody Franchise franchise) {
        return franchiseRepository.addFranchise(franchise);
    }

    @DeleteMapping("franchise")
    public boolean deleteFranchise(@RequestParam("id") int franchiseId) {
        return franchiseRepository.deleteFranchise(franchiseId);
    }
}
