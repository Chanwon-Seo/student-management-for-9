package org.example.parser;

import org.example.db.DBManager;
import org.example.domain.Student;
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

    /**
     * @세미 수강 중인 과목인지 조회
     * throw 해당 과목을 수강하지 않은 수강생일 경우
     */
    public void HavingsubjectCheck(Integer studentIdInput, Integer subjectIdInput) {
        Student student = dbManager.findOneByStudent(studentIdInput);
        for (Integer sub : student.getSubjectId()) {
            if (sub.equals(subjectIdInput)) {
                return;
            }
        }
        //throw new RuntimeException("수강하는 과목이 아닙니다.");
        System.out.println("수강하는 과목이 아닙니다.");
    }


    /**
     * @return
     * @성균 과목 이름 조회
     * throw 조회된 과목이 없을 경우
     * //
     */
    public boolean subjectIsEmptyCheck(Integer subjectId) throws Exception {
        for (Subject subject : dbManager.findBySubjects()) {
            if (subjectId == subject.getSubjectId()) {
                return true;
            }
        }
        throw new Exception("올바른 과목 ID가 아닙니다.");
    }

}
