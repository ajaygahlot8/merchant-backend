package com.payvyne.merchant.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
  T1("Transaction not found"),
  T2("Current transaction status is same as new transaction status");

  private final String message;
  private final String code;

  ErrorCode(String message) {
    this.code = this.name();
    this.message = message;
  }
}
