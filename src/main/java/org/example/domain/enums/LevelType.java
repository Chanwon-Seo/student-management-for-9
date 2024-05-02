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

    //TODO 과목타입 분류
}
