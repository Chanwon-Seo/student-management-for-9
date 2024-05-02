package org.example;

import lombok.Getter;
import org.example.domain.Score;
import org.example.domain.Student;
import org.example.domain.Subject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
public class DBStorage {
    private final List<Student> studentList;
    private final List<Score> scoreList;
    private final List<Subject> subjectList;

    public DBStorage() {
        studentList = new LinkedList<>();
        scoreList = new LinkedList<>();
        subjectList = new LinkedList<>();
    }

    public void saveScoreList(Score score) {
        // null 예외처리
        scoreList.add(score);
    }
}
