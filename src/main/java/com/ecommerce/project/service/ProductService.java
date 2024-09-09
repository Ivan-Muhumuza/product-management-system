package com.ecommerce.project.service;

import com.ecommerce.project.entity.Product;
import com.ecommerce.project.repository.ProductRepository;
import com.ecommerce.project.tree.BinaryTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    private boolean isTreeInitialized = false;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository; // Constructor injection of the product repository
        initializeTree(); // Initialize the binary tree with existing products
    }

    // Initialize the binary tree with products from the repository
    private void initializeTree() {
        if (!isTreeInitialized) {
            List<Product> products = productRepository.findAll();
            for (Product product : products) {
                productTree.insert(product); //insert each product into the binary tree
            }
            isTreeInitialized = true;
        }
    }

    // Retrieve all products
    @Cacheable(value = "products")  // Cache the result to reduce database load
    public List<Product> getAllProducts() {
        return productRepository.findAll(); // Return the list of all products
    }

    // Paginated retrieval of products with sorting
    @Cacheable(value = "productsPage", key = "#page + '-' + #size + '-' + #sortBy")
    public Page<Product> getProducts(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy)); // Create pageable object
        return productRepository.findAll(pageable); // Return paginated products
    }

    /* Paginated retrieval of products with sorting
    * method overload to allow  for other usecases */
    @Cacheable(value = "productsPage", key = "#pageable")
    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }


    // Retrieve a product by its ID
    @Cacheable(value = "productById", key = "#id")
    public Product getProductById(Long id) {
        // Search in the binary tree first
        Product product = productTree.search(id);
        return product != null ? product : productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Save a new product
    @CacheEvict(value = {"products", "productsPage"}, allEntries = true) // Evict cache on save
    public Product saveProduct(Product product) {
        Product savedProduct = productRepository.save(product); // Save to the database
        productTree.insert(savedProduct); // Insert into the binary tree for categorization
        return savedProduct; // Return the saved product
    }

    @CacheEvict(value = {"products", "productsPage", "productById"}, key = "#id") // Evict cache
    public synchronized Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update product details
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory());

        // Update the binary tree
        productTree.update(id, product);

        // Save the updated product
        return productRepository.save(product);
    }

    // Delete a product by its ID with cache eviction and binary tree deletion
    @CacheEvict(value = {"products", "productsPage", "productById"}, key = "#id")
    public synchronized void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found")); // Fetch existing product
        productRepository.delete(product); // Delete from database
        productTree.delete(id); // Remove from binary tree
    }

    // Retrieve products by category name with caching
    @Cacheable(value = "productsByCategoryName", key = "#categoryName")
    public List<Product> getProductsByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName); // Return products in specified category
    }

    // Retrieve products by name with caching
    @Cacheable(value = "productsByName", key = "#name")
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name); // Return products matching the specified name
    }

    // Retrieve expensive products with caching
    @Cacheable(value = "expensiveProducts", key = "#price")
    public List<Product> getExpensiveProducts(Double price) {
        return productRepository.findExpensiveProducts(price); // Return products above the specified price
    }
}



