package org.example.service;

import org.example.domain.Score;

import java.util.LinkedHashMap;
import java.util.Map;

public class ScoreService {

    /**
     * @찬원
     * 수강생 점수 등록 메서드
     */
    public Score scoreCreateV1(Integer subjectIdInput, Integer studentIdInput, Integer roundInput, Integer scoreInput) {
        Map<Integer, Integer> roundMap = new LinkedHashMap<>();
        roundMap.put(roundInput, scoreInput);

        //TODO 점수에 대한 level 검증
        return new Score(subjectIdInput,
                studentIdInput,
                roundMap,
                'c'
        );
    }

}
