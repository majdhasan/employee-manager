package life.majd.challenge.repository.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
@ToString
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  @Email
  @Column(name = "email", nullable = false, unique = true)
  private String email;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "birthday")
  private LocalDate birthday;
  @ElementCollection
  @CollectionTable(name = "hobby", joinColumns = @JoinColumn(name = "employee_id"))
  @Column(name = "hobby")
  private Set<String> hobbies;

}
