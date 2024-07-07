package kz.projects.atmSystem.service;

public interface TransactionService {
  void deposit(Double amount);

  void withdrawAmount(Double amount);

  void transferAmount(String accountNumber ,Double amount);


}
