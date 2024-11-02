DROP TABLE IF EXISTS orders CASCADE;

CREATE TABLE orders (
               id SERIAL PRIMARY KEY,
               price DECIMAL(10, 2) NOT NULL,
               status VARCHAR(50) NOT NULL,
               customer_id INT NULL,
               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
               last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);