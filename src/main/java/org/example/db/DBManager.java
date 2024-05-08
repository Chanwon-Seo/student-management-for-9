package org.example.db;


import org.example.domain.Score;
import org.example.domain.Student;
import org.example.domain.Subject;
import org.example.domain.enums.StudentStateType;
import org.example.domain.enums.SubjectType;

import java.util.List;
import java.util.Optional;

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

    public Optional<Student> findOneByStudent(Integer studentId) {
        Optional<Student> studentOptional = Optional.empty();
        for (Student student : dbStorage.getStudentList()) {
            if (studentId.equals(student.getStudentId())) {
                studentOptional = Optional.of(student);
            }
        }
        return studentOptional;
    }

    public Student studentFindById(int id) throws NullPointerException {
        for (Student student : findByStudents()) {
            if (student.getStudentId() == id) {
                return student;
            }
        }
        throw new NullPointerException("일치하는 수강생이 없습니다.");
    }

    public Integer findByStudentIdNum() {
        return dbStorage.getStudentIdNum();
    }

    public List<Subject> findBySubjects() {
        return dbStorage.getSubjectList();
    }

    public List<Student> findByStudents() {
        return dbStorage.getStudentList();
    }

    public void updateStudentIdNum(Integer studentIdNum) {
        dbStorage.setStudentIdNum(++studentIdNum);
    }

    /*
     * 차도범
     * 수정 메서드
     * */
    public void editStudent(Student student, String name, String birthDay, StudentStateType studentStateType) {
        student.editStudent(name, birthDay, studentStateType);
    }

    /*
     * @차도범
     * 수강생 아이디로 삭제
     * */

    public boolean deleteStudentById(int studentId) {
        List<Student> studentList = dbStorage.getStudentList();
        return studentList.removeIf(student -> studentId == student.getStudentId());
    }

    /*
     * @차도범
     * 수강생 아이디로 삭제
     * */
    public boolean deleteScoreByStudentId(int studentId) {
        List<Score> scoreList = dbStorage.getScoreList();
        return scoreList.removeIf(score -> score.getStudentId() == studentId);
    }


    /*
     * @세미
     * subject id로 필수인지 아닌지 판별
     * */
    public boolean FindSubjectTypebySubjectId(int subjectId) {
        List<Subject> subjectList = dbStorage.getSubjectList();
        Subject sub = subjectList.get(subjectId-1);
        if(sub.getSubjectType()==SubjectType.REQUIRED) return true;
        else return false;
    }
}
