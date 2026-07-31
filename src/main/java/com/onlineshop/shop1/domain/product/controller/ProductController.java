package com.onlineshop.shop1.domain.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.shop1.domain.product.dto.ProductDto;
import com.onlineshop.shop1.domain.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "상품 정보 관리" ,description = "상품 정보 조회 API")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @Operation(summary = "전체 상품 조회", description = "전체 상품을 조회합니다.")
    public ResponseEntity<List<ProductDto>> getAllProduct(){
        List<ProductDto> productDtos = productService.getAllProduct();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("{id}")
    @Operation(summary = "상품 조회(Id)", description = "Id로 상품을 조회합니다.")
    public ResponseEntity<ProductDto> getProductById(@Valid @PathVariable("id")Long id){
        ProductDto productDto = productService.getProductById(id);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping("/add")
    @Operation(summary = "상품 추가", description = "상품을 추가합니다.")
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto){
        ProductDto productDto2 = productService.addProduct(productDto);
        return ResponseEntity.ok(productDto2);
    }
    
    @PutMapping("/update")
    @Operation(summary = "상품 정보를 변경", description = "상품을 정보를 변경합니다.")
    public ResponseEntity<ProductDto> updateProudct(@Valid @RequestParam("id")Long id,@RequestBody ProductDto productDto){
        ProductDto productDto2 = productService.updateProduct(id,productDto);
        return ResponseEntity.ok(productDto2);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    public ResponseEntity<Void> deleteProduct(@Valid @RequestParam("id")Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    



}
