package org.example.service;

import org.example.db.DBManager;
import org.example.domain.Score;

import java.util.List;


public class StudentScoreRead {

    DBManager dbManager;

    public StudentScoreRead(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public void LoadScore(Integer studentId, Integer subjectId) {

        List<Score> score = dbManager.findByScores();

        System.out.println("학생고유번호: " + studentId + "  과목번호: " + subjectId);
        for (Score s : score) {
            if (s.getStudentId().equals(studentId) && s.getSubjectId().equals(subjectId)) {
                var temp = s.getScoreId();
                if (temp.size() <= 0) { //TEMP EXCEPTION
                    System.out.println("해당 과목의 점수가 없습니다");
                    break;
                }
                for (int i = 0; i < temp.size(); i++) {
                    System.out.print("회차: " + temp.keySet().toArray()[i]);
                    System.out.println(" , 점수: " + temp.values().toArray()[i]);
                }
            }
        }
    }

    //과목별 평균등급 조회 (추가 - 점수관리)
    public void LoadAvgScore(Integer studentId, Integer subjectId) {

        System.out.println("학생고유번호: " + studentId + "  과목번호: " + subjectId);
        double avg = 0;
        List<Score> score = dbManager.findByScores();
        for (Score s : score) {
            if (s.getStudentId().equals(studentId) && s.getSubjectId().equals(subjectId)) {
                var temp = s.getScoreId();
                if (temp.size() <= 0) { //TEMP EXCEPTION
                    System.out.println("해당 과목의 점수가 없습니다");
                    break;
                }

                double sum = 0;
                for (int i = 0; i < temp.size(); i++) {
                    sum += (double) temp.values().toArray()[i];
                }
                avg = sum / temp.size();
            }
        }
        System.out.println("해당과목의 평균은 " + avg + "입니다.");
    }

    // 회차 점수 수정
    public void UpdateScore(Integer studentId, Integer subjectId, Integer round) {

        System.out.println("수정 : 공사중인 함수입니다ㅋㅋ");
    }

    //특정상태 수강생들의 필수 과목 평균 등급 (추가 - 점수관리)
    public void LoadStudentStateOfRequiredSubject(String StudentState) {

    }
}
