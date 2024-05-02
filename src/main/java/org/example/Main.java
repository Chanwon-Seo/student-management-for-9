package org.example;

import org.example.domain.Score;
import org.example.domain.Student;
import org.example.domain.Subject;
import org.example.service.ScoreService;

import java.util.List;

public class Main {
    static DBStorage dbStorage = new DBStorage();

    public static void main(String[] args) {
        //시작

        //수강생 점수 등록
        //수강생 고유번호 및 과목 고유번호 입력 받은 후
        //TODO Scanner로 입력
        Integer studentId = 2023001;
        Integer subjectId = 1;
        Long round = 0L;
        Long scoreNum = 100L;
        studentScoreSaveValidation(studentId, subjectId, round, scoreNum);
        Score score = new ScoreService().scoreCreateV1(subjectId, studentId, round, scoreNum);
        dbStorage.saveScoreList(score);
    }

    /**
     * 수강생의 점수 등록
     * 수강생의 고유번호
     * 과목 고유번호
     * 회차
     * 점수
     */
    private static void studentScoreSaveValidation(Integer studentId, Integer subjectId, Long roundNum, Long scoreNum) {
        List<Student> studentList = dbStorage.getStudentList();
        for (Student student : studentList) {
            if (!studentId.equals(student.getStudentId())) {
                System.out.println("존재하지 않는 수강생입니다.");
            }
        }

        List<Subject> subjectList = dbStorage.getSubjectList();
        for (Subject subject : subjectList) {
            if (!subjectId.equals(subject.getSubjectId())) {
                System.out.println("존재하지 않는 과목입니다.");
            }
        }
        if (roundNum < 1 || 10 < roundNum) {
            System.out.println("회차 범위는 1 ~ 10까지 입니다.");
        }
        if (scoreNum < 0 || 100 < scoreNum) {
            System.out.println("점수 범위는 0 ~ 100까지 입니다.");
        }

        List<Score> scoreList = dbStorage.getScoreList();
        if (scoreList.size() >= roundNum) {
            System.out.println("이미 등록된 회차입니다.");
        }

    }
}