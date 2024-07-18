package kz.projects.atmSystem.mapper;

import kz.projects.atmSystem.dto.UserDTO;
import kz.projects.atmSystem.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  public static UserDTO toDto(User user) {
    if (user == null){
      return null;
    }

    UserDTO userDTO = new UserDTO();
    userDTO.setId(user.getId());
    userDTO.setAccountNumber(user.getAccountNumber());
    userDTO.setBalance(user.getBalance());
    userDTO.setPermissionList(user.getPermissionList());

    return userDTO;
  }
}
