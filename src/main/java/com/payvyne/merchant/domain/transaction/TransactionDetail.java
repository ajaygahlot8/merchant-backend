package com.payvyne.merchant.domain.transaction;

import java.math.BigDecimal;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder(toBuilder = true)
public class TransactionDetail {

  @NonNull private final BigDecimal amount;

  @NonNull private final Currency currency;

  @NonNull private final TransactionStatus status;

  private final String description;

  public Optional<String> getDescription() {
    return Optional.of(description);
  }
}
