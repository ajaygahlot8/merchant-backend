package com.payvyne.merchant.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
  E1("Internal Server Error"),
  E2("Invalid request body"),
  T1("Transaction not found"),
  T2("Current transaction status is same as new transaction status"),
  T3("Transaction already deleted"),
  T4("Transaction cannot be deleted via update API");

  private final String message;
  private final String code;

  ErrorCode(String message) {
    this.code = this.name();
    this.message = message;
  }
}
