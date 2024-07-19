package kz.projects.atmSystem.dto;

import kz.projects.atmSystem.model.Permissions;


import java.util.List;


public record UserDTO(
        Long id,
        String accountNumber,
        double balance,
        List<Permissions> permissionList
) {}
