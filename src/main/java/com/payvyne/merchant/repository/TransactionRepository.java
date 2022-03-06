package com.payvyne.merchant.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<TransactionEntity, UUID> {}
