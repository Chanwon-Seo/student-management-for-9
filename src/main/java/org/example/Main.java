package org.example;


import org.example.domain.Student;
import org.example.domain.enums.StudentStateType;
import org.example.service.StudentService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        DBStorage dbStorage = new DBStorage();
        List<Student> studentList = dbStorage.getStudentList();
        Set<Long> subjectIds = new HashSet<>();
        int num = 0;
        subjectIds.add(11L);
        subjectIds.add(131L);
        subjectIds.add(141L);
        subjectIds.add(112L);
        studentList.add(new Student(num++, "19990921", subjectIds, StudentStateType.GREEN.getValue(), "강철수"));
        studentList.add(new Student(num++, "20000203", subjectIds, StudentStateType.RED.getValue(), "박은미"));
        studentList.add(new Student(num++, "19891211", subjectIds, StudentStateType.YELLOW.getValue(), "김영수"));
        studentList.add(new Student(num++, "20001212", subjectIds, StudentStateType.GREEN.getValue(), "구자준"));
        studentList.add(new Student(num++, "19930521", subjectIds, StudentStateType.GREEN.getValue(), "김근영"));

        StudentService ss = new StudentService();
        ss.getStudentList();
        Student student = ss.StudentFindById(4);
        ss.getStudentDetail(student);
    }
}