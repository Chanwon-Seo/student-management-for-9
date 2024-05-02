package org.example.domain;

import lombok.Getter;

import java.util.Set;

@Getter
public class Student {
    private Integer studentId; //수강생 고유 번호


    private String birthDay; //생년월일

    private Set<Long> subjectId; //과목 고유 번호

    private String studentState; //수강생상태
}
