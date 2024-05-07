package org.example.service;

import org.example.DBStorage;
import org.example.domain.Score;




public class StudentScoreRead {

    DBManager dbManager;
    StudentScoreRead(DBManager dbManager){
        this.dbManager = dbManager;
    }

    public void LoadScore(Integer studentId, Integer subjectId) {

        System.out.println("학생고유번호: " + studentId + "  과목번호: " + subjectId);

        List<Score> score = DBStorage.getScoreList();
        for (Score s : score) {
            if (s.getStudentId().equals(studentId) && s.getSubjectId().equals(subjectId)) {
                var temp = s.getScoreId();
                for (int i = 0; i < temp.size(); i++) {
                    System.out.print("회차: " + temp.keySet().toArray()[i]);
                    System.out.println(" , 점수: " + temp.values().toArray()[i]);
                }
            }
        }
    }

    //과목별 평균등급 조회 (추가 - 점수관리)
    public void LoadAvgScore(Integer studentId, Integer subjectId){

    }

    //특정상태 수강생들의 필수 과목 평균 등급 (추가 - 점수관리)
    public void LoadStudentStateOfRequiredSubject(String StudentState){

    }

}
