package org.example.parser;

import org.example.db.DBManager;
import org.example.domain.Subject;

import java.util.HashSet;

//@RequiredArgsConstructor
public class Parser {
    //TODO 의존성 주입
    private final StudentParser studentParser;
    private final ScoreParser scoreParser;
    private final SubjectParser subjectParser;

    public Parser(DBManager dbManager) {
        this.studentParser = new StudentParser(dbManager);
        this.scoreParser = new ScoreParser(dbManager);
        this.subjectParser = new SubjectParser(dbManager);
    }

    /**
     * @찬원 수강생 점수 등록 검증
     */
    public Subject scoreCreate(Integer subjectIdInput, Integer studentIdInput, Integer roundInput, Integer scoreInput) {

        Subject findSubjectData = null;
        //TODO 예외 처리
        try {
            findSubjectData = subjectParser.subjectEmptyCheckValid(subjectIdInput);
            studentParser.studentEmptyCheckValid(findSubjectData, studentIdInput);
            scoreParser.scoreRoundInputOneToTenCheckValid(roundInput);
            scoreParser.scoreInputZeroToOneHundredCheckValid(scoreInput);

            scoreParser.scoreDuplicatedCheckValidv2(subjectIdInput, studentIdInput, roundInput);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
        return findSubjectData;
    }

    /**
     * @return
     * @성균 수강생 과목 등록 검증
     * id가 검증되면 참 반환
     */
    public boolean subjectIdCheck(Integer subjectId) {
        try {
            if (subjectParser.subjectIsEmptyCheck(subjectId)) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * @return
     * @성균 수강생 과목 중복 검증
     * 중복이면 거짓 반환
     */
    public boolean subjectIdDuplicationCheck(HashSet<Integer> dup, Integer subjectId) {
        try {
            if (subjectParser.subjectIdDuplicationCheck(dup, subjectId)) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * @return
     * @성균 수강생 과목 등록 검증
     * id가 검증되면 해당 subject 클래스 반환
     */
    public Subject subjectReturn(Integer subjectId) {
        Subject subject = null;
        try {
            subject = subjectParser.subjectEmptyCheckValid(subjectId);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return subject;
    }

    /**
     * @return
     * @성균 과목 등록 검증
     */

    public boolean subjectMinCheck(int rSub, int eSub) {
        try {
            if (subjectParser.subjectMinCheck(rSub, eSub)) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }


}
