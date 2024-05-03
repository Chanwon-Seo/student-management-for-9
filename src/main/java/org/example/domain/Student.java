package org.example.domain;

import lombok.Getter;

import java.util.Set;

@Getter
public class Student {
    private Integer studentIdInteger; //수강생 고유 번호

    private String studentName;

    private String birthDay; //생년월일

    private Set<Integer> subjectId; //과목 고유 번호

    private String studentState; //수강생상태

    public Student(Integer studentIdInteger, String studentName, String birthDay, Set<Integer> subjectId){
        this.studentIdInteger = studentIdInteger;
        this.studentName = studentName;
        this.birthDay = birthDay;
        this.subjectId = subjectId;
    }

}
