package com.ecommerce.project.repository;

import com.ecommerce.project.document.NoSQLProduct;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoSQLProductRepository extends MongoRepository<NoSQLProduct, String> {
}
