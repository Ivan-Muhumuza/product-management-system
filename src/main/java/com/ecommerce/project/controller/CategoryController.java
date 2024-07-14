package com.ecommerce.project.controller;

import com.ecommerce.project.entity.Category;
import com.ecommerce.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;


@RestController
@RequestMapping("/categories") // Base URL for all category-related endpoints
public class CategoryController {

    private final CategoryService categoryService; // Service for handling category operations

    // Constructor injection for CategoryService
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // GET endpoint to retrieve all categories
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK); // Return categories with 200 OK status
    }

    // GET endpoint to retrieve a specific category by ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK); // Return the category with 200 OK status
    }

    // POST endpoint to create a new category
    @PostMapping(consumes = "application/json") // Expecting JSON request body
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory); // Return created category with 201 Created status
    }

    // PUT endpoint to update an existing category by ID
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK); // Return updated category with 200 OK status
    }

    // DELETE endpoint to remove a category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 No Content status
    }

    // GET endpoint for paginated and sorted categories
    @GetMapping("/page")
    public Page<Category> getCategories(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy) {
        return categoryService.getCategories(page, size, sortBy); // Return a paginated list of categories
    }

    // GET endpoint to retrieve a category by name
    @GetMapping("/name/{name}")
    public Category getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name); // Return category found by name
    }

    // GET endpoint to retrieve categories that have products
    @GetMapping("/with-products")
    public List<Category> getCategoriesWithProducts() {
        return categoryService.getCategoriesWithProducts(); // Return categories with associated products
    }
}