package com.payvyne.merchant.domain.transaction;

import com.payvyne.merchant.domain.common.TimeSource;
import com.payvyne.merchant.exception.ErrorCode;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

@Service
public class TransactionService {

  private final IdGenerator idGenerator;

  private final TimeSource timeSource;

  private final TransactionRepositoryPort transactionRepositoryPort;

  public TransactionService(
      IdGenerator idGenerator,
      TimeSource timeSource,
      TransactionRepositoryPort transactionRepositoryPort) {
    this.idGenerator = idGenerator;
    this.timeSource = timeSource;
    this.transactionRepositoryPort = transactionRepositoryPort;
  }

  public Transaction create(TransactionDetail transactionDetail) {

    var transaction =
        Transaction.builder()
            .id(idGenerator.generateId())
            .amount(transactionDetail.getAmount())
            .currency(transactionDetail.getCurrency())
            .status(transactionDetail.getStatus())
            .createdAt(timeSource.now())
            .updatedAt(timeSource.now())
            .description(transactionDetail.getDescription().orElse(null))
            .build();

    return transactionRepositoryPort.create(transaction);
  }

  public Transaction update(TransactionStatus newStatus, UUID transactionId) {

    if (newStatus == TransactionStatus.DELETED) {
      throw new TransactionException(ErrorCode.T4);
    }

    var transaction = transactionRepositoryPort.getTransactionById(transactionId);

    if (transaction.isEmpty()) {
      throw new TransactionException(ErrorCode.T1);
    }

    if (transaction.get().getStatus() == newStatus) {
      throw new TransactionException(ErrorCode.T2);
    }

    var updatedTransaction =
        Transaction.builder()
            .id(transaction.get().getId())
            .amount(transaction.get().getAmount())
            .currency(transaction.get().getCurrency())
            .status(newStatus)
            .createdAt(transaction.get().getCreatedAt())
            .updatedAt(timeSource.now())
            .description(transaction.get().getDescription().orElse(null))
            .build();

    return transactionRepositoryPort.update(updatedTransaction);
  }

  public Transaction delete(UUID transactionId) {

    var transaction = transactionRepositoryPort.getTransactionById(transactionId);

    if (transaction.isEmpty()) {
      throw new TransactionException(ErrorCode.T1);
    }

    var updatedTransaction =
        Transaction.builder()
            .id(transaction.get().getId())
            .amount(transaction.get().getAmount())
            .currency(transaction.get().getCurrency())
            .status(TransactionStatus.DELETED)
            .createdAt(transaction.get().getCreatedAt())
            .updatedAt(timeSource.now())
            .description(transaction.get().getDescription().orElse(null))
            .build();

    return transactionRepositoryPort.update(updatedTransaction);
  }

  public List<Transaction> search() {
    return transactionRepositoryPort.getAllTransactions();
  }
}
