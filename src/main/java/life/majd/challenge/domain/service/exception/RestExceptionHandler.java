package life.majd.challenge.domain.service.exception;

import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<Object> handleDuplicateKeyException(DuplicateKeyException ex) {
    log.warn(ex.getMessage());
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("message", ex.getMessage());
    errorResponse.put("status", HttpStatus.CONFLICT.value());
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(EmployeeNotFoundException.class)
  public ResponseEntity<Object> handleNotFoundException(EmployeeNotFoundException ex) {
    log.warn(ex.getMessage());
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("message", ex.getMessage());
    errorResponse.put("status", HttpStatus.NOT_FOUND.value());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
    log.warn(ex.getMessage());
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("message", ex.getMessage());
    errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }
}