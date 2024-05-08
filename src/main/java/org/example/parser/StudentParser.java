package org.example.parser;

import org.example.db.DBManager;
import org.example.domain.Student;
import org.example.domain.Subject;


public class StudentParser {
    private final DBManager dbManager;

    public StudentParser(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * @return
     * @찬원 수강생 정보 조회
     * throw 조회된 수강생 정보가 없을 경우
     */
    public void studentEmptyCheckValid(Subject findSubjectData, Integer studentIdInput) {
        try {
            Student findStudentData = dbManager.findOneByStudent(studentIdInput);
            if (findStudentData.getSubjectId().contains(findSubjectData.getSubjectId())) {

                return;
            }
            throw new RuntimeException("수강한 과목 목록에 없습니다.\n");
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Student studentFindByIdEmptyCheckValid(Integer studentIdInput) throws NullPointerException {
        return dbManager.studentFindById(studentIdInput);
    }
}
