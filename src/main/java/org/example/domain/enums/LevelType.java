package org.example.domain.enums;

import lombok.Getter;

@Getter
public enum LevelType {
    A('A'),
    B('B'),
    C('C'),
    D('D'),
    F('F'),
    N('N'),
    ;


    private final Character levelTypeValue;

    LevelType(Character levelTypeValue) {
        this.levelTypeValue = levelTypeValue;
    }

    // TODO 과목타입 분류
    public static LevelType checkRequiredLevelType(String subjectType, Integer scoreInput) {
        if (scoreInput >= 95) {
            return LevelType.A;
        } else if (scoreInput >= 90) {
            return LevelType.B;
        } else if (scoreInput >= 80) {
            return LevelType.C;
        } else if (scoreInput >= 70) {
            return LevelType.D;
        } else if (scoreInput >= 60) {
            return LevelType.F;
        } else {
            return LevelType.N;
        }
    }

    public static LevelType checkElectiveLevelType(String subjectType, Integer scoreInput) {

        if (scoreInput >= 90) {
            return LevelType.A;
        } else if (scoreInput >= 80) {
            return LevelType.B;
        } else if (scoreInput >= 70) {
            return LevelType.C;
        } else if (scoreInput >= 60) {
            return LevelType.D;
        } else if (scoreInput >= 50) {
            return LevelType.F;
        } else {
            return LevelType.N;
        }

    }

}
