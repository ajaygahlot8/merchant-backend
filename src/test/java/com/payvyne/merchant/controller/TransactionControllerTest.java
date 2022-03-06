package com.payvyne.merchant.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payvyne.merchant.domain.transaction.Currency;
import com.payvyne.merchant.domain.transaction.Transaction;
import com.payvyne.merchant.domain.transaction.TransactionDetail;
import com.payvyne.merchant.domain.transaction.TransactionService;
import com.payvyne.merchant.domain.transaction.TransactionStatus;
import com.payvyne.merchant.rest.model.CreateTransactionRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private TransactionService transactionService;

  @Test
  @DisplayName("Should be able to create transaction")
  void testCreateTransaction() throws Exception {
    var amount = BigDecimal.valueOf(1000.50);
    var status = TransactionStatus.SUCCEED;
    var currency = Currency.GBP;
    var description = "Purchased laptop";
    var id = UUID.fromString("c658a23b-786a-48b5-8c07-aa84311d79d6");
    var now = LocalDateTime.parse("2022-03-07T12:30:30.123");

    var expectedResponse =
        "{\"success\":true,\"data\":\"Transaction with id " + id + " added successfully\"}}";

    var transactionDetail =
        TransactionDetail.builder()
            .amount(amount)
            .currency(currency)
            .status(status)
            .description(description)
            .build();

    var transaction =
        Transaction.builder()
            .amount(amount)
            .status(status)
            .currency(currency)
            .description(description)
            .id(id)
            .createdAt(now)
            .updatedAt(now)
            .build();

    when(transactionService.create(transactionDetail)).thenReturn(transaction);

    var request = new CreateTransactionRequest(amount, currency, status, description);

    mockMvc
        .perform(
            post("/v1/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expectedResponse));

    verify(transactionService, times(1)).create(transactionDetail);
  }

  @Test
  @DisplayName("Should be able to update transaction")
  void testUpdateTransaction() throws Exception {
    var amount = BigDecimal.valueOf(1000.50);
    var status = TransactionStatus.SUCCEED;
    var currency = Currency.GBP;
    var description = "Purchased laptop";
    var id = UUID.fromString("c658a23b-786a-48b5-8c07-aa84311d79d6");
    var now = LocalDateTime.parse("2022-03-07T12:30:30.123");

    var expectedResponse =
        "{\"success\":true,\"data\":\"Transaction with id " + id + " updated successfully\"}}";

    var transaction =
        Transaction.builder()
            .amount(amount)
            .status(status)
            .currency(currency)
            .description(description)
            .id(id)
            .createdAt(now)
            .updatedAt(now)
            .build();

    when(transactionService.update(status, id)).thenReturn(transaction);

    mockMvc
        .perform(
            put("/v1/transaction/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(status)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expectedResponse));

    verify(transactionService, times(1)).update(status, id);
  }

  @Test
  @DisplayName("Should be able to delete transaction")
  void testDeleteTransaction() throws Exception {
    var amount = BigDecimal.valueOf(1000.50);
    var status = TransactionStatus.DELETED;
    var currency = Currency.GBP;
    var description = "Purchased laptop";
    var id = UUID.fromString("c658a23b-786a-48b5-8c07-aa84311d79d6");
    var now = LocalDateTime.parse("2022-03-07T12:30:30.123");

    var expectedResponse =
        "{\"success\":true,\"data\":\"Transaction with id " + id + " deleted successfully\"}}";

    var transaction =
        Transaction.builder()
            .amount(amount)
            .status(status)
            .currency(currency)
            .description(description)
            .id(id)
            .createdAt(now)
            .updatedAt(now)
            .build();

    when(transactionService.delete(id)).thenReturn(transaction);

    mockMvc
        .perform(delete("/v1/transaction/" + id).contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expectedResponse));

    verify(transactionService, times(1)).delete(id);
  }

  @Test
  @DisplayName("Should be able get all transactions")
  void testGetAllTransactions() throws Exception {
    var amount = BigDecimal.valueOf(1000.50);
    var status = TransactionStatus.DELETED;
    var currency = Currency.GBP;
    var description = "Purchased laptop";
    var id = UUID.fromString("c658a23b-786a-48b5-8c07-aa84311d79d6");
    var id2 = UUID.fromString("b658a23b-786a-48b5-8c07-aa84311d79d6");
    var now = LocalDateTime.parse("2022-03-07T12:30:30.123");

    var expectedResponse =
        "{\"success\":true,\"data\":{\"transactions\":[{\"id\":\"c658a23b-786a-48b5-8c07-aa84311d79d6\",\"amount\":1000.5,\"currency\":\"GBP\",\"status\":\"DELETED\",\"description\":\"Purchased laptop\",\"createdAt\":\"2022-03-07T12:30:30.123\",\"updatedAt\":\"2022-03-07T12:30:30.123\"},{\"id\":\"b658a23b-786a-48b5-8c07-aa84311d79d6\",\"amount\":1000.5,\"currency\":\"GBP\",\"status\":\"DELETED\",\"description\":\"Purchased laptop\",\"createdAt\":\"2022-03-06T12:30:30.123\",\"updatedAt\":\"2022-03-06T12:30:30.123\"}]}}";

    var transaction =
        Transaction.builder()
            .amount(amount)
            .status(status)
            .currency(currency)
            .description(description)
            .id(id)
            .createdAt(now)
            .updatedAt(now)
            .build();

    var transaction2 =
        Transaction.builder()
            .amount(amount)
            .status(status)
            .currency(currency)
            .description(description)
            .id(id2)
            .createdAt(now.minusDays(1))
            .updatedAt(now.minusDays(1))
            .build();

    when(transactionService.search()).thenReturn(List.of(transaction, transaction2));

    mockMvc
        .perform(get("/v1/transactions").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expectedResponse));

    verify(transactionService, times(1)).search();
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
