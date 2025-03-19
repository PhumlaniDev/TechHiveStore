package com.phumlanidev.techhivestore.controller;

import com.phumlanidev.techhivestore.auth.ResponseDto;
import com.phumlanidev.techhivestore.constant.Constant;
import com.phumlanidev.techhivestore.dto.ProductDto;
import com.phumlanidev.techhivestore.service.impl.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ResponseDto(Constant.STATUS_CODE_CREATED, "Product created successfully"));
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @PutMapping("/update/{productId}")
  public ResponseEntity<ProductDto> updateProduct(@Valid @PathVariable Long productId,
                                                  @RequestBody ProductDto productDto) {
    ProductDto updatedProduct = productService.updateProduct(productId, productDto);
    return ResponseEntity.ok(updatedProduct);

  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @GetMapping("/find/{productId}")
  public ResponseEntity<ProductDto> findProductById(@Valid @PathVariable Long productId) {
    ProductDto product = productService.findProductById(productId);
    return ResponseEntity.status(HttpStatus.OK).body(product);
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @DeleteMapping("/delete/{productId}")
  public ResponseEntity<ResponseDto> deleteProduct(@PathVariable Long productId) {
    productService.deleteProductById(productId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseDto(Constant.STATUS_CODE_OK, Constant.MESSAGE_200));
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @GetMapping
  public ResponseEntity<List<ProductDto>> getAllProducts() {
    List<ProductDto> products = productService.findAllProducts();
    return ResponseEntity.status(HttpStatus.OK).body(products);
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  //  GET /api/products/search?name=laptop&page=0&size=5&sortField=price&sortDir=desc
  @GetMapping("/search")
  public Page<ProductDto> searchProducts(@RequestParam(required = false) String productName,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "productId") String sortField,
                                         @RequestParam(defaultValue = "asc") String sortDir) {
    return productService.searchProducts(productName, page, size, sortField, sortDir);
  }

}
