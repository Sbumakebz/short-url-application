CREATE TABLE short_url_mapping (
    id INTEGER NOT NULL PRIMARY KEY,
    long_url VARCHAR(1000) UNIQUE NOT NULL
);