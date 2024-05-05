package org.example;

import java.util.Scanner;

public class Menu {
    static DBStorage dbStorage = new DBStorage();
    static Scanner sc = new Scanner(System.in);

    public void startPage() {
//        int studentCount = 0;
//        StudentService studentService = new StudentService();
//        List<Student> studentList = DBStorage.getStudentList();
//        int num = 0;

//        studentList.add(new Student(num++, "kimchi", "19990909", new HashSet<>()));
//        studentList.add(new Student(num++, "dubu", "19990909", new HashSet<>()));
//        studentList.add(new Student(num++, "egg", "19990909", new HashSet<>()));

        while (true) {
            System.out.println("1. 수강생 관리");
            System.out.println("2. 수강생 점수관리");
            System.out.println("3. 프로그램 종료(exit)");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1: // 수강생 관리 이동
//                    new StudentMenu().displayStudentView();
//                    studentService.displayStudentView();
//                    manageStudents(students, studentCount, scanner);
                    break;
                case 2: // 수강생 점수관리 이동
                    new ScoreMenu().displayScoreView();
                    break;
                case 3: // 종료
                    System.out.println("종료합니다.");
                    return;
                default:
                    System.out.println("다시 입력바랍니다.");
            }
        }
    }

}

