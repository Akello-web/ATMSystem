package kz.projects.atmSystem.service;

import kz.projects.atmSystem.dto.AuthRequest;
import kz.projects.atmSystem.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
  void saveUser(User user);
  User register(User user);
  UserDetails loginUser(AuthRequest request);
  User getCurrentSessionUser();
  Double getCurrentUserBalance();
  User getUser(String accountNumber);
}
