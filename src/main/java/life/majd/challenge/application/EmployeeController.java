package life.majd.challenge.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import life.majd.challenge.domain.service.EmployeeService;
import life.majd.challenge.repository.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/employees")
@RestController
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @GetMapping
  public ResponseEntity<List<Employee>> getAllEmployees() {
    return ResponseEntity.ok(employeeService.getAllEmployees());
  }

  @PostMapping
  public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
    return ResponseEntity.ok(employeeService.saveEmployee(employee));
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<Employee> getEmployeeByUuid(@PathVariable UUID uuid) {
    Optional<Employee> optionalEmployee = employeeService.getEmployeeByUuid(uuid);
    return optionalEmployee.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{uuid}")
  public ResponseEntity<Employee> updateEmployee(@PathVariable UUID uuid,
      @RequestBody Employee updatedEmployee) {
    return ResponseEntity.ok(employeeService.updateEmployee(uuid, updatedEmployee));
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable UUID uuid) {
    Optional<Employee> optionalEmployee = employeeService.getEmployeeByUuid(uuid);
    if (optionalEmployee.isPresent()) {
      Employee employee = optionalEmployee.get();
      employeeService.deleteEmployee(employee);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}