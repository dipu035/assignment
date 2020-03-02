package com.rabobank.api.service;

import com.rabobank.api.data.entity.Product;

import java.util.List;

public interface ProductService {

  /**
   * Get all products
   *
   * @return <code>List<Product></code> List of products
   */
  List<Product> getAllProducts();

  /**
   * Get a product based on provided id.
   *
   * @param id The `id` of the product
   * @return <code>Product</code> The product object.
   */
  Product getProduct(Integer id);

  /**
   * Find a product based on the id and update the product with the given data.
   *
   * @param id The `id` of the product
   * @param product The `Product` data.
   * @return The updated product
   */
  Product updateProduct(Integer id, Product product);

  /**
   * Add a product
   *
   * @param product The product data.
   * @return The added product.
   */
  Product addProduct(Product product);
}
