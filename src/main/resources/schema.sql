CREATE TABLE savings_account (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    amount DOUBLE
);

CREATE TABLE savings_account_transaction (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    account_number VARCHAR(20) NOT NULL,
    amount DOUBLE,
    operation VARCHAR(10) NOT NULL,
    date_time DATE NOT NULL
);