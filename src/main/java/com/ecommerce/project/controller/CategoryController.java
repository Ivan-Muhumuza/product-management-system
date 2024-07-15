package com.ecommerce.project.controller;

import com.ecommerce.project.entity.Category;
import com.ecommerce.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import java.util.stream.Collectors;


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
    public ResponseEntity<CollectionModel<EntityModel<Category>>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<EntityModel<Category>> categoryResources = categories.stream()
                .map(category -> EntityModel.of(category,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getCategoryById(category.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getAllCategories()).withRel("categories")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(categoryResources, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getAllCategories()).withSelfRel()));
    }

    // GET endpoint to retrieve a specific category by ID
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Category>> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        EntityModel<Category> resource = EntityModel.of(category,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getCategoryById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getAllCategories()).withRel("categories"));
        return ResponseEntity.ok(resource); // Return the category with HATEOAS links
    }

    // POST endpoint to create a new category
    @PostMapping(consumes = "application/json") // Expecting JSON request body
    public ResponseEntity<EntityModel<Category>> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        EntityModel<Category> resource = EntityModel.of(createdCategory,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getCategoryById(createdCategory.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getAllCategories()).withRel("categories"));
        return ResponseEntity.status(HttpStatus.CREATED).body(resource); // Return created category with HATEOAS links
    }

    // PUT endpoint to update an existing category by ID
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Category>> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
        EntityModel<Category> resource = EntityModel.of(updatedCategory,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getCategoryById(updatedCategory.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getAllCategories()).withRel("categories"));
        return ResponseEntity.ok(resource); // Return updated category with HATEOAS links
    }

    // DELETE endpoint to remove a category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build(); // Return 204 No Content status
    }

    // GET endpoint for paginated and sorted categories
    @GetMapping("/page")
    public ResponseEntity<CollectionModel<EntityModel<Category>>> getCategories(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy) {
        Page<Category> categoryPage = categoryService.getCategories(page, size, sortBy);
        List<EntityModel<Category>> categoryResources = categoryPage.getContent().stream()
                .map(category -> EntityModel.of(category,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getCategoryById(category.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getAllCategories()).withRel("categories")))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Category>> categoryCollectionModel = CollectionModel.of(categoryResources,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getCategories(page, size, sortBy)).withSelfRel());

        // Add pagination information
        categoryCollectionModel.add(Link.of(categoryPage.getPageable().getSort().toString()).withRel("sort"));
        return ResponseEntity.ok(categoryCollectionModel); // Return paginated categories with HATEOAS links
    }

    // GET endpoint to retrieve a category by name
    @GetMapping("/name/{name}")
    public ResponseEntity<EntityModel<Category>> getCategoryByName(@PathVariable String name) {
        Category category = categoryService.getCategoryByName(name);
        EntityModel<Category> resource = EntityModel.of(category,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getCategoryByName(name)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getAllCategories()).withRel("categories"));
        return ResponseEntity.ok(resource); // Return category found by name with HATEOAS links
    }

    // GET endpoint to retrieve categories that have products
    @GetMapping("/with-products")
    public ResponseEntity<CollectionModel<EntityModel<Category>>> getCategoriesWithProducts() {
        List<Category> categories = categoryService.getCategoriesWithProducts();
        List<EntityModel<Category>> categoryResources = categories.stream()
                .map(category -> EntityModel.of(category,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getCategoryById(category.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getAllCategories()).withRel("categories")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(categoryResources, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getCategoriesWithProducts()).withSelfRel())); // Return categories with products and HATEOAS links
    }
}