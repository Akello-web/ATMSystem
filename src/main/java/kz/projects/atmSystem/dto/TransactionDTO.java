package kz.projects.atmSystem.dto;

import kz.projects.atmSystem.model.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDTO {
  private Long id;

  private TransactionType type;

  private double amount;

  private LocalDateTime date;

  private UserDTO user;
}
