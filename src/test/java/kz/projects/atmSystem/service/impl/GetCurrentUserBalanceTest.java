package kz.projects.atmSystem.service.impl;
import kz.projects.atmSystem.model.MyUserDetails;
import kz.projects.atmSystem.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class GetCurrentUserBalanceTest {

  @Mock
  private MyUserDetailsService myUserDetailsService;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  public void testGetCurrentUserBalance() {
    User user = new User();
    user.setAccountNumber("testUser");
    user.setPin("encodedPin");
    user.setBalance(100.0);
    MyUserDetails userDetails = new MyUserDetails(user);

    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(authentication);

    lenient().when(myUserDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);

    Double resultBalance = userService.getCurrentUserBalance();

    assertThat(resultBalance).isEqualTo(100.0);
  }
}
