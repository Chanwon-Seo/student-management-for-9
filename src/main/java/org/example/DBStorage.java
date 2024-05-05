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
    @Getter
    @Setter
    private static Integer studentIdNum = 2024000;
    private static Integer subjectIdNum = 0;
    @Getter
    private static final List<Student> studentList = new LinkedList<>();
    @Getter
    private static final List<Score> scoreList = new LinkedList<>();

    //TODO subjectType = 열거형?
    @Getter
    private static final List<Subject> subjectList = List.of(
            new Subject(
                    ++subjectIdNum,
                    "Java",
                    "SUBJECT_TYPE_MANDATORY"
            ),
            new Subject(
                    ++subjectIdNum,
                    "객체지향",
                    "SUBJECT_TYPE_MANDATORY"
            ),
            new Subject(
                    ++subjectIdNum,
                    "Spring",
                    "SUBJECT_TYPE_MANDATORY"
            ),
            new Subject(
                    ++subjectIdNum,
                    "JPA",
                    "SUBJECT_TYPE_MANDATORY"
            ),
            new Subject(
                    ++subjectIdNum,
                    "MySQL",
                    "SUBJECT_TYPE_MANDATORY"
            ),
            new Subject(
                    ++subjectIdNum,
                    "디자인 패턴",
                    "SUBJECT_TYPE_CHOICE"
            ),
            new Subject(
                    ++subjectIdNum,
                    "Spring Security",
                    "SUBJECT_TYPE_CHOICE"
            ),
            new Subject(
                    ++subjectIdNum,
                    "Redis",
                    "SUBJECT_TYPE_CHOICE"
            ),
            new Subject(
                    ++subjectIdNum,
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
