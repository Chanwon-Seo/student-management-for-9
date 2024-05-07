package org.example.parser;

import org.example.db.DBManager;
import org.example.domain.Subject;

//@RequiredArgsConstructor
public class Parser {
    private final StudentParser studentParser;
    private final ScoreParser scoreParser;
    private final SubjectParser subjectParser;

    public Parser(DBManager dbManager) {
        this.studentParser = new StudentParser(dbManager);
        this.scoreParser = new ScoreParser(dbManager);
        this.subjectParser = new SubjectParser(dbManager);
    }

    /**
     * @return
     * @찬원 수강생 점수 등록 검증
     */
    public Subject scoreCreate(Integer subjectIdInput, Integer studentIdInput, Integer roundInput, Integer scoreInput) {
        Subject findSubjectData = null;
        try {
            findSubjectData = subjectParser.subjectEmptyCheckValid(subjectIdInput);
            studentParser.studentEmptyCheckValid(studentIdInput);
            scoreParser.scoreRoundInputOneToTenCheckValid(roundInput);
            scoreParser.scoreInputZeroToOneHundredCheckValid(scoreInput);

            scoreParser.scoreDuplicatedCheckValid(roundInput);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return findSubjectData;
    }
}
