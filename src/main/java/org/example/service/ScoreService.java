package org.example.service;

import org.example.db.DBManager;
import org.example.domain.Score;
import org.example.domain.Subject;
import org.example.domain.enums.LevelType;
import org.example.parser.ScoreParser;
import org.example.parser.StudentParser;
import org.example.parser.SubjectParser;

import java.util.LinkedHashMap;
import java.util.Map;

public class ScoreService {
    private final DBManager dbManager;
    private final StudentParser studentParser;
    private final SubjectParser subjectParser;
    private final ScoreParser scoreParser;

    public ScoreService(DBManager dbManager) {
        this.dbManager = dbManager;
        this.studentParser = new StudentParser(dbManager);
        this.subjectParser = new SubjectParser(dbManager);
        this.scoreParser = new ScoreParser(dbManager);
    }

    /**
     * @찬원 수강생 점수 등록 메서드
     */
    public void scoreCreateV1(Integer subjectIdInput, Integer studentIdInput, Integer roundInput, Integer scoreInput) {

        Subject findSubjectData = subjectParser.subjectEmptyCheckValid(subjectIdInput);

        studentParser.studentEmptyCheckValid(findSubjectData, studentIdInput);
        scoreParser.scoreRoundInputOneToTenCheckValid(roundInput);
        scoreParser.scoreInputZeroToOneHundredCheckValid(scoreInput);

        scoreParser.scoreDuplicatedCheckValidv2(subjectIdInput, studentIdInput, roundInput);


        Map<Integer, Integer> roundMap = new LinkedHashMap<>();
        roundMap.put(roundInput, scoreInput);

        //TODO 점수에 대한 level 검증
        Score score = new Score(findSubjectData.getSubjectId(),
                studentIdInput,
                roundMap,
                checkLevelType(findSubjectData.getSubjectType().getSubjectTypeValue(), scoreInput)
        );

        dbManager.saveScore(score);
    }

    /**
     * @찬원 필수 또는 선택에 따른 등급 산정
     */
    private static LevelType checkLevelType(String findSubjectData, Integer scoreInput) {
        LevelType levelType;
        if ("필수".equals(findSubjectData)) {
            levelType = LevelType.checkRequiredLevelType(findSubjectData, scoreInput);
        } else {
            levelType = LevelType.checkElectiveLevelType(findSubjectData, scoreInput);
        }
        return levelType;
    }

    /*
     * @차도범
     * 수강생 아이디로 삭제
     * */
    public void deleteScoreByStudentId(int studentId) {
        try {
            boolean b = dbManager.deleteScoreByStudentId(studentId);
            if (b) System.out.println("점수를 삭제했습니다..");
            else System.out.println("점수를 삭제하지 못햇습니다.");
        } catch (RuntimeException e) {
//            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~");
        }
        System.out.print("\n\n");
    }
}
