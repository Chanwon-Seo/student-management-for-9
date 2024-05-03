package org.example.service;

import org.example.DBStorage;
import org.example.domain.Score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentScoreRead {

    public DBStorage storage = new DBStorage();

    public void TempSaveArea() {
        Map<Long, Long> scoreId = new HashMap<>();
        scoreId.put(1L, 90L);
        scoreId.put(2L, 90L);
        scoreId.put(3L, 90L);

        Map<Long, Long> personalscoreId = new HashMap<>();
        personalscoreId.put(100L, 900L);
        personalscoreId.put(1234L, 9L);
        personalscoreId.put(103L, 98L);

        List<Score> scoreListTemp = new ArrayList<>();
        scoreListTemp.add(new Score(0, 0, scoreId));
        scoreListTemp.add(new Score(0, 0, scoreId));
        scoreListTemp.add(new Score(1, 1, personalscoreId)); //찾을 데이터
        scoreListTemp.add(new Score(1, 2, scoreId));
        scoreListTemp.add(new Score(2, 1, scoreId));
        scoreListTemp.add(new Score(0, 0, scoreId));

        storage.setScoreList(scoreListTemp);
    }


    public void LoadScore(Integer studentId, Integer subjectId) {

        System.out.println("학생고유번호: " + studentId +"  과목번호: "+ subjectId);
        
        List<Score> score = storage.getScoreList();
        for (Score s : score) {
            if(s.getStudentId().equals(studentId) && s.getSubjectId().equals(subjectId))
            {
                 var temp = s.getScoreId();
                 for( int i=0;i<temp.size();i++){
                     System.out.print("회차: "+temp.keySet().toArray()[i]);
                     System.out.println(" , 점수: "+temp.values().toArray()[i]);
                 }
            }
        }
    }

    public static void main(String[] args) {
        StudentScoreRead studentScoreRead = new StudentScoreRead();
        studentScoreRead.TempSaveArea();

        studentScoreRead.LoadScore(1, 1);
        //System.out.println("hello");
    }


}