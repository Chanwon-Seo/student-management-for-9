package org.example.domain;

import lombok.Getter;

import java.util.Set;

@Getter
public class Student {
    private Integer studentId; //수강생 고유 번호

    private String studentName;

    private String birthDay; //생년월일

    private Set<Integer> subjectId; //과목 고유 번호

    Enum studentState; //수강생상태

    public Student(Integer studentId, String studentName, String birthDay, Set<Integer> subjectId){
        this.studentId = studentId;
        this.studentName = studentName;
        this.birthDay = birthDay;
        this.subjectId = subjectId;
    }
}
