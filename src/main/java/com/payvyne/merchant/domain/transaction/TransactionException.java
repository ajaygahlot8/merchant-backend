package com.payvyne.merchant.domain.transaction;

import com.payvyne.merchant.domain.common.ServiceException;
import com.payvyne.merchant.exception.ErrorCode;

public class TransactionException extends ServiceException {
  public TransactionException(ErrorCode error) {
    super(error);
  }
}
