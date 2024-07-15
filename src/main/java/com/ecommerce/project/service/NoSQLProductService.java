package com.ecommerce.project.service;

import com.ecommerce.project.document.NoSQLProduct;
import com.ecommerce.project.exception.ProductNotFoundException;
import com.ecommerce.project.repository.NoSQLProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoSQLProductService {

    private final NoSQLProductRepository noSQLProductRepository; // Repository for accessing NoSQL products

    @Autowired
    public NoSQLProductService(NoSQLProductRepository noSQLProductRepository) {
        this.noSQLProductRepository = noSQLProductRepository; // Constructor injection of the repository
    }

    // Save a product to the NoSQL database
    public NoSQLProduct saveProduct(NoSQLProduct product) {
        return noSQLProductRepository.save(product); // Save and return the product
    }

    // Retrieve all products from the NoSQL database
    public List<NoSQLProduct> getAllProducts() {
        return noSQLProductRepository.findAll(); // Return all products as a list
    }

    // Retrieve a product by its ID
    public NoSQLProduct getProductById(String id) {
        Optional<NoSQLProduct> product = noSQLProductRepository.findById(id); // Find product by ID
        return product.orElseThrow(() -> new ProductNotFoundException(id)); // Throw exception if not found
    }
}




