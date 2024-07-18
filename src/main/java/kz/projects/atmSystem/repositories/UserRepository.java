package kz.projects.atmSystem.repositories;

import kz.projects.atmSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//  User findByAccountNumber(String accountNumber);
  Optional<User> findByAccountNumber(String accountNumber);

}
