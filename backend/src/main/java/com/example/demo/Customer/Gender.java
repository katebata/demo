package com.example.demo.Customer;


import java.util.Arrays;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");


    private final String label;

    Gender(String label) {
        this.label = label;
    }


    public String getLabel() {
        return label;
    }

    public static Gender fromLabel(String label) {
        for (Gender g : values()) {
            if (g.label.equalsIgnoreCase(label)) {
                return g;
            }
        }

        throw new IllegalArgumentException(
                String.format("Invalid gender label '%s'; expected one of %s",
                        label, Arrays.toString(values()))
        );
    }
}
