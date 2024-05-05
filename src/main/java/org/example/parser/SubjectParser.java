package org.example.parser;

import org.example.DBStorage;
import org.example.domain.Subject;

import java.util.List;

public class SubjectParser {
    /**
     * @찬원
     * 과목 정보 조회
     * throw 조회된 수강생 정보가 없을 경우
     */
    public void subjectEmptyCheckValid(Integer subjectIdInput) {
        for (Subject subject : DBStorage.getSubjectList()) {
            if (subjectIdInput.equals(subject.getSubjectId())) {
                return;
            }
        }
        throw new RuntimeException("등록된 과목이 아닙니다.");
    }
}
