package com.ecommerce.project.tree;

import com.ecommerce.project.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BinaryTreeTest {
    private BinaryTree binaryTree;

    @BeforeEach
    public void setUp() {
        binaryTree = new BinaryTree(); // Initialize a new BinaryTree before each test
    }

    @Test
    public void testInsertAndSearch() {
        Product product1 = new Product("Product1", 10.0, null);
        product1.setId(1L); // Set ID for the product
        binaryTree.insert(product1); // Insert product into the tree

        Product foundProduct = binaryTree.search(1L); // Search for the product
        assertNotNull(foundProduct); // Ensure the product is found
        assertEquals("Product1", foundProduct.getName()); // Verify the product name
    }

    @Test
    public void testDelete() {
        Product product1 = new Product("Product1", 10.0, null);
        product1.setId(1L);
        binaryTree.insert(product1); // Insert product

        binaryTree.delete(1L); // Delete the product
        Product foundProduct = binaryTree.search(1L); // Search for the deleted product
        assertNull(foundProduct); // Ensure the product is no longer found
    }

    @Test
    public void testInsertMultipleAndSearch() {
        Product product1 = new Product("Product1", 10.0, null);
        product1.setId(1L);
        Product product2 = new Product("Product2", 20.0, null);
        product2.setId(2L);
        Product product3 = new Product("Product3", 5.0, null);
        product3.setId(3L);

        binaryTree.insert(product1);
        binaryTree.insert(product2);
        binaryTree.insert(product3);

        assertNotNull(binaryTree.search(1L)); // Check product 1 exists
        assertNotNull(binaryTree.search(2L)); // Check product 2 exists
        assertNotNull(binaryTree.search(3L)); // Check product 3 exists
    }
}

