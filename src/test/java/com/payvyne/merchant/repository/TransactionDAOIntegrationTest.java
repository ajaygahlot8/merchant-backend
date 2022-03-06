package com.payvyne.merchant.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.payvyne.merchant.domain.transaction.CurrencyEnum;
import com.payvyne.merchant.domain.transaction.Transaction;
import com.payvyne.merchant.domain.transaction.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;

@DataJdbcTest
class TransactionDAOIntegrationTest {

  TransactionDAO transactionDao;

  @Autowired TransactionRepository transactionRepository;

  @Autowired JdbcAggregateTemplate jdbcAggregateTemplate;

  @BeforeEach
  void setUp() {
    transactionDao = new TransactionDAO(transactionRepository, jdbcAggregateTemplate);
  }

  @DisplayName("Should create transactions")
  @Test
  void testCreateAndGetAllTransactions() {
    var amount = BigDecimal.valueOf(1000.50);
    var status = TransactionStatus.SUCCEED;
    var currency = CurrencyEnum.GBP;
    var description = "Purchased laptop";
    var now = LocalDateTime.parse("2022-03-07T12:30:30.123");
    var id = UUID.fromString("c658a23b-786a-48b5-8c07-aa84311d79d6");

    var transaction =
        Transaction.builder()
            .amount(amount)
            .status(status)
            .currency(currency)
            .description(description)
            .id(id)
            .createdAt(now)
            .updatedAt(now)
            .build();

    var actual = transactionDao.create(transaction);

    assertEquals(transaction, actual);
  }

  @DisplayName("Should get transaction by id")
  @Test
  void testGetTransactionById() {
    var amount = BigDecimal.valueOf(1000.50);
    var status = TransactionStatus.SUCCEED;
    var currency = CurrencyEnum.GBP;
    var description = "Purchased laptop";
    var now = LocalDateTime.parse("2022-03-07T12:30:30.123");
    var id = UUID.fromString("c658a23b-786a-48b5-8c07-aa84311d79d6");

    var transaction =
        Transaction.builder()
            .amount(amount)
            .status(status)
            .currency(currency)
            .description(description)
            .id(id)
            .createdAt(now)
            .updatedAt(now)
            .build();

    transactionDao.create(transaction);

    var actual = transactionDao.getTransactionById(transaction.getId());

    assertTrue(actual.isPresent());
    assertEquals(transaction, actual.get());
  }
}
