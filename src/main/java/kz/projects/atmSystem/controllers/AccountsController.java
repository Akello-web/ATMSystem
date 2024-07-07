package kz.projects.atmSystem.controllers;

import kz.projects.atmSystem.dto.AmountRequest;
import kz.projects.atmSystem.dto.TransferRequest;
import kz.projects.atmSystem.model.MyUserDetails;
import kz.projects.atmSystem.service.TransactionService;
import kz.projects.atmSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountsController {

  private final UserService userService;

  private final TransactionService transactionService;

  @GetMapping(value = "/check-user")
  public ResponseEntity<MyUserDetails> getCurrentUserInfo(){
    MyUserDetails currentUser = userService.getCurrentSessionUser();
    return new ResponseEntity<>(currentUser, HttpStatus.OK);
  }

  @GetMapping("/balance")
  public ResponseEntity<Double> getCurrentUserBalance(){
    return new ResponseEntity<>(userService.getCurrentUserBalance(), HttpStatus.OK);
  }

  @PostMapping("/deposit")
  public ResponseEntity<String> setUserDeposit(@RequestBody AmountRequest amountRequest){
    transactionService.deposit(amountRequest.getAmount());
    return ResponseEntity.ok("Deposit processed successfully");
  }

  @PostMapping("/withdraw")
  public ResponseEntity<String> setUserWithdrawal(@RequestBody AmountRequest amountRequest){
    transactionService.withdrawAmount(amountRequest.getAmount());
    return ResponseEntity.ok("Withdrawal processed successfully");
  }

  @PostMapping("/transfer")
  public ResponseEntity<String> setUserTransfer(@RequestBody TransferRequest transferRequest){
    transactionService.transferAmount(transferRequest.getAccount(), transferRequest.getAmount());
    return ResponseEntity.ok("Transfer processed successfully");
  }

}
