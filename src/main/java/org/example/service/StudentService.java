package org.example.service;


import org.example.DBStorage;
import org.example.domain.Student;

import java.util.List;

public class StudentService {

    DBStorage db = new DBStorage();
    List<Student> studentList = DBStorage.getStudentList();

    //학생 리스트
    public void getStudentList() {
        System.out.println("id / name");
        for (Student student : studentList) {
            System.out.println(student.getStudentIdInteger() + " : " + student.getStudentName());
        }
    }

    //학생 상세
    public void getStudentDetail(Student student) {
        System.out.println("----학생 상세-----");
        System.out.println("id : " + student.getStudentIdInteger());
        System.out.println("이름 : " + student.getStudentName());
        System.out.println("생년월일 : " + student.getBirthDay());
        System.out.println("상태 : " + student.getStudentState());
        for (Long subjectId : student.getSubjectId()) {
            System.out.println("과목 : " + subjectId);
        }
    }

    //학생 아이디로 검색
    public Student StudentFindById(Integer studentId) {
        for (Student student : studentList) {
            if (student.getStudentIdInteger() == studentId) {
                return student;
            }
        }
        return null;
    }
}