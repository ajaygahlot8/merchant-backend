package com.payvyne.merchant.rest.controller;

import com.payvyne.merchant.domain.transaction.Currency;
import com.payvyne.merchant.domain.transaction.TransactionQuery;
import com.payvyne.merchant.domain.transaction.TransactionService;
import com.payvyne.merchant.domain.transaction.TransactionStatus;
import com.payvyne.merchant.rest.ApiResponse;
import com.payvyne.merchant.rest.model.CreateTransactionRequest;
import com.payvyne.merchant.rest.model.GetTransactionsResponse;
import java.time.LocalDate;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TransactionController {

  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping(value = "/v1/transaction")
  public ApiResponse<String> createTransaction(@RequestBody CreateTransactionRequest request) {

    log.info("Received request to create transaction {}", request);
    var transaction = transactionService.create(request.toTransactionDetail());

    return ApiResponse.createSuccessResponse(
        "Transaction with id " + transaction.getId() + " added successfully");
  }

  @PutMapping(value = "/v1/transaction/{id}")
  public ApiResponse<String> updateTransaction(
      @PathVariable UUID id, @RequestBody TransactionStatus status) {

    log.info("Received request to update transaction with id {}", id);
    var transaction = transactionService.update(status, id);

    return ApiResponse.createSuccessResponse(
        "Transaction with id " + transaction.getId() + " updated successfully");
  }

  @DeleteMapping(value = "/v1/transaction/{id}")
  public ApiResponse<String> deleteTransaction(@PathVariable UUID id) {

    log.info("Received request to delete transaction with id {}", id);
    var transaction = transactionService.delete(id);

    return ApiResponse.createSuccessResponse(
        "Transaction with id " + transaction.getId() + " deleted successfully");
  }

  @GetMapping(value = "/v1/transactions")
  public ApiResponse<GetTransactionsResponse> getAllTransactions(
      @RequestParam(required = false) TransactionStatus status,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
      @RequestParam(required = false) Currency currency) {

    log.info("Received request to get all transactions");
    var transactions =
        transactionService.search(
            TransactionQuery.builder().date(date).currency(currency).status(status).build());

    return ApiResponse.createSuccessResponse(GetTransactionsResponse.from(transactions));
  }
}
