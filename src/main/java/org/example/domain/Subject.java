package org.example.domain;

import lombok.Getter;
import org.example.domain.enums.SubjectType;


@Getter
public class Subject {
    private Integer subjectId; //과목 고유 번호

    private String subjectName; //과목이름
    //TODO
    private SubjectType subjectType; //과목타입

    public Subject(Integer subjectId, String subjectName, SubjectType subjectType) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectType = subjectType;
    }
}
