package life.majd.challenge.domain.service.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<Object> handleDuplicateKeyException(DuplicateKeyException ex) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("message", ex.getMessage());
    errorResponse.put("status", HttpStatus.CONFLICT.value());
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(EmployeeNotFoundException.class)
  public ResponseEntity<Object> handleNotFoundException(EmployeeNotFoundException ex) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("message", ex.getMessage());
    errorResponse.put("status", HttpStatus.NOT_FOUND.value());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }
}