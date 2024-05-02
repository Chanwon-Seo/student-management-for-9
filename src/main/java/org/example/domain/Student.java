package org.example.domain;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Student {
    private Integer studentIdInteger; //수강생 고유 번호

    private String birthDay; //생년월일

    private Set<Long> subjectId = new HashSet<>(); //과목 고유 번호

    private String studentState; //수강생상태

    private String studentName;

    public Student(Integer studentIdInteger, String birthDay, Set<Long> subjectId, String studentState, String studentName) {
        this.studentIdInteger = studentIdInteger;
        this.birthDay = birthDay;
        this.subjectId = subjectId;
        this.studentState = studentState;
        this.studentName = studentName;
    }
}
