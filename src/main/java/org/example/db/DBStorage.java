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

    //초기값
    public void testData() {
        Set<Integer> set = Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        studentList.add(new Student(1, "서찬원", "990204", set, StudentStateType.GREEN));
        studentList.add(new Student(2, "박세미", "990204", set, StudentStateType.RED));
        studentList.add(new Student(3, "박상균", "990204", set, StudentStateType.RED));
        studentList.add(new Student(4, "차도범", "990204", set, StudentStateType.YELLOW));
        studentList.add(new Student(5, "이근수", "990204", set, StudentStateType.GREEN));
    }
}
