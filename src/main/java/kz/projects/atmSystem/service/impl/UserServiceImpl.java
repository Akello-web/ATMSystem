package kz.projects.atmSystem.service.impl;

import kz.projects.atmSystem.dto.AuthRequest;
import kz.projects.atmSystem.dto.UserDTO;
import kz.projects.atmSystem.mapper.UserMapper;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
  public UserDTO register(User user) {
    Optional<User> checkUser = userRepository.findByAccountNumber(user.getAccountNumber());

    if (checkUser.isPresent()){
      throw new IllegalArgumentException("User with this email already exists.");
    }

    user.setPin(passwordEncoder.encode(user.getPin()));
    Permissions defaultPermission = permissionRepository.findByRole("ROLE_USER");

    if (defaultPermission == null) {
      defaultPermission = new Permissions();
      defaultPermission.setRole("ROLE_USER");
      defaultPermission = permissionRepository.save(defaultPermission);
    }

    user.setPermissionList(Collections.singletonList(defaultPermission));
    return UserMapper.toDto(userRepository.save(user));
  }


  @Override
  public UserDTO loginUser(AuthRequest request) {
    UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getUsername());
    if (passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
      UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(userDetails, null,
                      userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      return UserMapper.toDto((User) userDetails);
    } else {
      throw new UsernameNotFoundException("Invalid credentials");
    }
  }


  @Override
  public User getCurrentSessionUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
      return (User) authentication.getPrincipal();
    }
    return null;
  }

  @Override
  public Double getCurrentUserBalance() {
    User currentUser = getCurrentSessionUser();
    return currentUser.getBalance();
  }

  @Override
  public User getUser(String accountNumber) {
    return userRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new IllegalArgumentException("Theres no user with this account number"));
  }

  @Override
  public List<UserDTO> getUsers() {
    List<User> users = userRepository.findAll();
    return users.stream()
            .map(UserMapper::toDto)
            .collect(Collectors.toList());
  }

  @Override
  public String updatePassword(String oldPin, String newPin) {
    User currentUser = getCurrentSessionUser();
    if(passwordEncoder.matches(oldPin, currentUser.getPassword())){
      currentUser.setPin(passwordEncoder.encode(newPin));
      userRepository.save(currentUser);
      return "Password successfully changed";
    }
    return "Password mismatch";
  }
}
