package kz.projects.atmSystem.service.impl;

import jakarta.transaction.Transactional;
import kz.projects.atmSystem.model.MyUserDetails;
import kz.projects.atmSystem.model.Transaction;
import kz.projects.atmSystem.model.TransactionType;
import kz.projects.atmSystem.model.User;
import kz.projects.atmSystem.repositories.TransactionRepository;
import kz.projects.atmSystem.service.TransactionService;
import kz.projects.atmSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final UserService userService;

  private final TransactionRepository transactionRepository;

  @Override
  @Transactional
  public void deposit(Double amount) {
    MyUserDetails currentUser = userService.getCurrentSessionUser();
    currentUser.getUser().setBalance(userService.getCurrentUserBalance() + amount);
    userService.saveUser(currentUser.getUser());

    Transaction transaction = new Transaction();
    transaction.setType(TransactionType.DEPOSIT);
    transaction.setAmount(amount);
    transaction.setDate(LocalDateTime.now());
    transaction.setUser(currentUser.getUser());
    transactionRepository.save(transaction);
  }

  @Override
  public void withdrawAmount(Double amount) {
    MyUserDetails currentUser = userService.getCurrentSessionUser();
    currentUser.getUser().setBalance(userService.getCurrentUserBalance() - amount);
    userService.saveUser(currentUser.getUser());

    Transaction transaction = new Transaction();
    transaction.setType(TransactionType.WITHDRAWAL);
    transaction.setAmount(amount);
    transaction.setDate(LocalDateTime.now());
    transaction.setUser(currentUser.getUser());
    transactionRepository.save(transaction);
  }

  @Override
  public void transferAmount(String accountNumber, Double amount) {
    User userToTransfer = userService.getUser(accountNumber);
    Double userToTransferAmount = userToTransfer.getBalance();
    userToTransfer.setBalance(userToTransferAmount + amount);
    userService.saveUser(userToTransfer);
    withdrawAmount(amount);

    MyUserDetails currentUser = userService.getCurrentSessionUser();
    Transaction transaction = new Transaction();
    transaction.setType(TransactionType.TRANSFER);
    transaction.setAmount(amount);
    transaction.setDate(LocalDateTime.now());
    transaction.setUser(currentUser.getUser());
    transactionRepository.save(transaction);

  }

  @Override
  public List<Transaction> getTransactions() {
    return transactionRepository.findAll();
  }

}
