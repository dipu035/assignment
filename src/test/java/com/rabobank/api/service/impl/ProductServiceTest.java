package com.rabobank.api.service.impl;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rabobank.api.data.dao.ProductDao;
import com.rabobank.api.data.entity.Product;
import com.rabobank.api.exception.ProductNotFoundException;
import com.rabobank.api.service.ProductService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

  @Mock
  private ProductDao dao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private ProductService service;

  @Before
  public void setUp() {
    service = new ProductServiceImpl(dao);
  }

  @Test
  public void testGetAllProducts() {
    //arrange
    when(dao.findAll()).thenReturn(List.of(createProduct()));

    //act
    List<Product> products = service.getAllProducts();

    //assert
    assertThat(products.size(), equalTo(1));
    Product product = products.get(0);
    assertThat(product.getId(), equalTo(1));
    assertThat(product.getName(), equalTo("Product1"));
    assertThat(product.getCurrentPrice(), equalTo(20.00));
  }

  @Test
  public void testGetAllLocations_Empty_Result() {
    //arrange
    when(dao.findAll()).thenReturn(emptyList());

    //act
    List<Product> locations = service.getAllProducts();

    //assert
    assertThat(locations.isEmpty(), equalTo(true));
  }

  @Test
  public void testGetProduct() {
    //arrange
    when(dao.findById(anyInt()))
        .thenReturn(Optional.of(createProduct()));

    //act
    Product product = service.getProduct(1);

    //assert
    assertThat(product.getId(), equalTo(1));
    assertThat(product.getName(), equalTo("Product1"));
    assertThat(product.getCurrentPrice(), equalTo(20.00));
  }

  @Test
  public void testGetProduct_exception() {
    //arrange
    when(dao.findById(anyInt()))
        .thenReturn(Optional.empty());

    // Assert
    thrown.expect(ProductNotFoundException.class);
    thrown.expectMessage("the requested product with id 1 is not found.");

    //act
    service.getProduct(1);
  }

  @Test
  public void testUpdateProduct() {
    //arrange
    when(dao.findById(anyInt()))
        .thenReturn(Optional.of(createProduct()));

    //act
    service.updateProduct(1, new Product(null, "updatedProduct", BigDecimal.valueOf(30.12), null, null));

    //assert
    ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
    verify(dao, times(1)).save(productCaptor.capture());

    assertThat(productCaptor.getValue().getId(), equalTo(1));
    assertThat(productCaptor.getValue().getName(), equalTo("updatedProduct"));
    assertThat(productCaptor.getValue().getCurrentPrice(), equalTo(30.12));
    assertThat(productCaptor.getValue().getLastUpdate().getDayOfMonth(),
        equalTo(LocalDateTime.now().getDayOfMonth()));
  }

  @Test
  public void testAddProduct() {
    //act
    service.addProduct(new Product(null, "product1", BigDecimal.valueOf(30.12), null, null));

    //assert
    ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
    verify(dao, times(1)).save(productCaptor.capture());

    assertThat(productCaptor.getValue().getName(), equalTo("product1"));
    assertThat(productCaptor.getValue().getCurrentPrice(), equalTo(30.12));
    assertThat(productCaptor.getValue().getLastUpdate(), equalTo(null));
  }


  private Product createProduct() {
    return new Product(1, "Product1", BigDecimal.valueOf(20.00), null, null);
  }
}
