package kz.projects.atmSystem.service.impl;

import kz.projects.atmSystem.model.MyUserDetails;
import kz.projects.atmSystem.model.Permissions;
import kz.projects.atmSystem.model.User;
import kz.projects.atmSystem.repositories.PermissionRepository;
import kz.projects.atmSystem.repositories.UserRepository;
import kz.projects.atmSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final PermissionRepository permissionRepository;

  private final MyUserDetailsService myUserDetailsService;

  private final PasswordEncoder passwordEncoder;

  @Override
  public void saveUser(User user) {
    userRepository.save(user);
  }

  @Override
  public UserDetails loginUser(String accountNumber, String pin) {
    UserDetails userDetails = myUserDetailsService.loadUserByUsername(accountNumber);
    if (passwordEncoder.matches(pin, userDetails.getPassword())) {
      UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(userDetails, null,
                      userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      return userDetails;
    } else {
      throw new UsernameNotFoundException("Invalid credentials");
    }
  }

  @Override
  public User addUser(User user) {
    User checkUser = userRepository.findByAccountNumber(user.getAccountNumber());
    if(checkUser==null){
      user.setPin(passwordEncoder.encode(user.getPin()));
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

  @Override
  public MyUserDetails getCurrentSessionUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
      MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
      if (myUserDetails != null) {
        return myUserDetails;
      }
    }
    return null;
  }

  @Override
  public Double getCurrentUserBalance() {
    MyUserDetails currentUser = getCurrentSessionUser();
    return currentUser.getUser().getBalance();
  }

  @Override
  public User getUser(String accountNumber) {
    return userRepository.findByAccountNumber(accountNumber);
  }


}
