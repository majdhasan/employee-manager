package life.majd.challenge.repository;

import java.util.Optional;
import java.util.UUID;
import life.majd.challenge.repository.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByUsername(String username);

}
