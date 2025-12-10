package br.lostpets.project.model.valueobject;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value object representing a Brazilian postal code (CEP)
 * Format: XXXXX-XXX
 */
public class PostalCode {
    private static final Pattern CEP_PATTERN = Pattern.compile("\\d{5}-?\\d{3}");
    
    private final String value;

    /**
     * Creates a PostalCode with validation
     * @param value CEP string (with or without hyphen)
     * @throws IllegalArgumentException if CEP format is invalid
     */
    public PostalCode(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("CEP cannot be null or empty");
        }
        
        String cleaned = value.trim();
        if (!CEP_PATTERN.matcher(cleaned).matches()) {
            throw new IllegalArgumentException("Invalid CEP format: " + value);
        }
        
        // Normalize: always store with hyphen
        this.value = cleaned.length() == 8 
            ? cleaned.substring(0, 5) + "-" + cleaned.substring(5)
            : cleaned;
    }

    public String getValue() {
        return value;
    }

    /**
     * Returns CEP without hyphen (for API calls)
     */
    public String getValueWithoutHyphen() {
        return value.replace("-", "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostalCode that = (PostalCode) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
