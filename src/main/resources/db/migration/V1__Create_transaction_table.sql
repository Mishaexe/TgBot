CREATE TABLE transactions (
                              id BIGINT PRIMARY KEY,
                              amount DECIMAL(19, 2) NOT NULL,
                              date DATE NOT NULL
);