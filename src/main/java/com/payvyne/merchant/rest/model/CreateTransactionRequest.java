package com.payvyne.merchant.rest.model;

import com.payvyne.merchant.domain.transaction.Currency;
import com.payvyne.merchant.domain.transaction.TransactionDetail;
import com.payvyne.merchant.domain.transaction.TransactionStatus;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class CreateTransactionRequest {
  @NonNull private final BigDecimal amount;

  @NonNull private final Currency currency;

  @NonNull private final TransactionStatus status;

  private final String description;

  public TransactionDetail toTransactionDetail() {
    return TransactionDetail.builder()
        .amount(this.amount)
        .status(this.status)
        .currency(this.currency)
        .description(this.description)
        .build();
  }
}
