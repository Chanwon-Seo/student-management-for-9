package org.example.db;


import org.example.domain.Score;
import org.example.domain.Student;
import org.example.domain.Subject;

import java.util.Collection;
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
    public List<Student> findByStudents() {
        return dbStorage.getStudentList();
    }

    public List<Subject> findBySubjects() {
        return dbStorage.getSubjectList();
    }

    public Student studentFindById(int id) {
        for (Student student : findByStudents()) {
            if (student.getStudentId() == id) {
                return student;
            }
        }
        return null;
    }

    public void addStudentList(Student st) {
        dbStorage.getStudentList().add(st);
    }

    public void saveScoreList(Score score) {
        dbStorage.getScoreList().add(score);
    }

    public List<Score> findByScores() {
        return dbStorage.getScoreList();
    }

}
