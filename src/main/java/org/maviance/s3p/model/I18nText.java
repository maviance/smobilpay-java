package org.maviance.s3p.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Localized text entry. Used for service hints and field labels (e.g.
 * {@link Service#labelServiceNumber()}).
 *
 * @param language target language code (e.g. {@code "en"}, {@code "fr"})
 * @param localText localized text
 */
public record I18nText(
        @JsonProperty("language") String language,
        @JsonProperty("localText") String localText
) {
    @JsonCreator
    public I18nText {}
}
