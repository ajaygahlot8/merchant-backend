package com.payvyne.merchant.domain.transaction;

public interface TransactionRepositoryPort {
  void create(Transaction transaction);
}
