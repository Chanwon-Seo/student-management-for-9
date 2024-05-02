package org.example;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.Score;
import org.example.domain.Student;
import org.example.domain.Subject;

import java.util.List;

public class DBStorage {
    private static List<Student> studentList;

    @Getter @Setter
    private static List<Score> scoreList;
    private static List<Subject> subjectList;
}
