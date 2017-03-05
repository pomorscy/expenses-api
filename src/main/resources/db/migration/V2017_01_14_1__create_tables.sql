CREATE TABLE IF NOT EXISTS categories (
  id VARCHAR(36) NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
  name VARCHAR(32) NOT NULL,
  id VARCHAR(36) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS expenses (
  id VARCHAR(36) NOT NULL,
  amount DECIMAL(25,2) NOT NULL,
  date DATE NULL,
  description VARCHAR(255) NULL,
  categories_id VARCHAR(36) NOT NULL,
  users_id VARCHAR(36) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_expenses_categories
    FOREIGN KEY (categories_id)
    REFERENCES categories (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_expenses_users
    FOREIGN KEY (users_id)
    REFERENCES users (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);
