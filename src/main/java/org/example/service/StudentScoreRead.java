package org.example.service;

import org.example.db.DBManager;
import org.example.domain.Score;
import org.example.domain.Subject;
import org.example.domain.enums.LevelType;
import org.example.domain.enums.SubjectType;
import org.example.parser.ScoreParser;
import org.example.parser.StudentParser;
import org.example.parser.SubjectParser;

import java.util.List;
import java.util.Map;


public class StudentScoreRead {

    DBManager dbManager;
    private final StudentParser studentParser;
    private final SubjectParser subjectParser;
    private final ScoreParser scoreParser;

    public StudentScoreRead(DBManager dbManager) {
        this.dbManager = dbManager;
        this.studentParser = new StudentParser(dbManager);
        this.subjectParser = new SubjectParser(dbManager);
        this.scoreParser = new ScoreParser(dbManager);
    }

    // 과목의 [회차: 점수] 전체조회
    public void LoadScore(Integer studentId, Integer subjectId) {
        var score = FindScoresByStudentIdANDSubjectId(studentId, subjectId);

        if (score == null) { //TEMPEXCEPTION
            System.out.println("해당 과목의 점수가 존재하지 않습니다");
            return;
        }

        //등급계산
        SubjectType levelType = dbManager.findOneBySubject(subjectId).getSubjectType();
        LevelType tempLevelType = LevelType.A;

        for (int i = 0; i < score.size(); i++) {
            System.out.print(score.keySet().toArray()[i]+ " 회차: " );
            // 등급계산
            if (levelType == SubjectType.REQUIRED)
                tempLevelType = LevelType.checkRequiredLevelType("", (Integer) score.values().toArray()[i]);
            else
                tempLevelType = LevelType.checkElectiveLevelType("", (Integer) score.values().toArray()[i]);
            System.out.println(tempLevelType);
        }
    }

    // 회차 점수 수정
    public void UpdateScore(Integer studentId, Integer subjectId, Integer roundInput, Integer scoreInput) {

        var score = FindScoresByStudentIdANDSubjectId(studentId, subjectId);

        if (score == null || !score.containsKey(roundInput)) { //TEMPEXCEPTION
            System.out.println("해당 과목의 회차가 존재하지 않습니다");
            return;
        }

        scoreParser.scoreInputZeroToOneHundredCheckValid(scoreInput);

        score.put(roundInput, scoreInput);
        System.out.println(roundInput + " 회차 : " + scoreInput + "점 수정완료!");
    }

    //과목별 평균등급 조회 (추가 - 점수관리)
    public void LoadAvgScore(Integer studentId, Integer subjectId) {
        var score = FindScoresByStudentIdANDSubjectId(studentId, subjectId);
        if (score == null) { //TEMP EXCEPTION
            System.out.println("해당 과목의 점수가 존재하지 않습니다");
            return;
        }

        double sum = 0, avg = 0;

        for (int i = 0; i < score.size(); i++) {
            sum += (double) score.values().toArray()[i];
        }
        avg = sum / score.size();
        System.out.println("해당과목의 평균은 " + avg + "입니다.");
    }


    //특정상태 수강생들의 필수 과목 평균 등급 (추가 - 점수관리)
    public void LoadStudentStateOfRequiredSubject(String StudentState) {

    }


    /* Util */

    // 수강생 과목번호 받아 score 리스트 return
    public Map<Integer, Integer> FindScoresByStudentIdANDSubjectId(Integer studentId, Integer subjectId) {

        Subject findSubjectData = subjectParser.subjectEmptyCheckValid(subjectId);
        studentParser.studentEmptyCheckValid(findSubjectData, studentId);

        if (dbManager.findByScores().isEmpty() || dbManager.findByScores() == null) return null;

        System.out.println("학생고유번호: " + studentId + "  과목번호: " + subjectId);
        List<Score> score = dbManager.findByScores();

        for (Score s : score) {
            if (s.getStudentId().equals(studentId) && s.getSubjectId().equals(subjectId)) {
                var temp = s.getScoreId();
                if (temp.size() <= 0) { //TEMP EXCEPTION
                    System.out.println("해당 과목의 점수가 없습니다");
                    break;
                }
                return temp;
            }
        }
        return null;
    }

}
