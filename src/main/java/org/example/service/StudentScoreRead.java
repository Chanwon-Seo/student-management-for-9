package org.example.service;

import org.example.db.DBManager;
import org.example.domain.Score;
import org.example.domain.Student;
import org.example.domain.Subject;
import org.example.domain.enums.LevelType;
import org.example.domain.enums.StudentStateType;
import org.example.domain.enums.SubjectType;
import org.example.parser.ScoreParser;
import org.example.parser.StudentParser;
import org.example.parser.SubjectParser;

import java.util.*;


public class StudentScoreRead {

    private final DBManager dbManager;
    private final StudentParser studentParser;
    private final SubjectParser subjectParser;
    private final ScoreParser scoreParser;

    public StudentScoreRead(DBManager dbManager) {
        this.dbManager = dbManager;
        this.studentParser = new StudentParser(dbManager);
        this.subjectParser = new SubjectParser(dbManager);
        this.scoreParser = new ScoreParser(dbManager);
    }

    //FIXME 메서드명
    // 과목의 [회차: 등급] 전체조회 (필수 - 과목의 회차별 등급 조회)
    public void LoadScore(Integer studentId, Integer subjectId) {
        Map<Integer, Integer> score = FindScoresByStudentIdANDSubjectId(studentId, subjectId);


        if (score == null) { //TEMPEXCEPTION
            System.out.println("해당 과목의 점수가 존재하지 않습니다");
            return;
        }

        System.out.println("학생고유번호: " + studentId + "  과목번호: " + subjectId);

        //등급계산
        SubjectType levelType = dbManager.findOneBySubject(subjectId).get().getSubjectType();

        //FIXME 초기화
        LevelType tempLevelType = LevelType.A;

        for (int i = 0; i < score.size(); i++) {
            System.out.print(score.keySet().toArray()[i] + " 회차: ");
            // 등급계산
            if (levelType == SubjectType.REQUIRED)
                tempLevelType = LevelType.checkRequiredLevelType((Integer) score.values().toArray()[i]);
            else
                tempLevelType = LevelType.checkElectiveLevelType((Integer) score.values().toArray()[i]);
            System.out.println(tempLevelType);
        }
    }

    //FIXME 메서드명
    // 회차 점수 수정 (필수 - 점수수정)
    public void UpdateScore(Integer studentId, Integer subjectId, Integer roundInput, Integer scoreInput) {

        Map<Integer, Integer> score = FindScoresByStudentIdANDSubjectId(studentId, subjectId);

        if (score == null || !score.containsKey(roundInput)) { //TEMPEXCEPTION
            System.out.println("해당 과목의 회차가 존재하지 않습니다");
            return;
        }

        scoreParser.scoreInputZeroToOneHundredCheckValid(scoreInput);

        score.put(roundInput, scoreInput);
        System.out.println(roundInput + " 회차 : " + scoreInput + "점 수정완료!");
    }

    //FIXME 메서드명
    //과목별 평균등급 조회 (추가 - 점수관리)
    public void LoadAvgScore(Integer studentId, Integer subjectId) {
        Map<Integer, Integer> score = FindScoresByStudentIdANDSubjectId(studentId, subjectId);
        if (score == null) { //TEMP EXCEPTION
            System.out.println("해당 과목의 점수가 존재하지 않습니다");
            return;
        }

        double sum = 0;
        double avg = 0;

        for (int i = 0; i < score.size(); i++) {
            sum += score.get(i + 1);
        }
        avg = sum / score.size();
        Optional<Subject> subject = dbManager.findOneBySubject(subjectId);
        System.out.println(subject.get().getSubjectName() + "과목의 평균은 " + avg + "입니다.");

    }

    //FIXME 메서드명
    //특정상태 수강생들의 필수 과목 평균 등급 (추가 - 점수관리)
    public void LoadStudentStateOfRequiredSubject(int state) {

        StudentStateType stateType = switch (state) {
            case 1 -> StudentStateType.GREEN;
            case 2 -> StudentStateType.RED;
            case 3 -> StudentStateType.YELLOW;
            default -> null;
        };


        List<Student> students = dbManager.findByStudents(); //basic
        List<Student> studentList = new LinkedList<>(); //가공
        for (Student student : students) {
            if (student.getStudentStateType() == stateType) {
                studentList.add(student);
            }
        } //특정상태 수강생들 뽑음 (studentList)

        double count = 0;
        double sum = 0;
        int avg = 0;
        //FIXME 초기화
        LevelType resultLevel = LevelType.A;
        for (Student student : studentList) {
            sum = 0;
            count = 0;
            for (Integer sub : student.getSubjectSet()) {
                boolean isRequired = dbManager.FindSubjectTypebySubjectId(sub);
                if (isRequired) {
                    if (LoadAvgScoreRequired(student.getStudentId(), sub) != 0) {
                        sum += LoadAvgScoreRequired(student.getStudentId(), sub); //해당 과목 평균을 넣기
                        count++;
                    }
                }
            }
            avg = (int) (sum / count); //평균들의 평균을 계산
            resultLevel = LevelType.checkRequiredLevelType(avg); //평균점수를 등급화

            if (avg != 0) System.out.println(student.getStudentName() + "의 필수 과목 평균 등급: " + resultLevel);
        }

    }


    /* Util */
    //FIXME 메서드명
    // 수강생 과목번호 받아 score 리스트 return
    public Map<Integer, Integer> FindScoresByStudentIdANDSubjectId(Integer studentId, Integer subjectId) {

        Optional<Subject> findSubjectData = subjectParser.subjectEmptyCheckValid(subjectId);
        studentParser.studentEmptyCheckValidV2(studentId);

        if (dbManager.findByScores().isEmpty() || dbManager.findByScores() == null) return null;

        List<Score> score = dbManager.findByScores();

        for (Score s : score) {
            if (s.getStudentId().equals(studentId) && s.getSubjectId().equals(subjectId)) {
                Map<Integer, Integer> temp = s.getScoreMap();
                if (temp.size() <= 0) { //TEMP EXCEPTION
                    System.out.println("해당 과목의 점수가 없습니다");
                    break;
                }
                return temp;
            }
        }
        return null;
    }

    //FIXME 메서드명
    public double LoadAvgScoreRequired(Integer studentId, Integer subjectId) {
        Map<Integer, Integer> score = FindScoresByStudentIdANDSubjectId(studentId, subjectId);
        if (score == null || score.isEmpty()) {
            return 0;
        }

        double sum = 0, avg = 0;

        for (int i = 0; i < score.size(); i++) {
            sum += (double) score.get(i + 1);
        }
        avg = sum / score.size();
        return avg;
    }

}
