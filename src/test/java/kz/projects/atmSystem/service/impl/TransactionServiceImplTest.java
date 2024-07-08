package kz.projects.atmSystem.service.impl;
import kz.projects.atmSystem.model.MyUserDetails;
import kz.projects.atmSystem.model.TransactionType;
import kz.projects.atmSystem.model.User;
import kz.projects.atmSystem.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

  @Mock
  private UserServiceImpl userService;

  @Mock
  private TransactionRepository transactionRepository;

  @InjectMocks
  private TransactionServiceImpl transactionService;

  private User user;
  private MyUserDetails userDetails;

  @BeforeEach
  public void setUp() {
    user = new User();
    user.setAccountNumber("account123");
    user.setPin("encodedPin");
    user.setBalance(100.0);
    user.setPermissionList(Collections.emptyList());

    userDetails = new MyUserDetails(user);
  }

  @Test
  public void testDeposit() {
    double depositAmount = 50.0;
    Double expectedBalance = user.getBalance() + depositAmount;

    when(userService.getCurrentSessionUser()).thenReturn(userDetails);
    when(userService.getCurrentUserBalance()).thenReturn(user.getBalance());

    transactionService.deposit(depositAmount);

    verify(userService).getCurrentSessionUser();
    verify(userService).getCurrentUserBalance();
    verify(userService).saveUser(user);

    assertThat(user.getBalance()).isEqualTo(expectedBalance);

    verify(transactionRepository).save(argThat(transaction ->
            transaction.getType() == TransactionType.DEPOSIT &&
                    (transaction.getAmount() == depositAmount) &&
                    transaction.getUser().equals(user)
    ));
  }

  @Test
  public void testWithdrawAmount() {
    double withdrawAmount = 50.0;
    Double expectedBalance = user.getBalance() - withdrawAmount;

    when(userService.getCurrentSessionUser()).thenReturn(userDetails);
    when(userService.getCurrentUserBalance()).thenReturn(user.getBalance());

    transactionService.withdrawAmount(withdrawAmount);

    verify(userService).getCurrentSessionUser();
    verify(userService).getCurrentUserBalance();
    verify(userService).saveUser(user);

    assertThat(user.getBalance()).isEqualTo(expectedBalance);

    verify(transactionRepository).save(argThat(transaction ->
            transaction.getType() == TransactionType.WITHDRAWAL &&
                    (transaction.getAmount() == withdrawAmount) &&
                    transaction.getUser().equals(user)
    ));
  }

}
