package life.majd.challenge.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import life.majd.challenge.repository.entity.Employee;

public interface EmployeeService {

  List<Employee> getAllEmployees();

  Optional<Employee> getEmployeeByUuid(UUID uuid);

  Employee saveEmployee(Employee employee);

  Employee updateEmployee(UUID uuid, Employee employee);

  void deleteEmployee(Employee employee);
}
