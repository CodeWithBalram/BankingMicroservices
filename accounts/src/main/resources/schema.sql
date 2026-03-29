CREATE TABLE customer (
                          customer_id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          email VARCHAR(100) NOT NULL UNIQUE,
                          mobile_number VARCHAR(20) NOT NULL,
                          created_at DATE NOT NULL,
                          created_by VARCHAR(20) NOT NULL,
                          updated_at DATE DEFAULT NULL,
                          updated_by VARCHAR(20) DEFAULT NULL
);

CREATE TABLE accounts (
                          account_number INT AUTO_INCREMENT PRIMARY KEY,
                          customer_id INT NOT NULL,
                          account_type VARCHAR(100) NOT NULL,
                          branch_address VARCHAR(200) NOT NULL,
                          balance DECIMAL(10,2) DEFAULT 0.00,
                          created_at DATE NOT NULL,
                          created_by VARCHAR(20) NOT NULL,
                          updated_at DATE DEFAULT NULL,
                          updated_by VARCHAR(20) DEFAULT NULL,

                          FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);