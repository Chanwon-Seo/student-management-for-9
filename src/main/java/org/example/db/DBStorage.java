package org.example.db;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.Score;
import org.example.domain.Student;
import org.example.domain.Subject;

import java.util.LinkedList;
import java.util.List;


// TODO: Setter custom
@Getter
public class DBStorage {
    private Integer studentIdNum = 2024000;
    private Integer subjectIdNum = 0;

    private final List<Student> studentList = new LinkedList<>();
    private final List<Score> scoreList = new LinkedList<>();

    //TODO subjectType = 열거형?
    private final List<Subject> subjectList = List.of(
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
}
