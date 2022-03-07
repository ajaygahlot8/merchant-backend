package com.payvyne.merchant.domain.transaction;

import com.payvyne.merchant.domain.common.ServiceException;
import com.payvyne.merchant.exception.ErrorCode;

public class TransactionNotFoundException extends ServiceException {
  public TransactionNotFoundException(ErrorCode error) {
    super(error);
  }
}
