package com.rabobank.api.data.dao;

import com.rabobank.api.data.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends CrudRepository<Product, Integer> {

  List<Product> findAll();

  Optional<Product> findById(Integer id);
}
