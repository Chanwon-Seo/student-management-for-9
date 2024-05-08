package org.example.db;

import lombok.Getter;
import org.example.domain.Score;
import org.example.domain.Student;
import org.example.domain.Subject;
import org.example.domain.enums.StudentStateType;
import org.example.domain.enums.SubjectType;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;


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
                    SubjectType.REQUIRED

            ),
            new Subject(
                    ++subjectIdNum,
                    "객체지향",
                    SubjectType.REQUIRED
            ),
            new Subject(
                    ++subjectIdNum,
                    "Spring",
                    SubjectType.REQUIRED
            ),
            new Subject(
                    ++subjectIdNum,
                    "JPA",
                    SubjectType.REQUIRED
            ),
            new Subject(
                    ++subjectIdNum,
                    "MySQL",
                    SubjectType.REQUIRED
            ),
            new Subject(
                    ++subjectIdNum,
                    "디자인 패턴",
                    SubjectType.ELECTIVE
            ),
            new Subject(
                    ++subjectIdNum,
                    "Spring Security",
                    SubjectType.ELECTIVE
            ),
            new Subject(
                    ++subjectIdNum,
                    "Redis",
                    SubjectType.ELECTIVE
            ),
            new Subject(
                    ++subjectIdNum,
                    "MongoDB",
                    SubjectType.ELECTIVE
            )
    );

    public void setStudentIdNum(Integer studentIdNum){
        this.studentIdNum = studentIdNum;
    }
}
