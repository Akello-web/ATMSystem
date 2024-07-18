package kz.projects.atmSystem.service;

import kz.projects.atmSystem.dto.AuthRequest;
import kz.projects.atmSystem.dto.UserDTO;
import kz.projects.atmSystem.model.User;

public interface UserService {
  void saveUser(User user);
  UserDTO register(User user);
  UserDTO loginUser(AuthRequest request);
  User getCurrentSessionUser();
  Double getCurrentUserBalance();
  User getUser(String accountNumber);
}
