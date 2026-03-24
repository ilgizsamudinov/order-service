package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.product.ProductDetailsView;
import org.example.orderservice.dto.product.ProductResponse;
import org.example.orderservice.dto.product.ProductListView;
import org.example.orderservice.exception.NotFoundException;
import org.example.orderservice.mapper.ProductMapper;
import org.example.orderservice.model.Product;
import org.example.orderservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService  {

    private final ProductRepository productRepository;
    private final Clock clock;
    private final ProductMapper productMapper;


    @Transactional
    public Product createProduct(Product product) {
        product.setCreatedAt(LocalDateTime.now(clock));
        product.setUpdatedAt(LocalDateTime.now(clock));
        return productRepository.save(product);
    }


    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }



   @Transactional(readOnly = true)
    public Page<ProductListView> getAllProducts(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return productRepository.findAllProducts(pageable);
    }


    @Transactional
    public Product updateProduct(Long id, Product product) {
        Product existing = getProductById(id);
        productMapper.merge(existing, product);
        return productRepository.save(existing);
    }


    @Transactional
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public ProductDetailsView getProductDetailsById(Long productId){
        return productRepository.findProductDetailsById(productId)
                .orElseThrow(()-> new NotFoundException("Product with id: " + productId + " not found!"));
    }
}
