package us.pomorscy.expenses.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import us.pomorscy.expenses.ExpensesApplication;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateSerializer extends JsonSerializer<LocalDate>{

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException{
        gen.writeString(value.format(ExpensesApplication.DATE_TIME_FORMATTER));
    }
}