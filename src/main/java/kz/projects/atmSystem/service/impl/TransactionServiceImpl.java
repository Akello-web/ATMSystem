package kz.projects.atmSystem.service.impl;

import jakarta.transaction.Transactional;
import kz.projects.atmSystem.model.MyUserDetails;
import kz.projects.atmSystem.model.User;
import kz.projects.atmSystem.service.TransactionService;
import kz.projects.atmSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final UserService userService;

  @Override
  @Transactional
  public void deposit(Double amount) {
    MyUserDetails currentUser = userService.getCurrentSessionUser();
    currentUser.getUser().setBalance(userService.getCurrentUserBalance() + amount);
    userService.saveUser(currentUser.getUser());
  }

  @Override
  public void withdrawAmount(Double amount) {
    MyUserDetails currentUser = userService.getCurrentSessionUser();
    currentUser.getUser().setBalance(userService.getCurrentUserBalance() - amount);
    userService.saveUser(currentUser.getUser());
  }

  @Override
  public void transferAmount(String accountNumber, Double amount) {
    User userToTransfer = userService.getUser(accountNumber);
    Double userToTransferAmount = userToTransfer.getBalance();
    userToTransfer.setBalance(userToTransferAmount + amount);
    userService.saveUser(userToTransfer);
    withdrawAmount(amount);

  }

}
