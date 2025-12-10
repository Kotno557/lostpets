package br.lostpets.project.model.valueobject;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value object representing a Brazilian phone number
 * Supports both landline and mobile formats
 */
public class PhoneNumber {
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\d{10,11}");
    
    private final String value;
    private final PhoneType type;

    public enum PhoneType {
        LANDLINE,  // Telefone fixo (10 digits)
        MOBILE     // Celular (11 digits)
    }

    /**
     * Creates a PhoneNumber with validation
     * @param value Phone number string (digits only)
     * @throws IllegalArgumentException if phone format is invalid
     */
    public PhoneNumber(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        
        String cleaned = value.replaceAll("[^0-9]", "");
        
        if (!PHONE_PATTERN.matcher(cleaned).matches()) {
            throw new IllegalArgumentException("Invalid phone format: " + value);
        }
        
        this.value = cleaned;
        this.type = cleaned.length() == 11 ? PhoneType.MOBILE : PhoneType.LANDLINE;
    }

    public String getValue() {
        return value;
    }

    public PhoneType getType() {
        return type;
    }

    /**
     * Returns formatted phone number
     * Mobile: (XX) XXXXX-XXXX
     * Landline: (XX) XXXX-XXXX
     */
    public String getFormatted() {
        if (type == PhoneType.MOBILE) {
            return String.format("(%s) %s-%s", 
                value.substring(0, 2), 
                value.substring(2, 7), 
                value.substring(7));
        } else {
            return String.format("(%s) %s-%s", 
                value.substring(0, 2), 
                value.substring(2, 6), 
                value.substring(6));
        }
    }

    public boolean isMobile() {
        return type == PhoneType.MOBILE;
    }

    public boolean isLandline() {
        return type == PhoneType.LANDLINE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return getFormatted();
    }
}
