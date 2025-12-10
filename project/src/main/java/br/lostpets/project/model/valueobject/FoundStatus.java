package br.lostpets.project.model.valueobject;

/**
 * Enum representing the status of found animals
 * 
 * A = Ativo (Active/Confirmed)
 * W = Waiting (Aguardando confirmação)
 */
public enum FoundStatus {
    ACTIVE("A", "Ativo"),
    WAITING("W", "Aguardando");

    private final String code;
    private final String description;

    FoundStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Get FoundStatus from code
     * @param code Status code (A, W)
     * @return FoundStatus enum value
     * @throws IllegalArgumentException if code is invalid
     */
    public static FoundStatus fromCode(String code) {
        for (FoundStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}
