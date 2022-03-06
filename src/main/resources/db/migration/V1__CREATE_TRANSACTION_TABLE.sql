CREATE TABLE transactions
(
    id          UUID        NOT NULL PRIMARY KEY,
    status      VARCHAR(20) NOT NULL,
    currency    VARCHAR(3)  NOT NULL,
    amount      NUMERIC     NOT NULL,
    description TEXT,
    created_at  TIMESTAMP   NOT NULL,
    updated_at  TIMESTAMP   NOT NULL
);