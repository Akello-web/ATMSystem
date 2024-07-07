package kz.projects.atmSystem.service.impl;

import kz.projects.atmSystem.model.MyUserDetails;
import kz.projects.atmSystem.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCurrentUserServiceImplTest {
  @Mock
  private Authentication authentication;

  @InjectMocks
  private UserServiceImpl myUserDetailsService;

  @Test
  public void testGetCurrentSessionUserAuthenticated() {
    User user = new User();
    user.setAccountNumber("testUser");
    user.setPin("encodedPin");
    MyUserDetails userDetails = new MyUserDetails(user);
    when(authentication.getPrincipal()).thenReturn(userDetails);

    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    securityContext.setAuthentication(authentication);
    SecurityContextHolder.setContext(securityContext);

    MyUserDetails currentUser = myUserDetailsService.getCurrentSessionUser();

    assertThat(currentUser).isNotNull();
    assertThat(currentUser.getUsername()).isEqualTo("testUser");
  }

  @Test
  public void testGetCurrentSessionUserPrincipalNull() {
    when(authentication.getPrincipal()).thenReturn(null);

    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    securityContext.setAuthentication(authentication);
    SecurityContextHolder.setContext(securityContext);

    MyUserDetails currentUser = myUserDetailsService.getCurrentSessionUser();

    assertThat(currentUser).isNull();
  }
}
