package com.payvyne.merchant.repository;

import com.payvyne.merchant.domain.transaction.Transaction;
import com.payvyne.merchant.domain.transaction.TransactionQuery;
import com.payvyne.merchant.domain.transaction.TransactionRepositoryPort;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionDAO implements TransactionRepositoryPort {

  private final TransactionRepository transactionRepository;

  private final JdbcAggregateTemplate jdbcAggregateTemplate;

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public TransactionDAO(
      TransactionRepository transactionRepository,
      JdbcAggregateTemplate jdbcAggregateTemplate,
      NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.transactionRepository = transactionRepository;
    this.jdbcAggregateTemplate = jdbcAggregateTemplate;
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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
  public List<Transaction> getAllTransactions(TransactionQuery transactionQuery) {

    if (transactionQuery.isQueryParameterAvailable()) {
      MapSqlParameterSource parameters = new MapSqlParameterSource();
      var sql = prepareQueryAndParams(transactionQuery, parameters);

      try {
        return (namedParameterJdbcTemplate.query(
                sql, parameters, (rs, rowNum) -> TransactionEntity.mapRow(rs)))
            .stream().map(TransactionEntity::toTransaction).collect(Collectors.toList());

      } catch (EmptyResultDataAccessException exception) {
        return List.of();
      }
    } else {
      return transactionRepository.findAllTransactions().stream()
          .map(TransactionEntity::toTransaction)
          .collect(Collectors.toList());
    }
  }

  private String prepareQueryAndParams(
      TransactionQuery transactionQuery, MapSqlParameterSource parameters) {
    String sql =
        "SELECT id, amount, status, currency, description, created_at, updated_at FROM transactions WHERE status != 'DELETED'";
    if (transactionQuery.getStatus().isPresent()) {
      parameters.addValue("status", transactionQuery.getStatus().get().name());
      sql = sql + "and status = :status";
    }

    if (transactionQuery.getCurrency().isPresent()) {
      parameters.addValue("currency", transactionQuery.getCurrency().get().name());
      sql = sql + " and currency = :currency";
    }

    if (transactionQuery.getDate().isPresent()) {
      parameters.addValue("createdAt", transactionQuery.getDate().get().toString());
      parameters.addValue(
          "createdAtNextDay", transactionQuery.getDate().get().plusDays(1).toString());

      sql = sql + " and created_at > :createdAt and created_at < :createdAtNextDay";
    }
    sql = sql + ";";
    return sql;
  }
}
