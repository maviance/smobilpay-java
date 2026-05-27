package org.maviance.smobilpay.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;

/**
 * Pre-configured Jackson {@link ObjectMapper} factory. Sealed for internal use
 * but exposed for tests that need to round-trip fixtures.
 */
public final class JsonMapper {

    private JsonMapper() {}

    /** Builds a Jackson mapper configured for the Smobilpay wire format. */
    public static ObjectMapper create() {
        SimpleModule lenientDates = new SimpleModule();
        lenientDates.addDeserializer(LocalDate.class, new LenientLocalDateDeserializer());

        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(lenientDates)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
