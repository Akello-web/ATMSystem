package kz.projects.atmSystem.service.impl;
import kz.projects.atmSystem.dto.TransactionDTO;
import kz.projects.atmSystem.dto.TransactionRequest;
import kz.projects.atmSystem.model.Transaction;
import kz.projects.atmSystem.model.TransactionType;
import kz.projects.atmSystem.model.User;
import kz.projects.atmSystem.repositories.TransactionRepository;
import kz.projects.atmSystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class TransactionServiceImplTest {

  @Mock
  private UserService userService;

  @Mock
  private TransactionRepository transactionRepository;

  @InjectMocks
  private TransactionServiceImpl transactionService;

  @Test
  @Transactional
  public void testDeposit_Success() {
    User currentUser = new User();
    currentUser.setId(1L);
    currentUser.setAccountNumber("KZ01");
    currentUser.setBalance(1000.0);

    when(userService.getCurrentSessionUser()).thenReturn(currentUser);
    when(userService.getCurrentUserBalance()).thenReturn(currentUser.getBalance());
    doNothing().when(userService).saveUser(any(User.class));
    doAnswer(invocation -> {
      Transaction savedTransaction = invocation.getArgument(0);
      savedTransaction.setId(1L); // Simulating saved transaction with ID
      return savedTransaction;
    }).when(transactionRepository).save(any(Transaction.class));

    double amount = 500.0;
    transactionService.deposit(amount);

    assertEquals(1500.0, currentUser.getBalance());
    verify(transactionRepository, times(1)).save(any(Transaction.class));
  }

  @Test
  @Transactional
  public void testWithdrawAmount_Success() {
    User currentUser = new User();
    currentUser.setId(1L);
    currentUser.setAccountNumber("KZ01");
    currentUser.setBalance(1000.0);

    when(userService.getCurrentSessionUser()).thenReturn(currentUser);
    when(userService.getCurrentUserBalance()).thenReturn(currentUser.getBalance());
    doNothing().when(userService).saveUser(any(User.class));
    doAnswer(invocation -> {
      Transaction savedTransaction = invocation.getArgument(0);
      savedTransaction.setId(1L);
      return savedTransaction;
    }).when(transactionRepository).save(any(Transaction.class));

    double amount = 500.0;
    transactionService.withdrawAmount(amount);

    assertEquals(500.0, currentUser.getBalance());
    verify(transactionRepository, times(1)).save(any(Transaction.class));
  }

  @Test
  @Transactional
  public void testTransferAmount_Success() {
    User currentUser = new User();
    currentUser.setId(1L);
    currentUser.setAccountNumber("KZ01");
    currentUser.setBalance(1000.0);

    User recipientUser = new User();
    recipientUser.setId(2L);
    recipientUser.setAccountNumber("KZ02");
    recipientUser.setBalance(500.0);

    TransactionRequest request = new TransactionRequest();
    request.setAccount("KZ02");
    request.setAmount(300.0);

    when(userService.getCurrentSessionUser()).thenReturn(currentUser);
    when(userService.getCurrentUserBalance()).thenReturn(currentUser.getBalance());
    when(userService.getUser("KZ02")).thenReturn(recipientUser);
    doNothing().when(userService).saveUser(any(User.class));
    doAnswer(invocation -> {
      Transaction savedTransaction = invocation.getArgument(0);
      savedTransaction.setId(1L);
      return savedTransaction;
    }).when(transactionRepository).save(any(Transaction.class));

    transactionService.transferAmount(request);

    assertEquals(700.0, currentUser.getBalance());
    assertEquals(800.0, recipientUser.getBalance());
    verify(transactionRepository, times(1)).save(any(Transaction.class));
  }

  @Test
  public void testGetTransactions_Success() {
    Transaction transaction1 = new Transaction();
    transaction1.setId(1L);
    transaction1.setType(TransactionType.DEPOSIT);
    transaction1.setAmount(500.0);
    transaction1.setDate(LocalDateTime.now());

    Transaction transaction2 = new Transaction();
    transaction2.setId(2L);
    transaction2.setType(TransactionType.WITHDRAWAL);
    transaction2.setAmount(200.0);
    transaction2.setDate(LocalDateTime.now().minusHours(1));

    List<Transaction> transactions = List.of(transaction1, transaction2);

    when(transactionRepository.findAll()).thenReturn(transactions);

    List<TransactionDTO> transactionDTOs = transactionService.getTransactions();

    assertEquals(2, transactionDTOs.size());
    assertEquals(transaction1.getId(), transactionDTOs.get(0).getId());
    assertEquals(transaction1.getType(), transactionDTOs.get(0).getType());
    assertEquals(transaction1.getAmount(), transactionDTOs.get(0).getAmount());
    assertEquals(transaction1.getDate(), transactionDTOs.get(0).getDate());

    assertEquals(transaction2.getId(), transactionDTOs.get(1).getId());
    assertEquals(transaction2.getType(), transactionDTOs.get(1).getType());
    assertEquals(transaction2.getAmount(), transactionDTOs.get(1).getAmount());
    assertEquals(transaction2.getDate(), transactionDTOs.get(1).getDate());
  }
}

