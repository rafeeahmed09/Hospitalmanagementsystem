package NewProject.example.hospital.management.system.Entity.Type;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BloodType {

    A_Positive,
    A_Negative,
    B_Positive,
    B_Negative,
    AB_Negative,
    AB_Positive,
    O_Positive,
    O_Negative;

    @JsonCreator
    public static BloodType fromValue(String value) {
        if (value == null) {
            return null;
        }

        String normalizedValue = normalize(value);
        for (BloodType bloodType : values()) {
            if (normalize(bloodType.name()).equals(normalizedValue)) {
                return bloodType;
            }
        }

        throw new IllegalArgumentException("Invalid blood group: " + value);
    }

    private static String normalize(String value) {
        return value.trim()
                .replace("-", "_")
                .replace(" ", "_")
                .replace("_", "")
                .toUpperCase();
    }
}
