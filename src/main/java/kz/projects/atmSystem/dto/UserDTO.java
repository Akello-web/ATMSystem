package kz.projects.atmSystem.dto;

import kz.projects.atmSystem.model.Permissions;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
  private Long id;

  private String accountNumber;

  private double balance;

  private List<Permissions> permissionList;
}
