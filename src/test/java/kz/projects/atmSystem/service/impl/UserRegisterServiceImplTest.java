package kz.projects.atmSystem.service.impl;

import kz.projects.atmSystem.model.Permissions;
import kz.projects.atmSystem.model.User;
import kz.projects.atmSystem.repositories.PermissionRepository;
import kz.projects.atmSystem.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRegisterServiceImplTest {
  @Mock
  private UserRepository userRepository;

  @Mock
  private PermissionRepository permissionRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserServiceImpl userService;

  @BeforeEach
  public void setUp() {
    lenient().when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

    Permissions defaultPermission = new Permissions();
    defaultPermission.setId(1L);
    defaultPermission.setRole("ROLE_USER");
    lenient().when(permissionRepository.findByRole("ROLE_USER")).thenReturn(defaultPermission);
  }

  @Test
  public void testAddUserSuccess() {
    User newUser = new User();
    newUser.setId(1L);
    newUser.setAccountNumber("KZ0123456789");
    newUser.setPin("1234");
    newUser.setBalance(0.0);

    when(userRepository.findByAccountNumber(newUser.getAccountNumber())).thenReturn(null);

    when(userRepository.save(newUser)).thenReturn(newUser);

    User addedUser = userService.addUser(newUser);

    assertThat(addedUser).isNotNull();
    assertThat(addedUser.getId()).isNotNull();
    assertThat(addedUser.getAccountNumber()).isEqualTo(newUser.getAccountNumber());
    assertThat(addedUser.getPin()).isEqualTo("encodedPassword"); // Ensure password encoding is applied
    assertThat(addedUser.getBalance()).isEqualTo(0.0);
    assertThat(addedUser.getPermissionList()).hasSize(1);
    assertThat(addedUser.getPermissionList().get(0).getRole()).isEqualTo("ROLE_USER");
  }

  @Test
  public void testAddUserDuplicateAccountNumber() {
    User existingUser = new User();
    existingUser.setAccountNumber("KZ0123456789");

    when(userRepository.findByAccountNumber(existingUser.getAccountNumber())).thenReturn(existingUser);

    User addedUser = userService.addUser(existingUser);

    assertThat(addedUser).isNull();
  }
}
