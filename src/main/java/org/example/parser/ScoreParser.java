package org.example.parser;


import org.example.DBStorage;
import org.example.domain.Score;

import java.util.List;

public class ScoreParser {
    /**
     * @찬원
     * 회차 범위 입력 검증
     * 1 ~ 10
     */
    public void scoreRoundInputOneToTenCheckValid(Integer roundInput) {
        if (1 <= roundInput && roundInput <= 10) {
            return;
        }
        throw new RuntimeException("회차 범위는 1 ~ 10까지 입니다.");
    }

    /**
     * @찬원
     * 점수 범위 입력 검증
     * 0 ~ 100
     */
    public void scoreInputZeroToOneHundredCheckValid(Integer roundInput) {
        if (0 <= roundInput && roundInput <= 100) {
            return;
        }
        throw new RuntimeException("점수 범위는 0 ~ 100까지 입니다.");
    }

    /**
     * @찬원
     * 회차 등록 여부 검증
     */
    public void scoreDuplicatedCheckValid(Integer roundInput) {
        if (DBStorage.getScoreList().size() >= roundInput) {
            throw new RuntimeException("이미 등록된 회차입니다.");
        }
    }

}
