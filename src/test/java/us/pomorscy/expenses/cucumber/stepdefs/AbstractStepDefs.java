package us.pomorscy.expenses.cucumber.stepdefs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import us.pomorscy.expenses.ExpensesApplication;

@ContextConfiguration(classes = ExpensesApplication.class)
@SpringBootTest
public abstract class AbstractStepDefs {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    PlatformTransactionManager platformTransactionManager;

    private TransactionStatus status;

    protected void beginTransaction(){
        DefaultTransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
        status = platformTransactionManager.getTransaction(paramTransactionDefinition);
    }

    protected void rollbackChanges() {
        platformTransactionManager.rollback(status);
    }

}
