package kz.projects.atmSystem.repositories;

import kz.projects.atmSystem.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
  UserModel findByAccountNumber(String accountNumber);

  UserModel findByPin(String pin);
}
