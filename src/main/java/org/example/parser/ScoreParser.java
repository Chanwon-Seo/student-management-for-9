package org.example.parser;

import org.example.db.DBManager;

public class ScoreParser {
    private final DBManager dbManager;

    static final int SCORE_ROUND_MIN_VALUE = 1;
    static final int SCORE_ROUND_MAX_VALUE = 10;
    static final int SCORE_MIN_VALUE = 0;
    static final int SCORE_MAX_VALUE = 100;

    public ScoreParser(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * @찬원 회차 범위 입력 검증
     * 1 ~ 10
     */
    public void scoreRoundInputOneToTenCheckValid(Integer roundInput) {
        if (SCORE_ROUND_MIN_VALUE <= roundInput && roundInput <= SCORE_ROUND_MAX_VALUE) {
            return;
        }
        throw new RuntimeException("회차 범위는 1 ~ 10까지 입니다.");
    }

    /**
     * @찬원 점수 범위 입력 검증
     * 0 ~ 100
     */
    public void scoreInputZeroToOneHundredCheckValid(Integer roundInput) {
        if (SCORE_MIN_VALUE <= roundInput && roundInput <= SCORE_MAX_VALUE) {
            return;
        }
        throw new RuntimeException("점수 범위는 0 ~ 100까지 입니다.");
    }

    /**
     * @찬원 회차 등록 여부 검증
     */
    public void scoreDuplicatedCheckValid(Integer roundInput) {
        if (dbManager.findByScores().size() >= roundInput) {
            throw new RuntimeException("이미 등록된 회차입니다.");
        }
    }

}
