package com.payvyne.merchant.domain.transaction;

import com.payvyne.merchant.domain.common.TimeSource;
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

  public UUID create(TransactionDetail transactionDetail) {

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

    transactionRepositoryPort.create(transaction);
    return transaction.getId();
  }
}
