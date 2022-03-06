package com.payvyne.merchant.repository;

import com.payvyne.merchant.domain.transaction.Transaction;
import com.payvyne.merchant.domain.transaction.TransactionRepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionDAO implements TransactionRepositoryPort {
  @Override
  public void create(Transaction transaction) {
    // TODO
  }
}
