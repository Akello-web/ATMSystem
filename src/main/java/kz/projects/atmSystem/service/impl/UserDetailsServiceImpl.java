package kz.projects.atmSystem.service.impl;

import kz.projects.atmSystem.model.Permissions;
import kz.projects.atmSystem.model.User;
import kz.projects.atmSystem.repositories.PermissionRepository;
import kz.projects.atmSystem.repositories.UserRepository;
import kz.projects.atmSystem.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final PermissionRepository permissionRepository;


  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = userRepository.findByAccountNumber(username);
    if (user != null) {
      return user;
    } else {
      throw new UsernameNotFoundException(username);
    }
  }

  @Override
  public UserDetails loginUser(String accountNumber, String pin) {
      UserDetails userDetails = loadUserByUsername(accountNumber);
      if (passwordEncoder.matches(pin, userDetails.getPassword())) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(accountNumber, pin,
                        userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return userDetails;
      } else {
        throw new UsernameNotFoundException("Invalid credentials");
      }
  }

  @Override
  public void logout() {
      SecurityContextHolder.clearContext();
  }

  @Override
  public User addUser(User user) {
    User checkUser = userRepository.findByAccountNumber(user.getAccountNumber());
    if(checkUser==null){
      user.setPin(passwordEncoder.encode(user.getPassword()));
      Permissions defaultPermission = permissionRepository.findByRole("ROLE_USER");

      if (defaultPermission == null) {
        defaultPermission = new Permissions();
        defaultPermission.setRole("ROLE_USER");
        defaultPermission = permissionRepository.save(defaultPermission);
      }
      user.setPermissionList(Collections.singletonList(defaultPermission));
      return userRepository.save(user);
    }
    else {
      return null;
    }
  }


}
