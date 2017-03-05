package us.pomorscy.expenses.web.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import us.pomorscy.expenses.ExpensesApplication;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate>{

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException{
        return LocalDate.parse(p.getValueAsString(), ExpensesApplication.DATE_TIME_FORMATTER);
    }
}
