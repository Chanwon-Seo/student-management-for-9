package org.example;

import org.example.service.StudentService;
import org.example.domain.Score;
import org.example.domain.Student;
import org.example.domain.Subject;

public class Main {

    public static Integer uNumber = 2024000;

    public static void main(String[] args) {

        StudentService st = new StudentService();
        while(true) {
            st.displayStudentView();
        }
    }
}