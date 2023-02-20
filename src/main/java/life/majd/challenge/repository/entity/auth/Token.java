package life.majd.challenge.repository.entity.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public UUID id;
  @Column(name = "token", unique = true)
  public String token;
  @Enumerated(EnumType.STRING)
  public TokenType tokenType = TokenType.BEARER;
  public boolean revoked;
  public boolean expired;

  @ManyToOne
  @JoinColumn(name = "user_id")
  public User user;
}
