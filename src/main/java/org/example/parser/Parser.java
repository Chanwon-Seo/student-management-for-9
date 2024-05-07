package org.example.parser;

import org.example.db.DBManager;
import org.example.domain.Score;
import org.example.domain.Subject;

//@RequiredArgsConstructor
public class Parser {
    private final DBManager dbManager;
    private final StudentParser studentParser;
    private final ScoreParser scoreParser;
    private final SubjectParser subjectParser;

    public Parser(DBManager dbManager) {
        this.dbManager = dbManager;
        this.studentParser = new StudentParser();
        this.scoreParser = new ScoreParser();
        this.subjectParser = new SubjectParser();
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
