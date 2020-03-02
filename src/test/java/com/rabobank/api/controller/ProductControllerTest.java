package com.rabobank.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabobank.api.data.entity.Product;
import com.rabobank.api.exception.ProductNotFoundException;
import com.rabobank.api.exception.handler.ControllerAdvice;
import com.rabobank.api.service.ProductService;
import org.hamcrest.core.StringContains;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

  @Mock
  private ProductService service;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    ProductController controller = new ProductController(service);
    this.mockMvc = standaloneSetup(controller).setControllerAdvice(new ControllerAdvice()).build();
  }

  @Test
  public void test_products_OK() throws Exception {
    //arrange
    when(service.getAllProducts()).thenReturn(new ArrayList<>());

    //Act and assert
    this.mockMvc.perform(get(constructUrl(null))
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
  }

  @Test
  public void test_productById_OK() throws Exception {
    //arrange
    when(service.getProduct(anyInt()))
        .thenReturn(new Product());

    //Act and assert
    this.mockMvc.perform(get(constructUrl("10"))
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
  }

  @Test
  public void test_productById_Not_Found() throws Exception {
    //arrange
    String notFoundMessage = "requested product is not found";
    when(service.getProduct(anyInt())).thenThrow(new ProductNotFoundException(notFoundMessage));

    //Act and assert
    this.mockMvc.perform(get(constructUrl("10"))
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound())
        .andExpect(content().string(notFoundMessage));
  }

  @Test
  public void test_update_product_OK() throws Exception {
    //arrange
    when(service.updateProduct(anyInt(), any()))
        .thenReturn(createProduct());

    //Act and assert
    this.mockMvc.perform(put(constructUrl("10"))
        .content(createRequestBody())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
        .andExpect(content().string(new StringContains(false, "product1")));
  }

  @Test
  public void test_add_product_OK() throws Exception {
    //arrange
    when(service.addProduct(any()))
        .thenReturn(createProduct());

    //Act and assert
    this.mockMvc.perform(post(constructUrl(null))
        .content(createRequestBody())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
        .andExpect(content().string(new StringContains(false, "product1")));
  }

  private String createRequestBody() throws JsonProcessingException {
    Product product = createProduct();
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(product);
  }

  private Product createProduct() {
    return new Product(10, "product1", BigDecimal.valueOf(100.00), null, null);
  }


  private String constructUrl(String id) {
    String path = "/api/products";
    if (id == null) {
      return path;
    }
    return path + "/" + id;
  }
}
