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
     * CUSTOM method
     */


    public Student studentFindById(int id) {
        for (Student student : findByStudents()) {
            if (student.getStudentId() == id) {
                return student;
            }
        }
        return null;
    }

    public Integer findByStudentIdNum() {
        return dbStorage.getStudentIdNum();
    }

    public List<Subject> findBySubjects() {
        return dbStorage.getSubjectList();
    }

    /**
     * 다건 조회
     */
    public List<Score> findByScores() {
        return dbStorage.getScoreList();
    }

    public List<Student> findByStudents() {
        return dbStorage.getStudentList();
    }

    public void addStudentList(Student st) {
        dbStorage.getStudentList().add(st);
    }

    public void saveScoreList(Score score) {
        dbStorage.getScoreList().add(score);
    }


    public void updateStudentIdNum(Integer studentIdNum) {
        dbStorage.setStudentIdNum(++studentIdNum);
    }
}