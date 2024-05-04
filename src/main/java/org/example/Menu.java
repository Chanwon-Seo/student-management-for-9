package org.example;


import org.example.domain.Student;
import org.example.service.StudentService;

import java.util.HashSet;
import java.util.List;

import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Student[] students = new Student[100]; // 100명정도 배열생성
        int studentCount = 0;

        StudentService studentService = new StudentService();
        DBStorage dbStorage = new DBStorage();
        List<org.example.domain.Student> studentList = dbStorage.getStudentList();
        int num = 0;

        studentList.add(new Student(num++, "kimchi", "19990909", new HashSet<>()));
        studentList.add(new Student(num++, "dubu", "19990909", new HashSet<>()));
        studentList.add(new Student(num++, "egg", "19990909", new HashSet<>()));

        while (true) {
            System.out.println("1. 수강생 관리");
            System.out.println("2. 수강생 점수관리");
            System.out.println("3. 프로그램 종료(exit)");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 제거

            switch (choice) {
                case 1: // 수강생 관리 이동

                    studentService.displayStudentView();
//                    manageStudents(students, studentCount, scanner);
                    break;
                case 2: // 수강생 점수관리 이동
//                    manageScores(students, studentCount, scanner);

                    break;
                case 3: // 끝
                    System.out.println("프로그램 종료...");
                    return;
                default:
                    System.out.println("다시 입력바랍니다.");
            }
        }
    }



    static class Student {
        