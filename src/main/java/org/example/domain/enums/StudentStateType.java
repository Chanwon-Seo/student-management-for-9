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
     * @성균
     * 수강생 상태 검증
     * 없는 경우 NOSTATUS 반환
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
