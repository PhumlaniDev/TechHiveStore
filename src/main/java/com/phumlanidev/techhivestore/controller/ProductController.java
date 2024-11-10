package com.phumlanidev.techhivestore.controller;

import com.phumlanidev.techhivestore.auth.ResponseDto;
import com.phumlanidev.techhivestore.constant.Constant;
import com.phumlanidev.techhivestore.dto.ProductDto;
import com.phumlanidev.techhivestore.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Comment: this is the placeholder for documentation.
 */
@RestController
@RequestMapping(path = "/api/v1/products", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class ProductController {

  private final ProductService productService;

  /**
   * Comment: this is the placeholder for documentation.
   */
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createProduct(@Valid @RequestBody ProductDto productDto) {
    productService.createProduct(productDto);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(Constant.STATUS_CODE_CREATED, "Product created successfully"));
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @PutMapping("/update/{productId}")
  public ResponseEntity<ResponseDto> updateProduct(@Valid @PathVariable Long productId,
                                                   @RequestBody ProductDto productDto) {
    try {
      boolean isUpdated = productService.updateProduct(productId, productDto);

      if (isUpdated) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(Constant.STATUS_CODE_ok, "Product updated successfully"));
      } else {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto(Constant.STATUS_500, Constant.MESSAGE_500));
      }
    } catch (RuntimeException ex) {
      return ResponseEntity
              .status(HttpStatus.NOT_FOUND)
              .body(new ResponseDto(HttpStatus.NOT_FOUND.toString(), "Product not found."));
    }
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @GetMapping("/find/{productId}")
  public ResponseEntity<ProductDto> findProductById(@Valid @PathVariable Long productId) {
    ProductDto product = productService.findProductById(productId);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(product);
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @DeleteMapping("/delete/{productId}")
  public ResponseEntity<ResponseDto> deleteProduct(@PathVariable Long productId) {
    productService.deleteProductTest(productId);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseDto(Constant.STATUS_CODE_ok, Constant.MESSAGE_200));
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @GetMapping
  public ResponseEntity<List<ProductDto>> getAllProducts() {
    List<ProductDto> products = productService.findAllProducts();
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(products);
  }

}
