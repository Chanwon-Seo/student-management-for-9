package org.example.parser;

import org.example.domain.Subject;

public class SubjectParser {
    /**
     * @return
     * @찬원 과목 정보 조회
     * throw 조회된 수강생 정보가 없을 경우
     */
    public Subject subjectEmptyCheckValid(Integer subjectIdInput) {
        Subject findSubjectData = null;
        for (Subject subject : DBStorage.getSubjectList()) {
            if (subjectIdInput.equals(subject.getSubjectId())) {
                findSubjectData = subject;
            }
        }

        if (findSubjectData != null) {
            return findSubjectData;
        }

        throw new RuntimeException("등록된 과목이 아닙니다.");
    }

    /**
     * @return
     * @성균 과목 이름 조회
     * throw 조회된 과목이 없을 경우
//     */
    public boolean subjectIsEmptyCheck(Integer subjectId) throws Exception {
        for (Subject subject : DBStorage.getSubjectList()) {
            if (subjectId == subject.getSubjectId()) {
                return true;
            }
        }
        throw new Exception("올바른 과목 ID가 아닙니다.");
    }

}
