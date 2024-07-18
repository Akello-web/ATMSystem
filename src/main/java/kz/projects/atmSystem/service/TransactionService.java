package kz.projects.atmSystem.service;

import kz.projects.atmSystem.dto.TransactionDTO;
import kz.projects.atmSystem.dto.TransactionRequest;

import java.util.List;

public interface TransactionService {
  void deposit(Double amount);

  void withdrawAmount(Double amount);

  void transferAmount(TransactionRequest request);

  List<TransactionDTO> getTransactions();

}
