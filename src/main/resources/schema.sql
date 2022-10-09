CREATE DATABASE banking;
use banking;
CREATE TABLE savings_account (
    account_number VARCHAR(20) UNIQUE NOT NULL,
    amount DECIMAL(13, 2)
);