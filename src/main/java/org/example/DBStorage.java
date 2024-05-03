package org.example;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.Score;
import org.example.domain.Student;
import org.example.domain.Subject;

import java.util.LinkedList;
import java.util.List;


// TODO: Setter custom
public class DBStorage {
//    @Getter
    private static final List<Student> studentList = new LinkedList<>();
    @Getter
    private static final List<Score> scoreList = new LinkedList<>();

    //TODO subjectType = 열거형?
    private static final List<Subject> subjectList = List.of(
            new Subject(
                    1,
                    "Java",
                    "SUBJECT_TYPE_MANDATORY"
            ),
            new Subject(
                    2,
                    "객체지향",
                    "SUBJECT_TYPE_MANDATORY"
            ),
            new Subject(
                    3,
                    "Spring",
                    "SUBJECT_TYPE_MANDATORY"
            ),
            new Subject(
                    4,
                    "JPA",
                    "SUBJECT_TYPE_MANDATORY"
            ),
            new Subject(
                    5,
                    "MySQL",
                    "SUBJECT_TYPE_MANDATORY"
            ),
            new Subject(
                    6,
                    "디자인 패턴",
                    "SUBJECT_TYPE_CHOICE"
            ),
            new Subject(
                    7,
                    "Spring Security",
                    "SUBJECT_TYPE_CHOICE"
            ),
            new Subject(
                    8,
                    "Redis",
                    "SUBJECT_TYPE_CHOICE"
            ),
            new Subject(
                    9,
                    "MongoDB",
                    "SUBJECT_TYPE_CHOICE"
            )
    );

    public void saveScoreList(Score score) {
        // null 예외처리
        scoreList.add(score);
    }

    public static void addStudentList(Student st) {
        studentList.add(st);
    }
}
