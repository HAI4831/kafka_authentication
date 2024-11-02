DROP TABLE IF EXISTS payments CASCADE;

CREATE TABLE payments (
               id SERIAL PRIMARY KEY,
               amount DECIMAL(10, 2) NOT NULL,
               status VARCHAR(50) NOT NULL,
                customer_id INT NOT NULL,
               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
               last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);