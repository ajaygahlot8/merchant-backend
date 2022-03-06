package com.payvyne.merchant.domain.transaction;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepositoryPort {
  Transaction create(Transaction transaction);

  Optional<Transaction> getTransactionById(UUID transactionId);
}
