package com.payvyne.merchant.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.payvyne.merchant.domain.transaction.Currency;
import com.payvyne.merchant.domain.transaction.Transaction;
import com.payvyne.merchant.domain.transaction.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class GetTransactionsResponse {
  private final List<TransactionData> transactions;

  @Builder
  @Data
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class TransactionData {

    private final UUID id;
    private final BigDecimal amount;
    private final Currency currency;
    private final TransactionStatus status;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
  }

  public static GetTransactionsResponse from(List<Transaction> transactions) {

    return GetTransactionsResponse.builder()
        .transactions(
            transactions.stream()
                .map(
                    transaction ->
                        TransactionData.builder()
                            .id(transaction.getId())
                            .amount(transaction.getAmount())
                            .currency(transaction.getCurrency())
                            .description(transaction.getDescription().orElse(null))
                            .status(transaction.getStatus())
                            .createdAt(transaction.getCreatedAt())
                            .updatedAt(transaction.getUpdatedAt())
                            .build())
                .collect(Collectors.toList()))
        .build();
  }
}
