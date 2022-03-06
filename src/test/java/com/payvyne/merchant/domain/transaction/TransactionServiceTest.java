package com.payvyne.merchant.domain.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.payvyne.merchant.domain.common.TimeSource;
import com.payvyne.merchant.exception.ErrorCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.IdGenerator;

class TransactionServiceTest {

  private TransactionService transactionService;

  @Mock private IdGenerator idGenerator;

  @Mock private TimeSource timeSource;

  @Mock private TransactionRepositoryPort transactionRepositoryPort;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    transactionService = new TransactionService(idGenerator, timeSource, transactionRepositoryPort);
  }

  @Test
  @DisplayName("Should create transaction ")
  void testCreateTransaction() {

    var amount = BigDecimal.valueOf(1000.50);
    var status = TransactionStatus.SUCCEED;
    var currency = CurrencyEnum.GBP;
    var description = "Purchased laptop";
    var now = LocalDateTime.parse("2022-03-07T12:30:30.123");
    var id = UUID.fromString("c658a23b-786a-48b5-8c07-aa84311d79d6");

    var transactionDetail =
        TransactionDetail.builder()
            .amount(amount)
            .status(status)
            .currency(currency)
            .description(description)
            .build();

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

    when(timeSource.now()).thenReturn(now);
    when(idGenerator.generateId()).thenReturn(id);
    when(transactionRepositoryPort.create(transaction)).thenReturn(transaction);

    var actual = transactionService.create(transactionDetail);

    assertEquals(transaction, actual);
    verify(transactionRepositoryPort, times(1)).create(transaction);
  }

  @Test
  @DisplayName("Should update transaction status")
  void testUpdateTransactionStatus() {

    var amount = BigDecimal.valueOf(1000.50);
    var status = TransactionStatus.PENDING;
    var newTransactionStatus = TransactionStatus.FAILED;
    var currency = CurrencyEnum.GBP;
    var description = "Purchased laptop";
    var now = LocalDateTime.parse("2022-03-07T12:30:30.123");
    var id = UUID.fromString("c658a23b-786a-48b5-8c07-aa84311d79d6");
    var yesterday = now.minusDays(1);

    var transaction =
        Transaction.builder()
            .amount(amount)
            .status(status)
            .currency(currency)
            .description(description)
            .id(id)
            .createdAt(yesterday)
            .updatedAt(yesterday)
            .build();

    var newTransaction =
        Transaction.builder()
            .amount(amount)
            .status(newTransactionStatus)
            .currency(currency)
            .description(description)
            .id(id)
            .createdAt(yesterday)
            .updatedAt(now)
            .build();

    when(timeSource.now()).thenReturn(now);
    when(transactionRepositoryPort.getTransactionById(id)).thenReturn(Optional.of(transaction));
    when(transactionRepositoryPort.update(newTransaction)).thenReturn(newTransaction);

    var actual = transactionService.update(newTransactionStatus, transaction.getId());

    assertEquals(newTransaction, actual);
    verify(transactionRepositoryPort, times(1)).getTransactionById(id);
    verify(transactionRepositoryPort, times(1)).update(newTransaction);
  }

  @Test
  @DisplayName("Should not update transaction if transaction not preset")
  void testThrowExceptionIfTransactionNotPresentWhileUpdating() {

    var amount = BigDecimal.valueOf(1000.50);
    var status = TransactionStatus.PENDING;
    var newTransactionStatus = TransactionStatus.FAILED;
    var currency = CurrencyEnum.GBP;
    var description = "Purchased laptop";
    var now = LocalDateTime.parse("2022-03-07T12:30:30.123");
    var id = UUID.fromString("c658a23b-786a-48b5-8c07-aa84311d79d6");
    var yesterday = now.minusDays(1);

    var transaction =
        Transaction.builder()
            .amount(amount)
            .status(status)
            .currency(currency)
            .description(description)
            .id(id)
            .createdAt(yesterday)
            .updatedAt(yesterday)
            .build();

    when(timeSource.now()).thenReturn(now);
    when(transactionRepositoryPort.getTransactionById(id)).thenReturn(Optional.empty());

    var exception =
        assertThrows(
            TransactionException.class,
            () -> transactionService.update(newTransactionStatus, transaction.getId()));

    assertEquals(ErrorCode.T1, exception.getError());
    verify(transactionRepositoryPort, times(1)).getTransactionById(id);
    verify(transactionRepositoryPort, times(0)).update(any());
  }

  @Test
  @DisplayName("Should not update transaction if new transaction status is same as old")
  void testThrowExceptionIfTransactionStatusIsSameAsOld() {

    var amount = BigDecimal.valueOf(1000.50);
    var status = TransactionStatus.PENDING;
    var newTransactionStatus = TransactionStatus.PENDING;
    var currency = CurrencyEnum.GBP;
    var description = "Purchased laptop";
    var now = LocalDateTime.parse("2022-03-07T12:30:30.123");
    var id = UUID.fromString("c658a23b-786a-48b5-8c07-aa84311d79d6");
    var yesterday = now.minusDays(1);

    var transaction =
        Transaction.builder()
            .amount(amount)
            .status(status)
            .currency(currency)
            .description(description)
            .id(id)
            .createdAt(yesterday)
            .updatedAt(yesterday)
            .build();

    when(timeSource.now()).thenReturn(now);
    when(transactionRepositoryPort.getTransactionById(id)).thenReturn(Optional.of(transaction));

    var exception =
        assertThrows(
            TransactionException.class,
            () -> transactionService.update(newTransactionStatus, transaction.getId()));

    assertEquals(ErrorCode.T2, exception.getError());
    verify(transactionRepositoryPort, times(1)).getTransactionById(id);
    verify(transactionRepositoryPort, times(0)).update(any());
  }
}
