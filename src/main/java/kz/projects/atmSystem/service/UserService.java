package kz.projects.atmSystem.service;

import kz.projects.atmSystem.model.MyUserDetails;
import kz.projects.atmSystem.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
  UserDetails loginUser(String accountNumber, String pin);

  User addUser(User user);
  MyUserDetails getCurrentSessionUser();
}
