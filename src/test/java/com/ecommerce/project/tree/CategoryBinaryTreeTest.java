package com.ecommerce.project.tree;

import com.ecommerce.project.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryBinaryTreeTest {
    private CategoryBinaryTree categoryTree;

    @BeforeEach
    public void setUp() {
        categoryTree = new CategoryBinaryTree(); // Initialize a new CategoryBinaryTree before each test
    }

    @Test
    public void testInsertAndSearch() {
        Category category1 = new Category("Electronics");
        category1.setId(1L); // Set ID for the category
        categoryTree.insert(category1); // Insert category into the tree

        Category foundCategory = categoryTree.search(1L); // Search for the category
        assertNotNull(foundCategory); // Ensure the category is found
        assertEquals("Electronics", foundCategory.getName()); // Verify the category name
    }

    @Test
    public void testDelete() {
        Category category1 = new Category("Electronics");
        category1.setId(1L);
        categoryTree.insert(category1); // Insert category

        categoryTree.delete(1L); // Delete the category
        Category foundCategory = categoryTree.search(1L); // Search for the deleted category
        assertNull(foundCategory); // Ensure the category is no longer found
    }

    @Test
    public void testInsertMultipleAndSearch() {
        Category category1 = new Category("Electronics");
        category1.setId(1L);
        Category category2 = new Category("Books");
        category2.setId(2L);
        Category category3 = new Category("Clothing");
        category3.setId(3L);

        categoryTree.insert(category1);
        categoryTree.insert(category2);
        categoryTree.insert(category3);

        assertNotNull(categoryTree.search(1L)); // Check category 1 exists
        assertNotNull(categoryTree.search(2L)); // Check category 2 exists
        assertNotNull(categoryTree.search(3L)); // Check category 3 exists
    }
}

