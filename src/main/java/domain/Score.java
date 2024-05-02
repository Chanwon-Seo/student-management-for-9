package domain;

import java.util.Map;

public class Score {
    private Integer subjectId; //과목 고유 번호
    private Integer studentId; //수강생 고유번호

    private Map<Long, Long> scoreId; //회차 점수리스트

    private Character level; //점수 레벨
}
