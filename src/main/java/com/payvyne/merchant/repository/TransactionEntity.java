package com.payvyne.merchant.repository;

import com.payvyne.merchant.domain.transaction.Currency;
import com.payvyne.merchant.domain.transaction.Transaction;
import com.payvyne.merchant.domain.transaction.TransactionStatus;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("transactions")
@Data
@Builder(toBuilder = true)
public class TransactionEntity {

  @Id
  @Column("id")
  private final UUID id;

  @Column("amount")
  private final BigDecimal amount;

  @Column("status")
  private final String status;

  @Column("currency")
  private final String currency;

  @Column("description")
  private final String description;

  @Column("created_at")
  private final LocalDateTime createdAt;

  @Column("updated_at")
  private final LocalDateTime updatedAt;

  public static TransactionEntity from(Transaction transaction) {
    return TransactionEntity.builder()
        .id(transaction.getId())
        .amount(transaction.getAmount())
        .status(transaction.getStatus().name())
        .currency(transaction.getCurrency().name())
        .description(transaction.getDescription().orElse(null))
        .createdAt(transaction.getCreatedAt())
        .updatedAt(transaction.getUpdatedAt())
        .build();
  }

  public static TransactionEntity mapRow(ResultSet rs) throws SQLException {

    return TransactionEntity.builder()
        .id(UUID.fromString(rs.getString("id")))
        .amount(rs.getBigDecimal("amount"))
        .status(rs.getString("status"))
        .currency(rs.getString("currency"))
        .description(rs.getString("description"))
        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
        .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
        .build();
  }

  public Transaction toTransaction() {
    return Transaction.builder()
        .amount(this.amount)
        .status(TransactionStatus.valueOf(this.status))
        .currency(Currency.valueOf(this.currency))
        .description(this.description)
        .id(this.id)
        .createdAt(this.createdAt)
        .updatedAt(this.updatedAt)
        .build();
  }
}
