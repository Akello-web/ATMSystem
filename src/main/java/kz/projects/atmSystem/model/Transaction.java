package kz.projects.atmSystem.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private TransactionType type;

  private BigDecimal amount;

  private LocalDateTime date;

  @ManyToOne
  @JoinColumn(name = "account_number", referencedColumnName = "accountNumber")
  private User user;
}
