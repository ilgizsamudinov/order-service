package org.example.orderservice.repository;

import org.example.orderservice.dto.product.ProductDetailsView;
import org.example.orderservice.dto.product.ProductListView;
import org.example.orderservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query(value = """
            select
                p.id as id,
                p.title as title,
                p.description as description,
                p.sku as sku,
                p.price as price,
                p.created_at as createdAt,
                p.updated_at as updatedAt,
                p.weight as weight
            from products p
            where p.id = :id
            """, nativeQuery = true)
    Optional<ProductDetailsView> findProductDetailsById(@Param("id") Long id);


    @Query(
            value = """
            select
                p.id as id,
                p.title as title,
                p.price as price,
                p.sku as sku
            from products p
            """,
            countQuery = """
            select count(*)
            from products
            """,
            nativeQuery = true
    )
    Page<ProductListView> findAllProducts(Pageable pageable);
}