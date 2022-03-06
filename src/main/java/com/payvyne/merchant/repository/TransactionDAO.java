package com.payvyne.merchant.repository;

import com.payvyne.merchant.domain.transaction.Transaction;
import com.payvyne.merchant.domain.transaction.TransactionRepositoryPort;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionDAO implements TransactionRepositoryPort {

  private final TransactionRepository transactionRepository;

  private final JdbcAggregateTemplate jdbcAggregateTemplate;

  public TransactionDAO(
      TransactionRepository transactionRepository, JdbcAggregateTemplate jdbcAggregateTemplate) {
    this.transactionRepository = transactionRepository;
    this.jdbcAggregateTemplate = jdbcAggregateTemplate;
  }

  @Override
  public Transaction create(Transaction transaction) {
    return jdbcAggregateTemplate.insert(TransactionEntity.from(transaction)).toTransaction();
  }

  @Override
  public Optional<Transaction> getTransactionById(UUID transactionId) {
    var transactionEntity = transactionRepository.findTransactionById(transactionId);
    return transactionEntity.map(TransactionEntity::toTransaction);
  }

  @Override
  public Transaction update(Transaction updatedTransaction) {
    return transactionRepository.save(TransactionEntity.from(updatedTransaction)).toTransaction();
  }

  @Override
  public List<Transaction> getAllTransactions() {
    var transactionEntities = transactionRepository.findAllTransactions();
    return transactionEntities.stream()
        .map(TransactionEntity::toTransaction)
        .collect(Collectors.toList());
  }
}
