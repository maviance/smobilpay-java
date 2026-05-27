package org.maviance.smobilpay.http;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

/**
 * Tolerant Jackson deserializer for {@link LocalDate}. The Smobilpay
 * partner API documents date-only fields (e.g. {@code Bill.billDate},
 * {@code Subscription.dueDate}) but its acceptance environment is known
 * to occasionally emit them as ISO datetimes with an offset
 * (e.g. {@code 2025-11-05T00:00:00+01:00}). The strict
 * {@code LocalDate.parse} bundled with {@code JavaTimeModule} rejects
 * those, breaking otherwise-valid responses.
 *
 * <p>This deserializer accepts, in order:
 * <ul>
 *   <li>{@code YYYY-MM-DD}          — ISO local date</li>
 *   <li>{@code YYYY-MM-DDTHH:MM:SS+HH:MM} — ISO offset datetime</li>
 *   <li>{@code YYYY-MM-DDTHH:MM:SS[Z]}    — ISO zoned datetime / instant</li>
 *   <li>{@code YYYY-MM-DDTHH:MM:SS}       — ISO local datetime</li>
 * </ul>
 * and returns the {@link LocalDate} portion. Null and empty strings
 * deserialize to {@code null}.
 */
final class LenientLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getValueAsString();
        if (text == null || text.isBlank()) {
            return null;
        }
        text = text.trim();
        try {
            return LocalDate.parse(text);
        } catch (DateTimeParseException ignored) {
            // fall through
        }
        try {
            return OffsetDateTime.parse(text).toLocalDate();
        } catch (DateTimeParseException ignored) {
            // fall through
        }
        try {
            return ZonedDateTime.parse(text).toLocalDate();
        } catch (DateTimeParseException ignored) {
            // fall through
        }
        try {
            return LocalDateTime.parse(text).toLocalDate();
        } catch (DateTimeParseException ignored) {
            // fall through
        }
        try {
            return Instant.parse(text).atOffset(ZoneOffset.UTC).toLocalDate();
        } catch (DateTimeParseException ignored) {
            // fall through
        }
        throw new IOException("Cannot deserialize LocalDate from '" + text + "'");
    }
}
