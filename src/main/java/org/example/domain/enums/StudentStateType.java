package org.example.domain.enums;

public enum StudentStateType {
    GREEN("green"), RED("red"), YELLOW("yellow");
    private String value;
    StudentStateType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
