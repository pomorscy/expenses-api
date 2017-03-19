package us.pomorscy.expenses;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import us.pomorscy.expenses.web.json.ExpensesObjectMapper;

@SpringBootApplication
public class ExpensesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpensesApplication.class, args);
    }

    @Bean
    @Primary
    public ObjectMapper serializingObjectMapper() {
        return new ExpensesObjectMapper();
    }
}
