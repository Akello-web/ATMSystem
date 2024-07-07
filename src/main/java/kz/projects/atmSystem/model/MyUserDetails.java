package kz.projects.atmSystem.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@RequiredArgsConstructor
public class MyUserDetails implements UserDetails {

  final User user;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getPermissionList();
  }

  @Override
  public String getPassword() {
    return user.getPin();
  }

  @Override
  public String getUsername() {
    return user.getAccountNumber();
  }
}
