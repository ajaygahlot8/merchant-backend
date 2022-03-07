package com.payvyne.merchant.domain.transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepositoryPort {
  Transaction create(Transaction transaction);

  Optional<Transaction> getTransactionById(UUID transactionId);

  Transaction update(Transaction updatedTransaction);

  List<Transaction> getAllTransactions(TransactionQuery transactionQuery);
}
