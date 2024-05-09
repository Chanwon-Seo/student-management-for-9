package org.example.parser;


import org.example.db.DBManager;
import org.example.domain.Score;
import org.example.domain.Student;

import java.util.List;
import java.util.Optional;

import java.util.Map;
import java.util.Set;

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
     * throw 1 ~ 10의 범위를 벗어난 경우
     */
    public void scoreRoundInputOneToTenCheckValid(Integer roundInput) {
        if (SCORE_ROUND_MIN_VALUE <= roundInput && roundInput <= SCORE_ROUND_MAX_VALUE) {
            return;
        }
        throw new IllegalArgumentException("회차 범위는 " + SCORE_ROUND_MIN_VALUE + " ~ " + SCORE_ROUND_MAX_VALUE + " 까지 입니다.\n");
    }

    /**
     * @찬원 점수 범위 입력 검증
     * throw 0 ~ 100의 범위를 벗어난 경우
     */
    public void scoreInputZeroToOneHundredCheckValid(Integer roundInput) {
        if (SCORE_MIN_VALUE <= roundInput && roundInput <= SCORE_MAX_VALUE) {
            return;
        }
        throw new IllegalArgumentException("점수 범위는 " + SCORE_MIN_VALUE + " ~ " + SCORE_MAX_VALUE + " 까지 입니다.\n");
    }

    /**
     * @찬원 회차 등록 여부 검증
     * throw 등록된 회차가 없지만 사용자로부터 입력 받은 회차가 1회차가 아닌 경우
     * throw 이미 등록된 회차 등록인 경우
     * throw 이전 회차 미등록인 경우
     */
    public void scoreDuplicatedCheckValidv2(Integer subjectIdInput, Integer studentIdInput, Integer roundInput) {
        Optional<Score> findScoreData = Optional.empty();
        for (Score score : dbManager.findByScores()) {
            if (subjectIdInput.equals(score.getSubjectId()) && studentIdInput.equals(score.getStudentId())) {
                findScoreData = Optional.of(score);
                break;
            }
        }

        //등록된 회차가 없지만 사용자로부터 입력 받은 회차가 1회차가 아닌 경우
        if (findScoreData.isEmpty() && roundInput != 1) {
            throw new NullPointerException("1회차가 입력되지 않았습니다.\n");
        }

        //등록된 회차가 있는 경우
        if (findScoreData.isPresent()) {
            int scoreSize = findScoreData.get().getScoreMap().size();

            //이미 등록된 회차 등록인 경우
            if (scoreSize >= roundInput) {
                throw new IllegalStateException("이미 등록된 회차입니다.\n");
            }
            //이전 회차 미등록인 경우
            if (scoreSize != roundInput - 1) {
                throw new IllegalArgumentException("이전 회차에서 미등록한 회차가 있습니다.\n");
            }
        }
    }

    /**
     * @세미 점수 수정시, 존재하는 회차인지
     * throw 회차가 존재하지 않을 때
     */
    public void scoreUpdateCheckValid(Integer subjectIdInput, Integer studentIdInput, Integer roundInput) {
        Score findScoreData = null;
        for (Score score : dbManager.findByScores()) {
            if (subjectIdInput.equals(score.getSubjectId()) && studentIdInput.equals(score.getStudentId())) {
                findScoreData = score;
                break;
            }
        }
        if (findScoreData == null) {
            throw new NullPointerException("해당 과목의 점수 정보가 없습니다");
        }
    }


    /**
     * @세미 해당 수강생의 과목에 점수가 존재하는지 (student subject id)
     * throw 회차가 존재하지 않을 때
     */
    public void studentScoreNullCheck(Integer studentId, Integer subjectId) {
        Optional<Student> findStudent = dbManager.findOneByStudent(studentId);
        Set<Integer> haveSubject = findStudent.get().getSubjectSet();
        if (!haveSubject.contains(subjectId)) {
            throw new NullPointerException("과목을 가지고 있지 않습니다.\n");
        }
        List<Score> scores = dbManager.findByScores();

        boolean isScoreNotNull = false;
        for (Score s : scores) {
            if (s.getStudentId().equals(studentId) && s.getSubjectId().equals(subjectId)) {
                isScoreNotNull = true;
            }
        }
        //if (!isScoreNotNull) throw new NullPointerException("점수가 없습니다.\n");

    }


}
