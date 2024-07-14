package com.ecommerce.project.controller;

import com.ecommerce.project.document.NoSQLProduct;
import com.ecommerce.project.service.NoSQLProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nosql/products") // Base URL for all NoSQL product-related endpoints
public class NoSQLProductController {

    private final NoSQLProductService noSQLProductService; // Service for handling NoSQL product operations

    // Constructor injection for NoSQLProductService
    @Autowired
    public NoSQLProductController(NoSQLProductService noSQLProductService) {
        this.noSQLProductService = noSQLProductService;
    }

    // POST endpoint to add a new product to the NoSQL database
    @PostMapping
    public NoSQLProduct addProduct(@RequestBody NoSQLProduct product) {
        return noSQLProductService.saveProduct(product); // Save the product and return it
    }

    // GET endpoint to retrieve all products from the NoSQL database
    @GetMapping
    public List<NoSQLProduct> getProducts() {
        return noSQLProductService.getAllProducts(); // Return the list of products
    }
}

