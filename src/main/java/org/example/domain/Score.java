package org.example.domain;

import lombok.Getter;

import java.util.Map;

@Getter
public class Score {
    private Integer subjectId; //과목 고유 번호
    private Integer studentId; //수강생 고유번호

    private Map<Long, Long> scoreId; //회차 점수리스트

    private Character level; //점수 레벨

    public Score(Integer subjectId, Integer studentId, Map<Long, Long> scoreId, Character level) {
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.scoreId = scoreId; //LinkedHashMap
        this.level = level;
    }
}
