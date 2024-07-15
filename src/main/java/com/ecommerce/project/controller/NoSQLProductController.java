package com.ecommerce.project.controller;

import com.ecommerce.project.document.NoSQLProduct;
import com.ecommerce.project.service.NoSQLProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/nosql/products") // Base URL for all NoSQL product-related endpoints
public class NoSQLProductController {

    private final NoSQLProductService noSQLProductService; // Service for handling NoSQL product operations

    // Constructor injection for NoSQLProductService
    @Autowired
    public NoSQLProductController(NoSQLProductService noSQLProductService) {
        this.noSQLProductService = noSQLProductService;
    }

    // POST endpoint to add a new product to the NoSQL database
    @PostMapping
    public ResponseEntity<EntityModel<NoSQLProduct>> addProduct(@RequestBody NoSQLProduct product) {
        NoSQLProduct savedProduct = noSQLProductService.saveProduct(product); // Save the product
        EntityModel<NoSQLProduct> resource = EntityModel.of(savedProduct,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NoSQLProductController.class).getProductById(savedProduct.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NoSQLProductController.class).getProducts()).withRel("all-products"));
        return ResponseEntity.status(HttpStatus.CREATED).body(resource); // Return created product with HATEOAS links
    }

    // GET endpoint to retrieve all products from the NoSQL database
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<NoSQLProduct>>> getProducts() {
        List<NoSQLProduct> products = noSQLProductService.getAllProducts(); // Get the list of products
        List<EntityModel<NoSQLProduct>> productResources = products.stream()
                .map(product -> EntityModel.of(product,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NoSQLProductController.class).getProductById(product.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NoSQLProductController.class).getProducts()).withRel("all-products")))
                .collect(Collectors.toList());

        // Wrap the resources in a CollectionModel
        CollectionModel<EntityModel<NoSQLProduct>> productCollectionModel = CollectionModel.of(productResources,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NoSQLProductController.class).getProducts()).withSelfRel());

        return ResponseEntity.ok(productCollectionModel); // Return the collection of products with HATEOAS links
    }

    // GET endpoint to retrieve a product by ID
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<NoSQLProduct>> getProductById(@PathVariable String id) {
        NoSQLProduct product = noSQLProductService.getProductById(id); // Retrieve the product by ID
        EntityModel<NoSQLProduct> resource = EntityModel.of(product,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NoSQLProductController.class).getProductById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NoSQLProductController.class).getProducts()).withRel("all-products"));
        return ResponseEntity.ok(resource); // Return the product with HATEOAS links
    }
}

