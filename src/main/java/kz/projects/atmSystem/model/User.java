package kz.projects.atmSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "t_users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String accountNumber;

  private String pin;

  private double balance;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Permissions> permissionList;
}
