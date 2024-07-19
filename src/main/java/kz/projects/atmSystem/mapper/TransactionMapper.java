package kz.projects.atmSystem.mapper;

import kz.projects.atmSystem.dto.TransactionDTO;
import kz.projects.atmSystem.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
  public static TransactionDTO toDto(Transaction transaction) {
    if (transaction == null) {
      return null;
    }

    return new TransactionDTO(
            transaction.getId(),
            transaction.getType(),
            transaction.getAmount(),
            transaction.getDate(),
            UserMapper.toDto(transaction.getUser())
    );
  }
}
