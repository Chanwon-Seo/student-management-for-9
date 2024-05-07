package org.example.parser;

import org.example.db.DBManager;
import org.example.domain.Student;
import org.example.db.DBManager;
import org.example.db.DBStorage;

public class StudentParser {

    private final DBManager dbManager;

    public StudentParser(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * @찬원 수강생 정보 조회
     * throw 조회된 수강생 정보가 없을 경우
     */
    public void studentEmptyCheckValid(Integer studentIdInput) {
        for (Student student : dbManager.findByStudents()) {
            if (studentIdInput.equals(student.getStudentId())) {
                return;
            }
        }
        throw new RuntimeException("조회된 수강생 정보가 없습니다.");
    }

    public Student studentFindByIdEmptyCheckValid(Integer studentIdInput) {
        Student student = dbManager.studentFindById(studentIdInput);
        if (student == null) throw new RuntimeException("조회된 수강생 정보가 없습니다.");
        return student;
    }
}
