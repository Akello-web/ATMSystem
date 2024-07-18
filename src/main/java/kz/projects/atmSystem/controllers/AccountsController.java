package kz.projects.atmSystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.projects.atmSystem.dto.TransactionRequest;
import kz.projects.atmSystem.service.TransactionService;
import kz.projects.atmSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
@Tag(name = "Transactions", description = "Endpoints for transaction operations")
public class AccountsController {

  private final UserService userService;

  private final TransactionService transactionService;

  @GetMapping("/balance")
  @Operation(summary = "Get current user balance",
          description = "Retrieve the current balance of the authenticated user")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved current user balance",
                  content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content)
  })
  public ResponseEntity<Double> getCurrentUserBalance(){
    return new ResponseEntity<>(userService.getCurrentUserBalance(), HttpStatus.OK);
  }

  @PostMapping("/deposit")
  @Operation(summary = "Deposit money", description = "Deposit a specified amount to the current user's account")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Deposit processed successfully", content = @Content),
          @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
  })
  public ResponseEntity<String> setUserDeposit(@RequestBody TransactionRequest request){
    transactionService.deposit(request.getAmount());
    return ResponseEntity.ok("Deposit processed successfully");
  }

  @PostMapping("/withdraw")
  @Operation(summary = "Withdraw money", description = "Withdraw a specified amount from the current user's account")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Withdrawal processed successfully", content = @Content),
          @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
  })
  public ResponseEntity<String> setUserWithdrawal(@RequestBody TransactionRequest request){
    transactionService.withdrawAmount(request.getAmount());
    return ResponseEntity.ok("Withdrawal processed successfully");
  }

  @PostMapping("/transfer")
  @Operation(summary = "Transfer money", description = "Transfer a specified amount from the current user's account to another account")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Transfer processed successfully", content = @Content),
          @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
          @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
  })
  public ResponseEntity<String> setUserTransfer(@RequestBody TransactionRequest transferRequest){
    transactionService.transferAmount(transferRequest);
    return ResponseEntity.ok("Transfer processed successfully");
  }

}
