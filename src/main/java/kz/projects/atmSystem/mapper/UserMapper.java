package kz.projects.atmSystem.mapper;

import kz.projects.atmSystem.dto.UserDTO;
import kz.projects.atmSystem.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  public static UserDTO toDto(User user) {
    if (user == null) {
      return null;
    }

    return new UserDTO(
            user.getId(),
            user.getAccountNumber(),
            user.getBalance(),
            user.getPermissionList()
    );
  }
}
