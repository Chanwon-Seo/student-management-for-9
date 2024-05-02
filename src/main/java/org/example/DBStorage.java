package org.example;

import org.example.domain.Score;
import org.example.domain.Student;
import org.example.domain.Subject;

import java.util.ArrayList;
import java.util.List;

public class DBStorage {
    private static List<Student> studentList = new ArrayList<>();
    private static List<Score> scoreList;
    private static List<Subject> subjectList;

    public List<Student> getStudentList() {
        return studentList;
    }
}
