package life.majd.challenge.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import life.majd.challenge.domain.service.exception.EmployeeNotFoundException;
import life.majd.challenge.repository.EmployeeRepository;
import life.majd.challenge.repository.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DisplayName("Employee Service Test")
@ExtendWith(SpringExtension.class)
public class EmployeeServiceImplTest {

  @Mock
  private EmployeeRepository employeeRepository;
  @Mock
  private KafkaTemplate<String, Object> kafkaTemplate;
  @InjectMocks
  private EmployeeServiceImpl employeeService;
  private Employee employee;

  @BeforeEach
  public void setup() {
    employee = Employee.builder()
        .id(UUID.randomUUID())
        .name("Test Employee")
        .email("test@example.com")
        .birthday(LocalDate.now())
        .hobbies(Set.of("reading", "swimming"))
        .build();
  }

  @Test
  @DisplayName("Should retrieve all employees")
  public void shouldRetrieveAllEmployees() {
    List<Employee> employees = Arrays.asList(employee);
    when(employeeRepository.findAll()).thenReturn(employees);
    List<Employee> result = employeeService.getAllEmployees();
    verify(employeeRepository, times(1)).findAll();
    assertThat(result.size() == 1 && result.get(0).equals(employee)).isTrue();
  }

  @Test
  @DisplayName("Should retrieve an employee by uuid")
  public void shouldRetrieveEmployeeByUuid() {
    UUID uuid = employee.getId();
    when(employeeRepository.findById(uuid)).thenReturn(Optional.of(employee));
    Optional<Employee> result = employeeService.getEmployeeByUuid(uuid);
    verify(employeeRepository, times(1)).findById(uuid);
    assertThat(result.isPresent() && result.get().equals(employee)).isTrue();
  }

  @Test
  @DisplayName("Should save a new employee")
  public void shouldSaveNewEmployee() {
    Employee savedEmployee = Employee.builder()
        .id(UUID.randomUUID())
        .name("Saved Employee")
        .email("saved@example.com")
        .birthday(LocalDate.now())
        .hobbies(Set.of("hiking", "cooking"))
        .build();
    when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

    Employee result = employeeService.saveEmployee(savedEmployee);
    verify(employeeRepository, times(1)).save(savedEmployee);
    verify(kafkaTemplate, times(1)).send(eq("employee.created"), eq(savedEmployee));

    assertThat(result).isEqualTo(savedEmployee);
  }

  @Test
  @DisplayName("Should throw DuplicateKeyException when attempting to save an employee with duplicate email")
  public void shouldThrowDuplicateKeyExceptionOnSave() {
    Employee savedEmployee = Employee.builder()
        .id(UUID.randomUUID())
        .name("Saved Employee")
        .email("duplicate@example.com")
        .birthday(LocalDate.now())
        .hobbies(Set.of("hiking", "cooking"))
        .build();
    when(employeeRepository.save(any(Employee.class))).thenThrow(
        DataIntegrityViolationException.class);
    assertThrows(DuplicateKeyException.class, () -> employeeService.saveEmployee(savedEmployee));
  }

  @Test
  @DisplayName("Should update an employee")
  public void shouldUpdateEmployee() {
    Employee updatedEmployee = Employee.builder()
        .id(employee.getId())
        .name("Updated Employee")
        .email("updated@example.com")
        .birthday(LocalDate.now())
        .hobbies(Set.of("running", "swimming"))
        .build();
    ArgumentCaptor<Employee> argumentCaptor = ArgumentCaptor.forClass(Employee.class);

    when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
    when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

    Employee result = employeeService.updateEmployee(employee.getId(), updatedEmployee);

    verify(employeeRepository, times(1)).findById(employee.getId());
    verify(employeeRepository, times(1)).save(argumentCaptor.capture());
    verify(kafkaTemplate, times(1)).send(eq("employee.updated"), eq(updatedEmployee));

    Employee capturedEmployee = argumentCaptor.getValue();
    assertThat(capturedEmployee.getEmail().equals("updated@example.com")).isTrue();
    assertThat(result).isEqualTo(updatedEmployee);
  }

  @Test
  @DisplayName("Should throw EmployeeNotFoundException when attempting to update a non-existent employee")
  public void shouldThrowEmployeeNotFoundExceptionOnUpdate() {
    UUID nonExistentUuid = UUID.randomUUID();
    when(employeeRepository.findById(nonExistentUuid)).thenReturn(Optional.empty());
    assertThrows(EmployeeNotFoundException.class,
        () -> employeeService.updateEmployee(nonExistentUuid, new Employee()));
  }

  @Test
  @DisplayName("Should delete the employee")
  void testDeleteEmployee() {

    Employee employee = new Employee();
    employee.setId(UUID.randomUUID());

    employeeService.deleteEmployee(employee);

    verify(employeeRepository, times(1)).delete(employee);
    verify(kafkaTemplate, times(1)).send(eq("employee.deleted"), eq(employee.getId()));
  }


}