package org.example.service;

import org.example.domain.Score;
import org.example.domain.Subject;
import org.example.domain.enums.LevelType;

import java.util.LinkedHashMap;
import java.util.Map;

public class ScoreService {

    /**
     * @찬원 수강생 점수 등록 메서드
     */
    public Score scoreCreateV1(Subject findSubjectData, Integer studentIdInput, Integer roundInput, Integer scoreInput) {
        Map<Integer, Integer> roundMap = new LinkedHashMap<>();
        roundMap.put(roundInput, scoreInput);

        //TODO 점수에 대한 level 검증
        return new Score(findSubjectData.getSubjectId(),
                studentIdInput,
                roundMap,
                checkLevelType(findSubjectData.getSubjectType().getSubjectTypeValue(), scoreInput)
        );
    }

    /**
     * @찬원 필수 또는 선택에 따른 등급 산정
     */
    private static LevelType checkLevelType(String findSubjectData, Integer scoreInput) {
        LevelType levelType;
        if ("필수".equals(findSubjectData)) {
            levelType = LevelType.checkRequiredLevelType(findSubjectData, scoreInput);
        } else {
            levelType = LevelType.checkElectiveLevelType(findSubjectData, scoreInput);
        }
        return levelType;
    }


}
