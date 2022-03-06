package com.payvyne.merchant.domain.common;

import com.payvyne.merchant.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

  private final ErrorCode error;

  public ServiceException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.error = errorCode;
  }
}
