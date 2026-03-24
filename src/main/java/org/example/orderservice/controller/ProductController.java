package org.example.orderservice.controller;


import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.product.ProductDetailsView;
import org.example.orderservice.dto.product.ProductRequest;
import org.example.orderservice.dto.product.ProductResponse;
import org.example.orderservice.dto.product.ProductListView;
import org.example.orderservice.mapper.ProductMapper;
import org.example.orderservice.model.Product;
import org.example.orderservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductMapper productMapper;
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        Product product = productMapper.toEntity(productRequest);
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productMapper.toResponse(createdProduct));
    }



    @GetMapping
    public ResponseEntity<Page<ProductListView>> getAllProducts(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailsView> getProductDetails(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductDetailsById(productId));
    }


    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductRequest productRequest
    ) {
        Product product = productMapper.toEntity(productRequest);
        Product updatedProduct = productService.updateProduct(productId, product);
        return ResponseEntity.ok(productMapper.toResponse(updatedProduct));
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }
}