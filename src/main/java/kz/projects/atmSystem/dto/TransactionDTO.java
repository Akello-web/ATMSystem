package kz.projects.atmSystem.dto;

import kz.projects.atmSystem.model.TransactionType;
import java.time.LocalDateTime;

public record TransactionDTO(
        Long id,
        TransactionType type,
        double amount,
        LocalDateTime date,
        UserDTO user
) {}
