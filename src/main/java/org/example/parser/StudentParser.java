package org.example.parser;

import org.example.DBStorage;
import org.example.domain.Student;

import java.util.List;

public class StudentParser {

    /**
     * 수강생 정보 조회
     * throw 조회된 수강생 정보가 없을 경우
     */
    public void studentEmptyCheckValid(DBStorage dbStorage, Integer studentIdInput) {
        List<Student> studentList = dbStorage.getStudentList();
        for (Student student : studentList) {
            if (studentIdInput.equals(student.getStudentId())) {
                return ;
            }
        }
        throw new RuntimeException("조회된 수강생 정보가 없습니다.");
    }
}
