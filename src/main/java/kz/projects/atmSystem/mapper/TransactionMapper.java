package kz.projects.atmSystem.mapper;

import kz.projects.atmSystem.dto.TransactionDTO;
import kz.projects.atmSystem.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
  public static TransactionDTO toDto(Transaction transaction) {
    if (transaction == null){
      return null;
    }

    TransactionDTO dto = new TransactionDTO();
    dto.setId(transaction.getId());
    dto.setType(transaction.getType());
    dto.setAmount(transaction.getAmount());
    dto.setDate(transaction.getDate());
    dto.setUser(UserMapper.toDto(transaction.getUser()));


    return dto;
  }
}
