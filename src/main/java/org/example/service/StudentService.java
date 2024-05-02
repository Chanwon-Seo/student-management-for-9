package org.example.service;


import org.example.DBStorage;
import org.example.domain.Student;

import java.util.List;

public class StudentService {
    public void getStudentList(DBStorage dbStorage) {
        List<Student> studentList = dbStorage.getStudentList();
        for (Student student : studentList) {
            System.out.println("id / name");
            System.out.println(student.getStudentIdInteger() + " : " + student.getStudentName());
        }
    }

    public void getStudentDetail(Student student) {
        System.out.println("id : " + student.getStudentIdInteger());
        System.out.println("이름 : " + student.getStudentName());
        System.out.println("생년월일 : " + student.getBirthDay());
        System.out.println("상태 : " + student.getStudentState());
        /*
        * 추후 학생 아이디 일칳라는 과목 반목문
        * */
        for (Long subjectId : student.getSubjectId()) {
            System.out.println("과목 : " + subjectId);
        }
    }

}