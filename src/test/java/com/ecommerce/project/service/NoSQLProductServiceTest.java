package com.ecommerce.project.service;

import com.ecommerce.project.document.NoSQLProduct;
import com.ecommerce.project.repository.NoSQLProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NoSQLProductServiceTest {

    @Mock
    private NoSQLProductRepository noSQLProductRepository;

    @InjectMocks
    private NoSQLProductService noSQLProductService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test
    }

    @Test
    public void testSaveProduct() {
        NoSQLProduct product = new NoSQLProduct();
        product.setName("Test Product");
        product.setPrice(99.99);
        when(noSQLProductRepository.save(product)).thenReturn(product); // Mock save behavior

        NoSQLProduct savedProduct = noSQLProductService.saveProduct(product);
        assertNotNull(savedProduct); // Ensure the saved product is not null
        assertEquals("Test Product", savedProduct.getName()); // Check the product name
    }

    @Test
    public void testGetAllProducts() {
        NoSQLProduct product1 = new NoSQLProduct();
        product1.setName("Product 1");
        NoSQLProduct product2 = new NoSQLProduct();
        product2.setName("Product 2");
        when(noSQLProductRepository.findAll()).thenReturn(Arrays.asList(product1, product2)); // Mock findAll behavior

        List<NoSQLProduct> products = noSQLProductService.getAllProducts();
        assertEquals(2, products.size()); // Verify the number of products returned
    }
}

