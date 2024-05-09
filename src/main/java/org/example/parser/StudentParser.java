package org.example.parser;

import org.example.db.DBManager;
import org.example.domain.Student;
import org.example.domain.enums.StudentStateType;

import java.util.Optional;


public class StudentParser {
    private final DBManager dbManager;

    public StudentParser(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
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

    /**
     * @찬원 수강생의 수강 과목 여부 확인
     * throw 수강하지 않은 과목인 경우
     */
    public void studentAndSubjectCheckValid(Integer findSubjectData, Student findStudentData) {
        for (Integer subjectId : findStudentData.getSubjectSet()) {
            if (findSubjectData.equals(subjectId)) {
                return;
            }
        }
        throw new NullPointerException("해당 학생은 수강하지 않은 과목입니다.\n");
    }

    public void studentTypeCheckValid(StudentStateType stateType) {
        if(stateType==null)
            throw new NullPointerException("잘못된 학생 상태 입니다.\n");

    }

    /**
     * @차도범 수강생 정보 수정 메소드
     */
    public void editStudentEmptyCheckValid(String name, String birthDay, StudentStateType studentStateType) {
        if (name == null || birthDay == null || studentStateType == null) {
            throw new NullPointerException("이름/생일 정보가 입력되지 않았습니다.");
        }
    }
}
