package org.example.parser;


import org.example.db.DBManager;
import org.example.domain.Score;

public class ScoreParser {
    static final int SCORE_ROUND_MIN_VALUE = 1;
    static final int SCORE_ROUND_MAX_VALUE = 10;
    static final int SCORE_MIN_VALUE = 0;
    static final int SCORE_MAX_VALUE = 100;
    private final DBManager dbManager;

    public ScoreParser(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * @찬원 회차 범위 입력 검증
     * throw 1 ~ 10의 범위를 벗어난 경우
     */
    public void scoreRoundInputOneToTenCheckValid(Integer roundInput) {
        if (SCORE_ROUND_MIN_VALUE <= roundInput && roundInput <= SCORE_ROUND_MAX_VALUE) {
            return;
        }
        throw new RuntimeException("회차 범위는 " + SCORE_ROUND_MIN_VALUE + " ~ " + SCORE_ROUND_MAX_VALUE + " 까지 입니다.\n");
    }

    /**
     * @찬원 점수 범위 입력 검증
     * throw 0 ~ 100의 범위를 벗어난 경우
     */
    public void scoreInputZeroToOneHundredCheckValid(Integer roundInput) {
        if (SCORE_MIN_VALUE <= roundInput && roundInput <= SCORE_MAX_VALUE) {
            return;
        }
        throw new RuntimeException("점수 범위는 " + SCORE_MIN_VALUE + " ~ " + SCORE_MAX_VALUE + " 까지 입니다.\n");
    }

    /**
     * @찬원 회차 등록 여부 검증
     * throw 등록된 회차가 없는 경우
     * throw 동일한 회차 또는 이미 등록된 회차 등록인 경우
     * throw 이전 회차 미등록인 경우
     */
    public void scoreDuplicatedCheckValidv2(Integer subjectIdInput, Integer studentIdInput, Integer roundInput) {
        Score findScoreData = null;
        for (Score score : dbManager.findByScores()) {
            if (subjectIdInput.equals(score.getSubjectId()) && studentIdInput.equals(score.getStudentId())) {
                findScoreData = score;
                break;
            }
        }

        //등록된 회차가 없는 경우
        if (findScoreData == null && roundInput != 1) {
            throw new RuntimeException("1회차가 입력되지 않았습니다.\n");
        }

        //등록된 회차가 있는 경우
        if (findScoreData != null) {
            int scoreSize = findScoreData.getScoreId().size();

            //동일한 회차 또는 이미 등록된 회차 등록인 경우
            if (scoreSize >= roundInput) {
                throw new RuntimeException("이미 등록된 회차입니다.\n");
            }
            //이전 회차 미등록인 경우
            if (scoreSize == roundInput - 1) {
                throw new RuntimeException("이전 회차에서 미등록한 회차가 있습니다.\n");
            }
        }
    }

}
