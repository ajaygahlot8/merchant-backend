package com.payvyne.merchant.domain.transaction;

import com.payvyne.merchant.domain.common.ServiceException;
import com.payvyne.merchant.exception.ErrorCode;

public class InvalidTransactionRequestException extends ServiceException {
  public InvalidTransactionRequestException(ErrorCode error) {
    super(error);
  }
}
