package org.example.parser;

import org.example.domain.Subject;

//@RequiredArgsConstructor
public class Parser {
    private final StudentParser studentParser;
    private final ScoreParser scoreParser;
    private final SubjectParser subjectParser;

    public Parser() {
        this.studentParser = new StudentParser();
        this.scoreParser = new ScoreParser();
        this.subjectParser = new SubjectParser();
    }

    /**
     * @찬원 수강생 점수 등록 검증
     */
    public Subject scoreCreate(Integer subjectIdInput, Integer studentIdInput, Integer roundInput, Integer scoreInput) {
        Subject findSubjectData;
        findSubjectData = subjectParser.subjectEmptyCheckValid(subjectIdInput);
        studentParser.studentEmptyCheckValid(studentIdInput);
        scoreParser.scoreRoundInputOneToTenCheckValid(roundInput);
        scoreParser.scoreInputZeroToOneHundredCheckValid(scoreInput);

        scoreParser.scoreDuplicatedCheckValidv2(subjectIdInput, studentIdInput, roundInput);

        return findSubjectData;
    }
}
