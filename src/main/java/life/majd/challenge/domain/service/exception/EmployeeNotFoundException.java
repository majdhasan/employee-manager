package life.majd.challenge.domain.service.exception;

public class EmployeeNotFoundException extends RuntimeException{

  public EmployeeNotFoundException(String message) {
    super(message);
  }
}
