package com.phumlanidev.techhivestore.controller;

import com.phumlanidev.techhivestore.auth.ResponseDto;
import com.phumlanidev.techhivestore.constant.Constant;
import com.phumlanidev.techhivestore.dto.ProductDto;
import com.phumlanidev.techhivestore.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/products", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        productService.createProduct(productDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(Constant.STATUS_CODE_CREATED, "Product created successfully"));
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDto> updateProduct(@Valid @RequestBody ProductDto productDto) {
        boolean isUpdated = productService.updateProduct(productDto);

        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(Constant.STATUS_CODE_ok, "Product updated successfully"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(Constant.STATUS_500, Constant.MESSAGE_500));
        }

    }

    @PostMapping("/find")
    public ResponseEntity<ProductDto> findProductById(@Valid @RequestParam Long productId) {
        ProductDto product = productService.findProductById(productId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(product);
    }
}
