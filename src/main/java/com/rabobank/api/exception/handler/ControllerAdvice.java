package com.rabobank.api.exception.handler;


import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.rabobank.api.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler({ProductNotFoundException.class})
  public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e) {
    return constructResponseEntity(NOT_FOUND, e);
  }

  private ResponseEntity<String> constructResponseEntity(HttpStatus status, Exception e) {
    log.error("Exception : ", e);
    return ResponseEntity.status(status).body(e.getMessage());
  }

}
