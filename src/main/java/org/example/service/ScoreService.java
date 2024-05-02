package org.example.service;

import org.example.domain.Score;

import java.util.LinkedHashMap;
import java.util.Map;

public class ScoreService {
    // 수강생 점수 등록 메서드
    public Score scoreCreateV1(Integer subjectId, Integer studentId, Long round, Long scoreNum) {
        Map<Long, Long> map = new LinkedHashMap<>();
        map.put(round, scoreNum);

        return new Score(subjectId,
                studentId,
                map,
                'c'
        );

    }

}
