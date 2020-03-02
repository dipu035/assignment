package com.rabobank.api.exception.handler;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.rabobank.api.exception.ProductNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class ControllerAdviceTest {

  private ControllerAdvice advice = new ControllerAdvice();


  @Test
  public void testHandleProductNotFoundException() {
    //arrange
    String message = "product not found.";
    //act
    ResponseEntity<String> responseEntity = advice
        .handleProductNotFoundException(new ProductNotFoundException(message));

    //assert
    assertThat(responseEntity.getStatusCode(), equalTo(NOT_FOUND));
    assertThat(responseEntity.getBody(), equalTo(message));

  }
}
