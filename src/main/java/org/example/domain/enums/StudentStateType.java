package org.example.domain.enums;

import lombok.Getter;

@Getter
public enum StudentStateType {
    GREEN("green"),
    RED("red"),
    YELLOW("yellow");
    private String studentTypeValue;

    StudentStateType(String studentTypeValue) {
        this.studentTypeValue = studentTypeValue;
    }
}
