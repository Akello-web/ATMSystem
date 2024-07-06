package kz.projects.atmSystem.repositories;

import kz.projects.atmSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByAccountNumber(String accountNumber);

}
