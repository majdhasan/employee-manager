package life.majd.challenge.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import life.majd.challenge.domain.service.exception.EmployeeNotFoundException;
import life.majd.challenge.repository.EmployeeRepository;
import life.majd.challenge.repository.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private KafkaTemplate<String, Object> kafkaTemplate;

  @Override
  public List<Employee> getAllEmployees() {
    log.info("Retrieving all employees");
    return employeeRepository.findAll();
  }

  @Override
  public Optional<Employee> getEmployeeByUuid(UUID uuid) {
    log.info("Retrieving employee with uuid: {}", uuid);
    return employeeRepository.findById(uuid);
  }

  @Override
  public Employee saveEmployee(Employee employee) {
    try {
      log.info("Attempting saving new employee: {}", employee.toString());

      Employee savedEmployee = employeeRepository.save(employee);
      // Send message to kafka topic
      kafkaTemplate.send("employee.created", savedEmployee);
      return savedEmployee;

    } catch (
        DataIntegrityViolationException ex) {
      throw new DuplicateKeyException("User with email " + employee.getEmail() + " already exists");
    }

  }

  @Override
  public Employee updateEmployee(UUID uuid, Employee employee) {
    Optional<Employee> optionalEmployee = getEmployeeByUuid(uuid);
    if (optionalEmployee.isPresent()) {
      Employee foundEmployee = optionalEmployee.get();
      foundEmployee.setEmail(employee.getEmail());
      foundEmployee.setName(employee.getName());
      foundEmployee.setBirthday(employee.getBirthday());
      foundEmployee.setHobbies(employee.getHobbies());
      Employee updatedEmployee = saveEmployee(foundEmployee);

      // Send message to kafka topic
      kafkaTemplate.send("employee.updated", updatedEmployee);
      return updatedEmployee;
    } else {
      throw new EmployeeNotFoundException("Employee with id: " + uuid + " not found");
    }
  }

  @Override
  public void deleteEmployee(Employee employee) {
    log.info("Attempting to delete employee: {}", employee.toString());
    employeeRepository.delete(employee);
    // Send message to kafka topic
    kafkaTemplate.send("employee.deleted", employee.getId());
  }
}