package org.example.parser;

import org.example.db.DBManager;
import org.example.domain.Student;

import java.util.Optional;


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
    public Optional<Student> studentEmptyCheckValidV2(Integer studentIdInput) {
        Optional<Student> findStudentData = dbManager.findOneByStudent(studentIdInput);

        if (findStudentData.isPresent()) {
            return findStudentData;
        }

        throw new NullPointerException("조회된 수강생 정보가 없습니다.\n");
    }

    public Student studentFindByIdEmptyCheckValid(Integer studentIdInput) {
        Student student = dbManager.studentFindById(studentIdInput);
        if (student == null) throw new RuntimeException("조회된 수강생 정보가 없습니다.\n");
        return student;
    }

    public void studentAndSubjectCheckValid(Integer findSubjectData, Student findStudentData) {
        if (!findStudentData.getSubjectId().contains(findSubjectData)) {
            throw new NullPointerException("해당 학생은 수강하지 않은 과목입니다.\n");
        }
    }
}
