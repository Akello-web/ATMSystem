package kz.projects.atmSystem.controllers;

import kz.projects.atmSystem.model.Transaction;
import kz.projects.atmSystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

  private final TransactionService transactionService;

  @GetMapping(value = "transactions")
  public ResponseEntity<List<Transaction>> getTransactionsList(){
    return new ResponseEntity<>(transactionService.getTransactions(), HttpStatus.OK);
  }
}
