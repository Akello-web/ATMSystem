package kz.projects.atmSystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
  private String account;
  private Double amount;
}
