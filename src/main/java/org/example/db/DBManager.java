package org.example.db;


import org.example.domain.Score;
import org.example.domain.Student;
import org.example.domain.Subject;
import org.example.domain.enums.StudentStateType;
import org.example.domain.enums.SubjectType;
import org.example.service.ScoreService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * DBStorage의 데이터를 CRUD하기 위한 클래스
 */
public class DBManager {
    private final DBStorage dbStorage;

    public DBManager(DBStorage dbStorage) {
        this.dbStorage = dbStorage;
    }

    /**
     * Save
     */
    public void saveScore(Score score) {
        dbStorage.getScoreList().add(score);
    }

    public void saveStudent(Student student) {
        dbStorage.getStudentList().add(student);
    }

    public void saveSubject(Subject subject) {
        dbStorage.getSubjectList().add(subject);
    }

    /**
     * 다건 조회
     */
    public List<Score> findByScores() {
        return dbStorage.getScoreList();
    }

    public List<Subject> findBySubjects() {
        return dbStorage.getSubjectList();
    }

    public List<Student> findByStudents() {
        return dbStorage.getStudentList();
    }

    /**
     * 단건 조회
     */
    public Optional<Subject> findOneBySubject(Integer subjectId) {
        Optional<Subject> subjectOptional = Optional.empty();
        for (Subject subject : dbStorage.getSubjectList()) {
            if (subjectId.equals(subject.getSubjectId())) {
                subjectOptional = Optional.of(subject);
            }
        }
        return subjectOptional;
    }

    /**
     * @찬원
     * 특정 학생 고유번호를 통해 수강생인지 판별
     * 수강생이 아닌 경우 = Optional<Student> NULL
     * 수강생인 경우 = Optional<Student> NOT NUll
     */
    public Optional<Student> findOneByStudent(Integer studentId) {
        Optional<Student> studentOptional = Optional.empty();
        for (Student student : dbStorage.getStudentList()) {
            if (studentId.equals(student.getStudentId())) {
                studentOptional = Optional.of(student);
            }
        }
        return studentOptional;
    }

    /**
     * @성균
     * 수강생 고유번호 가져오기
     */
    public Integer findByStudentIdNum() {
        return dbStorage.getStudentIdNum();
    }

    /**
     * @성균
     * 수강생이 추가된 후 수강생 고유번호 증가
     */
    public void updateStudentIdNum(Integer studentIdNum) {
        dbStorage.setStudentIdNum(++studentIdNum);
    }

    /**
     * @차도범 수정 메서드
     */
    public void editStudent(Student student, String name, String birthDay, StudentStateType studentStateType) {
        student.editStudent(name, birthDay, studentStateType);
    }

    /**
     * @차도범 수강생 아이디로 삭제
     */
    public boolean deleteStudentById(Integer studentId) {
        List<Student> studentList = dbStorage.getStudentList();
        return studentList.removeIf(student -> studentId == student.getStudentId());
    }

    /**
     * @차도범 수강생 아이디로 삭제
     */
    public boolean deleteScoreByStudentId(Integer studentId) {
        List<Score> scoreList = dbStorage.getScoreList();
        return scoreList.removeIf(score -> score.getStudentId() == studentId);
    }


    /**
     * @세미 subject id로 필수인지 아닌지 판별
     */
    public boolean findSubjectTypebySubjectId(Integer subjectId) {
        List<Subject> subjectList = dbStorage.getSubjectList();
        Subject sub = subjectList.get(subjectId - 1);
        if (sub.getSubjectType() == SubjectType.REQUIRED) return true;
        else return false;
    }

    /**
     * 테스트 데이터
     */
    public void initData() {
        //찬원
        Set<Integer> set = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        dbStorage.getStudentList().add(new Student(1, "서찬원", "990204", set, StudentStateType.GREEN));
        dbStorage.getStudentList().add(new Student(2, "박세미", "990204", set, StudentStateType.RED));
        dbStorage.getStudentList().add(new Student(3, "박성균", "990204", set, StudentStateType.RED));
        dbStorage.getStudentList().add(new Student(4, "차도범", "990204", set, StudentStateType.YELLOW));
        dbStorage.getStudentList().add(new Student(5, "이근수", "990204", set, StudentStateType.GREEN));


        ScoreService scoreService = new ScoreService(this);
        scoreService.scoreCreateV3(1,1,1,96);
        scoreService.scoreCreateV3(1,1,2,77);
        scoreService.scoreCreateV3(1,1,3,85);
        scoreService.scoreCreateV3(2,1,1,55);
        scoreService.scoreCreateV3(2,1,2,77);
        scoreService.scoreCreateV3(3,1,1,35);

        scoreService.scoreCreateV3(1,2,1,50);
        scoreService.scoreCreateV3(1,2,2,68);
        scoreService.scoreCreateV3(2,2,1,99);

        scoreService.scoreCreateV3(5,4,1,55);
        scoreService.scoreCreateV3(3,4,1,35);
        scoreService.scoreCreateV3(9,4,1,77);
        scoreService.scoreCreateV3(9,4,2,45);

        scoreService.scoreCreateV3(1,5,1,50);
        scoreService.scoreCreateV3(1,5,2,90);
        scoreService.scoreCreateV3(1,5,3,20);
        scoreService.scoreCreateV3(2,5,1,80);
    }
}
