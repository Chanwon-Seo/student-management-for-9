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

    /**
     * 필수과목 등급 산정
     */
    public static LevelType checkRequiredLevelType(Integer scoreInput) {
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

    /**
     * 선택과목 등급 산정
     */
    public static LevelType checkElectiveLevelType(Integer scoreInput) {

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