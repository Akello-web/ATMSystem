package kz.projects.atmSystem.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
  @Mock
  private MyUserDetailsService myUserDetailsService;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserServiceImpl userService;

  private UserDetails userDetails;

  @BeforeEach
  public void setUp() {
    userDetails = mock(UserDetails.class);
    lenient().when(userDetails.getPassword()).thenReturn("encodedPin");
    lenient().when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());
  }

  @Test
  public void testLoginUserSuccess() {
    String accountNumber = "123456";
    String pin = "correctPin";

    when(myUserDetailsService.loadUserByUsername(accountNumber)).thenReturn(userDetails);
    when(passwordEncoder.matches(pin, userDetails.getPassword())).thenReturn(true);

    UserDetails result = userService.loginUser(accountNumber, pin);

    assertThat(result).isEqualTo(userDetails);
    assertThat(SecurityContextHolder.getContext().getAuthentication()).isInstanceOf(UsernamePasswordAuthenticationToken.class);
    assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isEqualTo(userDetails);
  }

  @Test
  public void testLoginUserInvalidPin() {
    String accountNumber = "123456";
    String pin = "wrongPin";

    when(myUserDetailsService.loadUserByUsername(accountNumber)).thenReturn(userDetails);
    when(passwordEncoder.matches(pin, userDetails.getPassword())).thenReturn(false);

    assertThatThrownBy(() -> userService.loginUser(accountNumber, pin))
            .isInstanceOf(UsernameNotFoundException.class)
            .hasMessageContaining("Invalid credentials");
  }

  @Test
  public void testLoginUserAccountNotFound() {
    String accountNumber = "unknown";
    String pin = "anyPin";

    when(myUserDetailsService.loadUserByUsername(accountNumber)).thenThrow(new UsernameNotFoundException("Account not found"));

    assertThatThrownBy(() -> userService.loginUser(accountNumber, pin))
            .isInstanceOf(UsernameNotFoundException.class)
            .hasMessageContaining("Account not found");
  }
}
