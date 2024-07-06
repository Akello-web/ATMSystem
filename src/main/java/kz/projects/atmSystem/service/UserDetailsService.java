package kz.projects.atmSystem.service;

import kz.projects.atmSystem.model.User;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserDetailsService {
  UserDetails loadUserByUsername(String username);

  User addUser(User user);

  UserDetails loginUser(String accountNumber, String pin);

  void logout();

}
