package com.rabobank.api.service.impl;

import com.rabobank.api.data.dao.ProductDao;
import com.rabobank.api.data.entity.Product;
import com.rabobank.api.exception.ProductNotFoundException;
import com.rabobank.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

  private static final String NOT_FOUND_MESSAGE = "the requested product with id %s is not found.";

  private final ProductDao dao;

  @Override
  public List<Product> getAllProducts() {
    return dao.findAll();
  }

  @Override
  public Product getProduct(Integer id) {
    return retrieveProduct(id);
  }

  @Override
  public Product updateProduct(Integer id, Product product) {
    return addOrUpdateProduct(id, product);
  }

  @Override
  public Product addProduct(Product product) {
    return addOrUpdateProduct(null, product);
  }

  private Product addOrUpdateProduct(Integer id, Product productToBeUpdated) {
    Product product = id != null ? retrieveProduct(id) : new Product();
    product.setCurrentPrice(productToBeUpdated.getCurrentPrice());
    product.setDescription(productToBeUpdated.getDescription());
    product.setName(productToBeUpdated.getName());
    return dao.save(product);
  }

  private Product retrieveProduct(Integer id) {
    Optional<Product> productOptional = dao.findById(id);
    if (productOptional.isEmpty()) {
      throw new ProductNotFoundException(String.format(NOT_FOUND_MESSAGE, id));
    }
    return productOptional.get();
  }
}
