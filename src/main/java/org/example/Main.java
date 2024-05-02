package org.example;


import org.example.domain.Student;
import org.example.service.StudentService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        DBStorage dbStorage = new DBStorage();
        List<Student> studentList = dbStorage.getStudentList();
        Set<Long> subjectIds = new HashSet<>();
        subjectIds.add(11L);
        subjectIds.add(131L);
        subjectIds.add(141L);
        subjectIds.add(112L);
        studentList.add(new Student(1, "19990921", subjectIds, "dㅠㅠ", "김까칠") );
        studentList.add(new Student(1, "19990921", subjectIds, "dㅠㅠ", "김까칠") );
        studentList.add(new Student(1, "19990921", subjectIds, "dㅠㅠ", "김까칠") );
        studentList.add(new Student(1, "19990921", subjectIds, "dㅠㅠ", "김까칠") );
        studentList.add(new Student(1, "19990921", subjectIds, "dㅠㅠ", "김까칠") );

        StudentService ss = new StudentService();
        ss.getStudentList(dbStorage);
        ss.getStudentDetail(new Student(1, "19990921", subjectIds, "dㅠㅠ", "김까칠"));
    }
}