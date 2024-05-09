package org.example.domain.enums;

import lombok.Getter;

@Getter
public enum StudentStateType {
    GREEN("green"),
    RED("red"),
    YELLOW("yellow"),
    NOSTATUS("nostatus"),
    ;

    private String studentTypeValue;

    StudentStateType(String studentTypeValue) {
        this.studentTypeValue = studentTypeValue;
    }

    /**
     * FIXME 메서드 설명
     */
    public static StudentStateType studentStateType(String status) {
        StudentStateType stateType;

        for (StudentStateType s : StudentStateType.values()) {
            if (s.getStudentTypeValue().equals(status)) {
                stateType = s;

                return stateType;
            }
        }

        return NOSTATUS;
    }
}
