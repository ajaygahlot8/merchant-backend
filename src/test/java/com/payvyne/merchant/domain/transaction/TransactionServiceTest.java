package com.payvyne.merchant.domain.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.payvyne.merchant.domain.common.TimeSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    var transactionId = transactionService.create(transactionDetail);

    assertEquals(id, transactionId);
    verify(transactionRepositoryPort, times(1)).create(transaction);
  }
}
