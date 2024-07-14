package com.ecommerce.project.service;

import com.ecommerce.project.entity.Product;
import com.ecommerce.project.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test
    }

    @Test
    public void testSaveProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(99.99);
        when(productRepository.save(product)).thenReturn(product); // Mock save behavior

        Product savedProduct = productService.saveProduct(product);
        assertNotNull(savedProduct); // Ensure the saved product is not null
        assertEquals("Test Product", savedProduct.getName()); // Check the product name
    }

    @Test
    public void testGetAllProducts() {
        Product product1 = new Product();
        product1.setName("Product 1");
        Product product2 = new Product();
        product2.setName("Product 2");
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2)); // Mock findAll behavior

        List<Product> products = productService.getAllProducts();
        assertEquals(2, products.size()); // Verify the number of products returned
    }

    @Test
    public void testGetProductByIdFound() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        when(productRepository.findById(1L)).thenReturn(Optional.of(product)); // Mock findById behavior

        Product foundProduct = productService.getProductById(1L);
        assertEquals("Test Product", foundProduct.getName()); // Verify the product name
    }

    @Test
    public void testGetProductByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty()); // Mock not found behavior

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.getProductById(1L);
        });
        assertEquals("Product not found", exception.getMessage()); // Verify exception message
    }

}

