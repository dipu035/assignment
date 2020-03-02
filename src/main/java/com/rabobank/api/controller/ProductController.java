package com.rabobank.api.controller;

import com.rabobank.api.data.entity.Product;
import com.rabobank.api.service.ProductService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
    value = "/api",
    produces = {"application/json"})
@RequiredArgsConstructor
@Validated
public class ProductController {

  @NonNull
  private final ProductService service;

  @GetMapping(path = "/products")
  public ResponseEntity<List<Product>> getProducts() {
    return ResponseEntity.ok(service.getAllProducts());
  }

  @GetMapping(path = "/products/{id}")
  public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
    return ResponseEntity.ok(service.getProduct(id));
  }

  @PutMapping(path = "/products/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable Integer id,
      @RequestBody Product product) {
    return ResponseEntity.ok(service.updateProduct(id, product));
  }

  @PostMapping(path = "/products")
  public ResponseEntity<Product> addProduct(@RequestBody Product product) {
    return ResponseEntity.ok(service.addProduct(product));
  }

}
