package com.ecommerce.project.service;

import com.ecommerce.project.entity.Product;
import com.ecommerce.project.repository.ProductRepository;
import com.ecommerce.project.tree.BinaryTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository; // Repository for accessing products
    private final BinaryTree productTree = new BinaryTree(); // Binary tree instance for product categorization

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository; // Constructor injection of the product repository
        initializeTree(); // Initialize the binary tree with existing products
    }

    // Initialize the binary tree with products from the repository
    private void initializeTree() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            productTree.insert(product); // Insert each product into the binary tree
        }
    }

    // Retrieve all products
    public List<Product> getAllProducts() {
        return productRepository.findAll(); // Return the list of all products
    }

    // Paginated retrieval of products with sorting
    public Page<Product> getProducts(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy)); // Create pageable object
        return productRepository.findAll(pageable); // Return paginated products
    }

    /* Paginated retrieval of products with sorting
    * method overload to allow  for other usecases */
    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }


    // Retrieve a product by its ID
    public Product getProductById(Long id) {
        // Search in the binary tree first
        Product product = productTree.search(id);
        return product != null ? product : productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Save a new product
    public Product saveProduct(Product product) {
        Product savedProduct = productRepository.save(product); // Save to the database
        productTree.insert(savedProduct); // Insert into the binary tree for categorization
        return savedProduct; // Return the saved product
    }

    // Update an existing product
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found")); // Fetch existing product
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory());
        Product updatedProduct = productRepository.save(product); // Save updated product
        productTree.delete(id); // Remove old entry from the tree
        productTree.insert(updatedProduct); // Insert updated entry into the tree
        return updatedProduct; // Return updated product
    }

    // Delete a product by its ID
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found")); // Fetch existing product
        productRepository.delete(product); // Delete from database
        productTree.delete(id); // Remove from binary tree
    }

    // Retrieve products by category name
    public List<Product> getProductsByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName); // Return products in specified category
    }

    // Retrieve products by name
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name); // Return products matching the specified name
    }

    // Retrieve products that are more expensive than a specified price
    public List<Product> getExpensiveProducts(Double price) {
        return productRepository.findExpensiveProducts(price); // Return products above the specified price
    }
}



