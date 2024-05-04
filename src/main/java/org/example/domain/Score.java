package org.example.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Score {
    @Getter
    private Integer subjectId; //과목 고유 번호
    @Getter
    private Integer studentId; //수강생 고유번호

    @Getter
    private Map<Long, Long> scoreId = new HashMap<>(); //회차 점수리스트

    private Character level; //점수 레벨

    public Score(Integer subjectId, Integer studentId, Map<Long, Long> scoreId) {
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.scoreId = scoreId;
    }





}
