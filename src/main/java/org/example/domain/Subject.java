package org.example.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Subject {
    private Integer subjectId; //과목 고유 번호

    private String subjectName; //과목이름

    private String subjectType; //과목타입

    public Subject(Integer subjectId, String subjectName, String subjectType){
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectType = subjectType;
    }
}
