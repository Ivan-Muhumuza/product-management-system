package com.ecommerce.project.controller;

import com.ecommerce.project.entity.Product;
import com.ecommerce.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products") // Base URL for all product-related endpoints
public class ProductController {

    private final ProductService productService; // Service for handling product operations

    // Constructor injection for ProductService
    public ProductController(@Autowired ProductService productService) {
        this.productService = productService;
    }

    // GET endpoint to retrieve all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts(); // Return the list of all products
    }

    // GET endpoint to retrieve a paginated list of products with sorting
    @GetMapping("/page")
    public Page<Product> getProducts(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy) {
        return productService.getProducts(page, size, sortBy); // Return a paginated list of products
    }

    // GET endpoint to retrieve a specific product by its ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id); // Return the product with the given ID
    }

    // POST endpoint to create a new product
    @PostMapping(consumes = "application/json")
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product); // Save the product and return it
    }

    // PUT endpoint to update an existing product by its ID
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product); // Update the product and return the updated product
    }

    // DELETE endpoint to remove a product by its ID
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id); // Delete the product with the given ID
    }

    // GET endpoint to retrieve products by their category name
    @GetMapping("/category/{categoryName}")
    public List<Product> getProductsByCategory(@PathVariable String categoryName) {
        return productService.getProductsByCategoryName(categoryName); // Return products in the specified category
    }

    // GET endpoint to retrieve products by their name
    @GetMapping("/name/{name}")
    public List<Product> getProductsByName(@PathVariable String name) {
        return productService.getProductsByName(name); // Return products that match the specified name
    }

    // GET endpoint to retrieve products that are more expensive than a specified price
    @GetMapping("/expensive/{price}")
    public List<Product> getExpensiveProducts(@PathVariable Double price) {
        return productService.getExpensiveProducts(price); // Return products above the specified price
    }
}