package uz.genesis.aiquest.webserver.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        int year = 0;
        int month = 0;
        int day = 1; // Set default day to 1
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String field = jsonParser.getCurrentName();
            jsonParser.nextToken(); // Move to value
            switch (field) {
                case "year":
                    year = jsonParser.getIntValue();
                    break;
                case "month":
                    month = jsonParser.getIntValue();
                    break;
                case "day":
                    day = jsonParser.getIntValue();
                    break;
            }
        }
        if (year == 0 && month == 0 && day == 0)
            return null;

        if (day == 0)
            day = 1;

        if (month == 0) {
            month = 1;
        }

        return LocalDate.of(year, month, day);
    }
}