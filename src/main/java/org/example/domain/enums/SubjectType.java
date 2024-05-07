package org.example.domain.enums;

import lombok.Getter;

@Getter
public enum SubjectType {
    REQUIRED("필수"),
    ELECTIVE("선택"),
    ;

    private final String subjectTypeValue;

    SubjectType(String subjectTypeValue) {
        this.subjectTypeValue = subjectTypeValue;
    }

}
