# Create expenses user
CREATE USER 'expensesuser'@'localhost' IDENTIFIED BY 'expenses';

# Create expenses database
CREATE SCHEMA `expenses` DEFAULT CHARACTER SET utf8 ;

# Grant expenses user admin rights to expenses database
GRANT ALL ON expenses.* TO 'expensesuser'@'localhost';
FLUSH PRIVILEGES;
