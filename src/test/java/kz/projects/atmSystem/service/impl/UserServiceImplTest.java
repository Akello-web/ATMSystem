package kz.projects.atmSystem.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import kz.projects.atmSystem.dto.AuthRequest;
import kz.projects.atmSystem.dto.UserDTO;
import kz.projects.atmSystem.model.Permissions;
import kz.projects.atmSystem.model.User;
import kz.projects.atmSystem.repositories.PermissionRepository;
import kz.projects.atmSystem.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PermissionRepository permissionRepository;

  @Mock
  private MyUserDetailsService myUserDetailsService;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  public void testRegister_NewUser_Success() {
    User user = new User();
    user.setAccountNumber("KZTest");
    user.setPin("1111");

    when(userRepository.findByAccountNumber("KZTest")).thenReturn(Optional.empty());
    when(permissionRepository.findByRole("ROLE_USER")).thenReturn(new Permissions());
    when(userRepository.save(any(User.class))).thenReturn(user);

    UserDTO result = userService.register(user);

    assertNotNull(result);
    assertEquals("KZTest", result.accountNumber());
    assertEquals(0.0, result.balance());
    assertEquals(1, result.permissionList().size());
  }

  @Test
  public void testRegister_ExistingUser_ExceptionThrown() {
    User existingUser = new User();
    existingUser.setAccountNumber("KZ01");

    when(userRepository.findByAccountNumber("KZ01")).thenReturn(Optional.of(existingUser));

    assertThrows(IllegalArgumentException.class, () -> userService.register(existingUser));
  }

  @Test
  public void testLoginUser_ValidCredentials_Success() {
    AuthRequest request = new AuthRequest("KZ01", "1111");

    User user = new User();
    user.setAccountNumber("KZ01");
    user.setPin("$2a$10$7QF9KzHJyD6u12Hfy7h4YOzq16Lv7O5xJ8/3h3dc3IzT0HBJSqG92"); // Mock hashed password

    when(myUserDetailsService.loadUserByUsername("KZ01")).thenReturn(user);
    when(passwordEncoder.matches("1111", user.getPassword())).thenReturn(true);

    UserDTO result = userService.loginUser(request);

    assertNotNull(result);
    assertEquals("KZ01", result.accountNumber());
    assertNull(result.permissionList());
  }

  @Test
  public void testLoginUser_InvalidCredentials_ExceptionThrown() {
    AuthRequest request = new AuthRequest("KZ01", "wrongpassword");

    User user = new User();
    user.setAccountNumber("KZ01");
    user.setPin("$2a$10$7QF9KzHJyD6u12Hfy7h4YOzq16Lv7O5xJ8/3h3dc3IzT0HBJSqG92"); // Mock hashed password

    when(myUserDetailsService.loadUserByUsername("KZ01")).thenReturn(user);
    when(passwordEncoder.matches("wrongpassword", user.getPassword())).thenReturn(false);

    assertThrows(UsernameNotFoundException.class, () -> userService.loginUser(request));
  }

  @Test
  public void testGetCurrentSessionUser_AuthenticatedUser_ReturnsUser() {
    User mockUser = new User();
    mockUser.setAccountNumber("KZ01");
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(mockUser, null);

    SecurityContextHolder.getContext().setAuthentication(auth);

    User result = userService.getCurrentSessionUser();

    assertNotNull(result);
    assertEquals("KZ01", result.getAccountNumber());
  }

  @Test
  public void testGetCurrentUserBalance_AuthenticatedUser_ReturnsBalance() {
    User mockUser = new User();
    mockUser.setAccountNumber("KZ01");
    mockUser.setBalance(1000.0);
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(mockUser, null);

    SecurityContextHolder.getContext().setAuthentication(auth);

    Double result = userService.getCurrentUserBalance();

    assertNotNull(result);
    assertEquals(1000.0, result); // Assuming balance set correctly
  }

  @Test
  public void testGetUser_ExistingUser_ReturnsUser() {
    User mockUser = new User();
    mockUser.setAccountNumber("KZ01");

    when(userRepository.findByAccountNumber("KZ01")).thenReturn(Optional.of(mockUser));

    User result = userService.getUser("KZ01");

    assertNotNull(result);
    assertEquals("KZ01", result.getAccountNumber());
  }

  @Test
  public void testGetUser_NonExistingUser_ExceptionThrown() {
    when(userRepository.findByAccountNumber("KZ02")).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> userService.getUser("KZ02"));
  }

  @Test
  public void testGetUsers_ReturnsListOfUsers() {
    User user1 = new User();
    user1.setAccountNumber("KZ01");

    User user2 = new User();
    user2.setAccountNumber("KZ02");

    when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

    List<UserDTO> result = userService.getUsers();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("KZ01", result.get(0).accountNumber());
    assertEquals("KZ02", result.get(1).accountNumber());
  }
}

