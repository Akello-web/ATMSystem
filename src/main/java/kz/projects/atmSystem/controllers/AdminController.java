package kz.projects.atmSystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.projects.atmSystem.dto.TransactionDTO;
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
@Tag(name = "Admin Operations", description = "Endpoints for admin operations")
public class AdminController {

  private final TransactionService transactionService;

  @GetMapping(value = "transactions")
  @Operation(summary = "Get Transactions List", description = "Retrieve a list of transactions")
  public ResponseEntity<List<TransactionDTO>> getTransactionsList(){
    return new ResponseEntity<>(transactionService.getTransactions(), HttpStatus.OK);
  }
}
