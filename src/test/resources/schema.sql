CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    owner_last_name VARCHAR(255) NOT NULL,
    owner_first_name VARCHAR(255) NOT NULL,
    bank_code INT NOT NULL,
    branch_number INT NOT NULL,
    account_number INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE activity (
    id BIGSERIAL PRIMARY KEY,
    owner_account_id BIGINT NOT NULL,
    source_account_id BIGINT NOT NULL,
    target_account_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    amount BIGINT NOT NULL
);

CREATE TABLE bank (
    code INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE branch (
    bank_code INT NOT NULL,
    number INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (bank_code, number),
    FOREIGN KEY (bank_code) REFERENCES bank(code)
);