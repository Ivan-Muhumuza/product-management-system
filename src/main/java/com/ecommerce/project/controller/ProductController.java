package com.ecommerce.project.controller;

import com.ecommerce.project.entity.Product;
import com.ecommerce.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


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
    public CollectionModel<EntityModel<Product>> getAllProducts() {
        // Convert the list of products to a list of EntityModel<Product> with HATEOAS links
        List<EntityModel<Product>> products = productService.getAllProducts().stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
                        linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")))
                .collect(Collectors.toList());

        // Return the collection of products wrapped in a CollectionModel
        return CollectionModel.of(products, linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
    }

    // GET endpoint to retrieve a paginated list of products with sorting
    @GetMapping("/page")
    public Page<Product> getProducts(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return productService.getProducts(pageable); // Ensure this does not lead back to itself
    }

    // GET endpoint to retrieve a specific product by its ID
    @GetMapping("/{id}")
    public EntityModel<Product> getProductById(@PathVariable Long id) {
        // Retrieve the product and wrap it in an EntityModel with HATEOAS links
        Product product = productService.getProductById(id);
        return EntityModel.of(product,
                linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
    }

    // POST endpoint to create a new product
    @PostMapping(consumes = "application/json")
    public EntityModel<Product> createProduct(@RequestBody Product product) {
        // Save the product and wrap it in an EntityModel with HATEOAS links
        Product createdProduct = productService.saveProduct(product);
        return EntityModel.of(createdProduct,
                linkTo(methodOn(ProductController.class).getProductById(createdProduct.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
    }

    // PUT endpoint to update an existing product by its ID
    @PutMapping("/{id}")
    public EntityModel<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        // Update the product and wrap it in an EntityModel with HATEOAS links
        Product updatedProduct = productService.updateProduct(id, product);
        return EntityModel.of(updatedProduct,
                linkTo(methodOn(ProductController.class).getProductById(updatedProduct.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
    }

    // DELETE endpoint to remove a product by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        // Delete the product
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // GET endpoint to retrieve products by their category name
    @GetMapping("/category/{categoryName}")
    public CollectionModel<EntityModel<Product>> getProductsByCategory(@PathVariable String categoryName) {
        // Retrieve products by category and convert to a list of EntityModel<Product> with HATEOAS links
        List<EntityModel<Product>> products = productService.getProductsByCategoryName(categoryName).stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
                        linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")))
                .collect(Collectors.toList());

        // Return the collection of products wrapped in a CollectionModel
        return CollectionModel.of(products, linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
    }

    // GET endpoint to retrieve products by their name
    @GetMapping("/name/{name}")
    public CollectionModel<EntityModel<Product>> getProductsByName(@PathVariable String name) {
        // Retrieve products by name and convert to a list of EntityModel<Product> with HATEOAS links
        List<EntityModel<Product>> products = productService.getProductsByName(name).stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
                        linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")))
                .collect(Collectors.toList());

        // Return the collection of products wrapped in a CollectionModel
        return CollectionModel.of(products, linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
    }

    // GET endpoint to retrieve products that are more expensive than a specified price
    @GetMapping("/expensive/{price}")
    public CollectionModel<EntityModel<Product>> getExpensiveProducts(@PathVariable Double price) {
        // Retrieve products above the specified price and convert to a list of EntityModel<Product> with HATEOAS links
        List<EntityModel<Product>> products = productService.getExpensiveProducts(price).stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
                        linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")))
                .collect(Collectors.toList());

        // Return the collection of products wrapped in a CollectionModel
        return CollectionModel.of(products, linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
    }
}