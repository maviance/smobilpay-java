package org.maviance.smobilpay.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Regression tests for {@link LenientLocalDateDeserializer}. The wire format
 * for date-only fields is documented as {@code YYYY-MM-DD} but acceptance
 * has been observed emitting full ISO datetimes with offset for the same
 * fields. The deserializer must accept both and any reasonable variant
 * without breaking downstream parsing.
 *
 * <p>Tests exercise the deserializer through {@link JsonMapper#create()}
 * — the public path — so we also pin the wiring through Jackson.
 */
class LenientLocalDateDeserializerTest {

    private final ObjectMapper mapper = JsonMapper.create();

    private LocalDate parse(String wireValue) throws IOException {
        Holder h = mapper.readValue("{\"date\":" + wireValue + "}", Holder.class);
        return h.date;
    }

    @Test
    void parsesIsoLocalDate() throws Exception {
        assertThat(parse("\"2025-11-05\"")).isEqualTo(LocalDate.of(2025, 11, 5));
    }

    @Test
    void parsesOffsetDateTime() throws Exception {
        assertThat(parse("\"2025-11-05T00:00:00+01:00\"")).isEqualTo(LocalDate.of(2025, 11, 5));
    }

    @Test
    void parsesOffsetDateTimeWithNegativeOffset() throws Exception {
        assertThat(parse("\"2025-11-05T23:59:59-08:00\"")).isEqualTo(LocalDate.of(2025, 11, 5));
    }

    @Test
    void parsesZonedDateTimeWithBracketedZone() throws Exception {
        assertThat(parse("\"2025-11-05T12:30:00+01:00[Europe/Paris]\""))
                .isEqualTo(LocalDate.of(2025, 11, 5));
    }

    @Test
    void parsesInstantWithZ() throws Exception {
        assertThat(parse("\"2025-11-05T00:00:00Z\"")).isEqualTo(LocalDate.of(2025, 11, 5));
    }

    @Test
    void parsesLocalDateTimeWithoutOffset() throws Exception {
        assertThat(parse("\"2025-11-05T08:30:00\"")).isEqualTo(LocalDate.of(2025, 11, 5));
    }

    @Test
    void parsesNullAsNull() throws Exception {
        assertThat(parse("null")).isNull();
    }

    @Test
    void parsesEmptyStringAsNull() throws Exception {
        assertThat(parse("\"\"")).isNull();
    }

    @Test
    void parsesBlankStringAsNull() throws Exception {
        assertThat(parse("\"   \"")).isNull();
    }

    @Test
    void rejectsCompletelyMalformedString() {
        assertThatThrownBy(() -> parse("\"definitely not a date\""))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("definitely not a date");
    }

    /** Minimal holder so we exercise the deserializer through Jackson's field-binding path. */
    private static final class Holder {
        public LocalDate date;
    }
}
