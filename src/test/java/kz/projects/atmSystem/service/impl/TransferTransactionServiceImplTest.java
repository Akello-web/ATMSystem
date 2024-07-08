package kz.projects.atmSystem.service.impl;

import kz.projects.atmSystem.model.MyUserDetails;
import kz.projects.atmSystem.model.Transaction;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferTransactionServiceImplTest {

  @Mock
  private UserServiceImpl userService;

  @Mock
  private TransactionRepository transactionRepository;

  @InjectMocks
  private TransactionServiceImpl transactionService;

  private User currentUser;
  private User targetUser;
  private MyUserDetails currentUserDetails;

  @BeforeEach
  public void setUp() {
    currentUser = new User();
    currentUser.setAccountNumber("currentUser123");
    currentUser.setPin("encodedPin");
    currentUser.setBalance(100.0);
    currentUser.setPermissionList(Collections.emptyList());

    targetUser = new User();
    targetUser.setAccountNumber("targetUser456");
    targetUser.setPin("encodedPin");
    targetUser.setBalance(50.0);
    targetUser.setPermissionList(Collections.emptyList());

    currentUserDetails = new MyUserDetails(currentUser);
  }

  @Test
  public void testTransferAmount() {
    double transferAmount = 50.0;
    Double expectedCurrentUserBalance = currentUser.getBalance() - transferAmount;//50
    Double expectedTargetUserBalance = targetUser.getBalance() + transferAmount;//100

    when(userService.getCurrentSessionUser()).thenReturn(currentUserDetails);
    when(userService.getUser("targetUser456")).thenReturn(targetUser);
    when(userService.getCurrentUserBalance()).thenReturn(currentUser.getBalance());

    transactionService.transferAmount("targetUser456", transferAmount);

    verify(userService, times(1)).getCurrentSessionUser();
    verify(userService, times(1)).getUser("targetUser456");
    verify(userService, times(2)).saveUser(any(User.class));

    assertThat(currentUser.getBalance()).isEqualTo(expectedCurrentUserBalance);
    assertThat(targetUser.getBalance()).isEqualTo(expectedTargetUserBalance);

    verify(transactionRepository).save(argThat(transaction ->
            transaction.getType() == TransactionType.TRANSFER &&
                    (transaction.getAmount() == transferAmount) &&
                    transaction.getUser().equals(currentUser)
    ));
  }

  @Test
  public void testTransferAmountToSameAccount() {
    double transferAmount = 50.0;

    when(userService.getCurrentSessionUser()).thenReturn(currentUserDetails);

    assertThatThrownBy(() -> transactionService.transferAmount("currentUser123", transferAmount))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Cannot transfer to the same account");

    verify(userService, times(1)).getCurrentSessionUser();
    verify(userService, never()).saveUser(any(User.class));
    verify(transactionRepository, never()).save(any(Transaction.class));
  }
}

