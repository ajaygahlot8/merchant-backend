package com.payvyne.merchant.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<TransactionEntity, UUID> {

  @Query(
      "SELECT id, amount, status, currency, description, created_at, updated_at FROM transactions WHERE id = :id and status != 'DELETED'")
  Optional<TransactionEntity> findTransactionById(UUID id);

  @Query(
      "SELECT id, amount, status, currency, description, created_at, updated_at FROM transactions WHERE status != 'DELETED'")
  List<TransactionEntity> findAllTransactions();
}
