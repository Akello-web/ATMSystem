package kz.projects.atmSystem.service;

import kz.projects.atmSystem.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {
  void deposit(Double amount);

  void withdrawAmount(Double amount);

  void transferAmount(String accountNumber ,Double amount);

  List<TransactionDTO> getTransactions();

}
