package com.payvyne.merchant.domain.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder(toBuilder = true)
public class Transaction {

  @NonNull private final UUID id;

  @NonNull private final BigDecimal amount;

  @NonNull private final CurrencyEnum currency;

  @NonNull private final TransactionStatus status;

  @NonNull private final LocalDateTime createdAt;

  @NonNull private final LocalDateTime updatedAt;

  private final String description;

  public Optional<String> getDescription() {
    return Optional.of(description);
  }
}
