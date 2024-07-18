package kz.projects.atmSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "t_users")
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String accountNumber;

  private String pin;

  private double balance;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "user_permissions",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "permission_id")
  )
  private List<Permissions> permissionList;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.permissionList;
  }

  @Override
  public String getPassword() {
    return this.pin;
  }

  @Override
  public String getUsername() {
    return this.accountNumber;
  }
}
