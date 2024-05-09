package org.example.service;

import org.example.db.DBManager;
import org.example.domain.Score;
import org.example.domain.Student;
import org.example.domain.Subject;
import org.example.domain.enums.LevelType;
import org.example.parser.ScoreParser;
import org.example.parser.StudentParser;
import org.example.parser.SubjectParser;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

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
    public void scoreCreateV2(Integer subjectIdInput, Integer studentIdInput, Integer roundInput, Integer scoreInput) {
        //FIXME 불필요 코드 완료
        Optional<Subject> findSubjectData;
        Optional<Student> findStudentData;
        try {
            findSubjectData = subjectParser.subjectEmptyCheckValid(subjectIdInput);
            findStudentData = studentParser.studentEmptyCheckValidV2(studentIdInput);
            studentParser.studentAndSubjectCheckValid(findSubjectData.get().getSubjectId(), findStudentData.get());
            scoreParser.scoreRoundInputOneToTenCheckValid(roundInput);
            scoreParser.scoreInputZeroToOneHundredCheckValid(scoreInput);

            scoreParser.scoreDuplicatedCheckValidv2(
                    findSubjectData.get().getSubjectId(),
                    findStudentData.get().getStudentId(),
                    roundInput
            );
        } catch (NullPointerException | IllegalArgumentException | IllegalStateException e) {
            throw new RuntimeException(e.getMessage());
        }


        Map<Integer, Integer> roundMap = new LinkedHashMap<>();
        roundMap.put(roundInput, scoreInput);

        Score score = new Score(findSubjectData.get().getSubjectId(),
                studentIdInput,
                roundMap,
                checkLevelType(findSubjectData.get().getSubjectType().getSubjectTypeValue(), scoreInput)
        );
        System.out.printf("%d / %d / %d회차에 %d점수 ( %s )등급이 저장 되었습니다.\n\n",
                findSubjectData.get().getSubjectId(),
                findStudentData.get().getStudentId(),
                roundInput,
                scoreInput,
                score.getLevelType()
        );

        dbManager.saveScore(score);
    }

    /**
     * @찬원 필수 또는 선택에 따른 등급 산정
     */
    private LevelType checkLevelType(String findSubjectData, Integer scoreInput) {
        LevelType levelType;
        if ("필수".equals(findSubjectData)) {
            levelType = LevelType.checkRequiredLevelType(scoreInput);
        } else {
            levelType = LevelType.checkElectiveLevelType(scoreInput);
        }
        return levelType;
    }

    /**
     * @차도범 수강생 아이디로 삭제
     */
    public void deleteScoreByStudentId(int studentId) {
        try {
            boolean b = dbManager.deleteScoreByStudentId(studentId);
            if (b) System.out.println("점수를 삭제했습니다..");
            else System.out.println("점수를 삭제하지 못햇습니다.");
            System.out.println();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }
        System.out.print("\n\n");
    }
}
