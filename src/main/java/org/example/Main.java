package org.example;

import org.example.parser.ScoreParser;
import org.example.service.ScoreService;
import org.example.parser.StudentParser;
import org.example.parser.SubjectParser;

public class Main {
    static DBStorage dbStorage = new DBStorage();
    static StudentParser studentParser = new StudentParser();
    static SubjectParser subjectParser = new SubjectParser();
    static ScoreParser scoreParser = new ScoreParser();
    public static int uNumber = 2024000;

    public static void main(String[] args) {

    }

    /**
     * 수강생 점수 등록
     */
    private static void scoreCreate(Integer subjectIdInput, Integer studentIdInput, Integer roundInput, Integer scoreInput) {
        //입력값 검증
        try {
            subjectParser.subjectEmptyCheckValid(subjectIdInput);
            studentParser.studentEmptyCheckValid(dbStorage, studentIdInput);
            scoreParser.scoreRoundInputOneToTenCheckValid(roundInput);
            scoreParser.scoreInputZeroToOneHundredCheckValid(scoreInput);

            scoreParser.scoreDuplicatedCheckValid(dbStorage, roundInput);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        dbStorage.saveScoreList(new ScoreService().scoreCreateV1(subjectIdInput, studentIdInput, roundInput, scoreInput));
    }
}