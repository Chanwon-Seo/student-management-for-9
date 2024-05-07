package org.example.service;

import org.example.db.DBManager;
import org.example.domain.Score;

import java.util.List;
import java.util.Map;


public class StudentScoreRead {

    DBManager dbManager;
    public StudentScoreRead(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    // 과목의 회차: 점수 전체조회
    public void LoadScore(Integer studentId, Integer subjectId) {

        var score = FindScoresByStudentIdANDSubjectId(studentId, subjectId);

        for (int i = 0; i < score.size(); i++) {
            System.out.print("회차: " + score.keySet().toArray()[i]);
            System.out.println(" , 점수: " + score.values().toArray()[i]);
        }
    }

    // 회차 점수 수정
    public void UpdateScore(Integer studentId, Integer subjectId, Integer roundInput, Integer scoreInput) {

        var score = FindScoresByStudentIdANDSubjectId(studentId, subjectId);

        if(score.get(roundInput)==null) { //예외처리
            System.out.println("수정할 회차가 없습니다.");
        }
        else{
            score.put(roundInput,scoreInput);
        }
    }

    //과목별 평균등급 조회 (추가 - 점수관리)
    public void LoadAvgScore(Integer studentId, Integer subjectId) {
        var score = FindScoresByStudentIdANDSubjectId(studentId, subjectId);
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


    // 수강생 과목번호 받아 score 리스트 return
    public Map<Integer, Integer> FindScoresByStudentIdANDSubjectId(Integer studentId, Integer subjectId) {

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
