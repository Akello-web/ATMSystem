package kz.projects.atmSystem.service.impl;

import kz.projects.atmSystem.model.User;
import kz.projects.atmSystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> userOptional = userRepository.findByAccountNumber(username);
    if (userOptional.isEmpty()){
      throw new UsernameNotFoundException("Username not found");
    }

    return userOptional.get();
  }
}
