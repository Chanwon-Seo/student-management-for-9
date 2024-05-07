package org.example.db;


import org.example.domain.Score;
import org.example.domain.Student;
import org.example.domain.Subject;

import java.util.List;

/**
 * DBStorage의 데이터를 CRUD하기 위한 클래스
 */
public class DBManager {
    private final DBStorage dbStorage;

    public DBManager(DBStorage dbStorage) {
        this.dbStorage = dbStorage;
    }

    /**
     * Save
     */
    public void saveScore(Score score) {
        dbStorage.getScoreList().add(score);
    }

    public void saveStudent(Student student) {
        dbStorage.getStudentList().add(student);
    }

    public void saveSubject(Subject subject) {
        dbStorage.getSubjectList().add(subject);
    }

    /**
     * 다건 조회
     */
    public List<Score> findByScores() {
        return dbStorage.getScoreList();
    }

    /**
     * 단건 조회
     */
    public Subject findOneBySubject(Integer subjectId) {
        for (Subject subject : dbStorage.getSubjectList()) {
            if (subjectId.equals(subject.getSubjectId())) {
                return subject;
            }
        }
        throw new RuntimeException("등록된 과목이 아닙니다.\n");
    }

    public Student findOneByStudent(Integer studentId) {
        for (Student student : dbStorage.getStudentList()) {
            if (studentId.equals(student.getStudentId())) {
                return student;
            }
        }
        throw new RuntimeException("조회된 수강생 정보가 없습니다.\n");
    }


}
