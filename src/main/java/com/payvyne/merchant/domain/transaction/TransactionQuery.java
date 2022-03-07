package com.payvyne.merchant.domain.transaction;

import java.time.LocalDate;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class TransactionQuery {

  private final LocalDate date;

  private final Currency currency;

  private final TransactionStatus status;

  public Optional<LocalDate> getDate() {
    return Optional.ofNullable(date);
  }

  public Optional<Currency> getCurrency() {
    return Optional.ofNullable(currency);
  }

  public Optional<TransactionStatus> getStatus() {
    return Optional.ofNullable(status);
  }

  public Boolean isQueryParameterAvailable() {
    return getDate().isPresent() || getCurrency().isPresent() || getStatus().isPresent();
  }
}
