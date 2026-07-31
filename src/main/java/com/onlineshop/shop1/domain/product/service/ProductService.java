package com.onlineshop.shop1.domain.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onlineshop.shop1.domain.product.dto.ProductDto;
import com.onlineshop.shop1.domain.product.entity.Product;
import com.onlineshop.shop1.domain.product.repository.ProductRepository;
import com.onlineshop.shop1.exception.DuplicateProductException;
import com.onlineshop.shop1.exception.ProductNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductDto> getAllProduct() {
        return productRepository.findAll().stream()
                .map(ProductDto::from)
                .toList();
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return ProductDto.from(product);
    }

    @Transactional
    public ProductDto addProduct(ProductDto productDto) {
        if (productRepository.existsByProductName(productDto.getProductName())) {
            throw new DuplicateProductException(productDto.getProductName());
        }
        Product product = Product.builder()
                .productName(productDto.getProductName())
                .productPrice(productDto.getProductPrice())
                .build();
        Product savedProduct = productRepository.save(product);
        return ProductDto.from(savedProduct);
    }

    @Transactional
    public ProductDto updateProduct(Long id,ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(()->new ProductNotFoundException(id));
        product.update(productDto.getProductName(), productDto.getProductPrice(),productDto.getQuantity());
        return ProductDto.from(product);
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id).orElseThrow(()->new ProductNotFoundException(id));
        productRepository.deleteById(id);
    }

}
