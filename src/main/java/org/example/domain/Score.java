package org.example.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Score {
    private Integer subjectId; //과목 고유 번호

    private Integer studentId; //수강생 고유번호

    private Map<Integer, Integer> scoreId; //회차 점수리스트

    private Character level; //점수 레벨

    public Score(Integer subjectId, Integer studentId, Map<Integer, Integer> scoreId, Character level) {
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.scoreId = scoreId;
        this.level = level;
    }

    public Score(Integer subjectId, Integer studentId, Map<Integer, Integer> scoreId) {
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.scoreId = scoreId;
    }
}
