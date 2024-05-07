package org.example.parser;

import org.example.db.DBManager;
import org.example.domain.Subject;

public class SubjectParser {
    private final DBManager dbManager;

    public SubjectParser(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * @찬원 과목 정보 조회
     * throw 조회된 수강생 정보가 없을 경우
     */
    public Subject subjectEmptyCheckValid(Integer subjectIdInput) {
        try {
            return dbManager.findOneBySubject(subjectIdInput);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
