package com.payvyne.merchant.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class UpdateTransactionRequest {
  @NonNull private final String status;
}
