package br.lostpets.project.model.valueobject;

/**
 * Enum representing the status of a lost pet
 * 
 * P = Pendente (Pending/Lost)
 * A = Achado (Found)
 * W = Waiting (Aguardando confirmação)
 */
public enum PetStatus {
    PENDING("P", "Pendente"),
    FOUND("A", "Achado"),
    WAITING("W", "Aguardando");

    private final String code;
    private final String description;

    PetStatus(String code, String description) {
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
     * Get PetStatus from code
     * @param code Status code (P, A, W)
     * @return PetStatus enum value
     * @throws IllegalArgumentException if code is invalid
     */
    public static PetStatus fromCode(String code) {
        for (PetStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}
