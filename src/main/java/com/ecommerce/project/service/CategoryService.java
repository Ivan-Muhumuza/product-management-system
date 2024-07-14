package com.ecommerce.project.service;

import com.ecommerce.project.entity.Category;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.tree.CategoryBinaryTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryBinaryTree categoryTree = new CategoryBinaryTree(); // Instance of the binary tree for categories

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        initializeTree(); // Initialize the binary tree with existing categories
    }

    // Method to populate the binary tree with all categories from the database
    private void initializeTree() {
        List<Category> categories = categoryRepository.findAll(); // Fetch all categories
        for (Category category : categories) {
            categoryTree.insert(category); // Insert each category into the binary tree
        }
    }

    // Retrieve all categories from the repository
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Retrieve paginated categories with sorting
    public Page<Category> getCategories(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return categoryRepository.findAll(pageable);
    }

    // Retrieve a category by its ID, checking both the tree and the repository
    public Category getCategoryById(Long id) {
        // Search in the binary tree
        Category category = categoryTree.search(id);
        // If not found in the tree, check the database
        return category != null ? category : categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    // Create a new category and insert it into the binary tree
    public Category createCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        categoryTree.insert(savedCategory); // Insert into the binary tree
        return savedCategory;
    }

    // Update an existing category and reflect changes in the binary tree
    public Category updateCategory(Long id, Category updatedCategory) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existingCategory.setName(updatedCategory.getName()); // Update the category name
        Category updatedSavedCategory = categoryRepository.save(existingCategory);
        // Update the binary tree
        categoryTree.delete(id); // Remove old entry
        categoryTree.insert(updatedSavedCategory); // Insert updated entry
        return updatedSavedCategory;
    }

    // Delete a category and remove it from the binary tree
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
        categoryTree.delete(id); // Remove from binary tree
    }

    // Retrieve a category by its name
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    // Get categories that have associated products
    public List<Category> getCategoriesWithProducts() {
        return categoryRepository.findCategoriesWithProducts();
    }
}

