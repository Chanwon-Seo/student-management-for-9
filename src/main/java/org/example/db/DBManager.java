package org.example.db;


import org.example.domain.Score;
import org.example.domain.Student;

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

    public void addStudentList(Student st) {
        dbStorage.getStudentList().add(st);
    }

    public void saveScoreList(Score score) {
        dbStorage.getScoreList().add(score);
    }

}
