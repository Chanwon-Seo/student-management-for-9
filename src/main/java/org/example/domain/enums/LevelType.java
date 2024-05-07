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
    public static Character checkRequiredLevelType(String subjectType, Integer scoreInput) {
        if (scoreInput >= 95) {
            return LevelType.A.getLevelTypeValue();
        } else if (scoreInput >= 90) {
            return LevelType.B.getLevelTypeValue();
        } else if (scoreInput >= 80) {
            return LevelType.C.getLevelTypeValue();
        } else if (scoreInput >= 70) {
            return LevelType.D.getLevelTypeValue();
        } else if (scoreInput >= 60) {
            return LevelType.F.getLevelTypeValue();
        } else {
            return LevelType.N.getLevelTypeValue();
        }
    }

    public static Character checkElectiveLevelType(String subjectType, Integer scoreInput) {

        if (scoreInput >= 90) {
            return LevelType.A.getLevelTypeValue();
        } else if (scoreInput >= 80) {
            return LevelType.B.getLevelTypeValue();
        } else if (scoreInput >= 70) {
            return LevelType.C.getLevelTypeValue();
        } else if (scoreInput >= 60) {
            return LevelType.D.getLevelTypeValue();
        } else if (scoreInput >= 50) {
            return LevelType.F.getLevelTypeValue();
        } else {
            return LevelType.N.getLevelTypeValue();
        }

    }

}
