
// "메인메뉴>수강생 관리"

package org.example;

import org.example.db.DBManager;
import org.example.domain.enums.StudentStateType;
import org.example.parser.Parser;
import org.example.domain.Student;
import org.example.service.StudentService;

import java.util.*;

import static org.example.Menu.sc;

public class StudentMenu {

    private final DBManager dbManager;


    public StudentMenu(DBManager dbManager) {
        this.dbManager = dbManager;
    }


    public void displayStudentView() {

        StudentService studentService = new StudentService(dbManager);
        List<Student> studentList = dbManager.findByStudents();

        while (true) {
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 조회");
            System.out.println("0. 이전메뉴로 이동");
            System.out.printf("%n");


            if (sc.hasNextLine()) {
                try {
                    int i = Integer.parseInt(sc.nextLine());
                    dbManager.saveStudent(new Student(1, "서찬원", "111111", new HashSet<>(), StudentStateType.GREEN));
                    switch (i) {
                        case 1:  //연결되었습니다.
                            System.out.println("***** 수강생 등록 *****");
                            System.out.println("메인메뉴> 수강생 관리> 수강생 등록>...");

                            studentService.createStudent();

                            break;
                        case 2: //연결되었습니다.
                            while (true) {
                                new Parser(dbManager);
                                // new StudentService().getStudentDetail();
                                studentService.getStudentList();
                                System.out.println("조회할 수강생 아이디 입력");
                                int studentId = Integer.parseInt(sc.nextLine());
                                studentService.getStudentDetail(studentId);

                                System.out.println("***** 수강생 조회 *****");
                                System.out.println("메인메뉴> 수강생 관리> 수강생 조회>...");
                                System.out.println("1. 수강생 상세 정보 조회");
                                System.out.println("2. 수강생 정보 수정");
                                System.out.println("3. 상태별 수강생 목록 조회");
                                System.out.println("4. 수강생 삭제");
                                System.out.println("0. 이전메뉴로 이동");
                                System.out.printf("%n");

                                switch (Integer.parseInt(sc.nextLine())) {
                                    case 1:
                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        break;
                                    case 4:
                                        displayStudentStateList(dbManager);
                                        break;
                                    case 5:
                                        break;

                                    case 0:
                                        System.out.println("123");
                                        break;
                                }

                            }
//                            break;
                        case 0:
                            System.out.println("이전 화면으로 돌아갑니다.");
                            System.out.println("메인메뉴> 이전메뉴로 이동>...");

                            return;


                        default:
                            System.out.println("다시입력바랍니다.");
                    }
                } catch (NumberFormatException e) {
                    //ystem.out.println("숫자외 문자를 입력하였습니다, 다시 입력바랍니다.");
                    System.out.println("something wrong~, 다시 입력바랍니다.");
                }
            } else
                System.out.println("입력이 없습니다, 다시입력바랍니다.");
        }
    }

    private static void displayStudentStateList(DBManager dbManager) {
        System.out.println("***** 상태별 수강생 목록 조회 *****");
        System.out.println("메인메뉴> 수강생 관리> 수강생 조회> 상태별 수강생 목록 조회>...");



//        dbManager.saveStudent(new Student(2, "서찬투", "222222", new HashSet<>(), StudentStateType.RED));
//        dbManager.saveStudent(new Student(3, "서찬쓰리", "333333", new HashSet<>(), StudentStateType.GREEN));
//        dbManager.saveStudent(new Student(4, "서찬포", "444444", new HashSet<>(), StudentStateType.YELLOW));

        List<Student> findAllStudentStateList = null;

        try {
            findAllStudentStateList = new Parser(dbManager).findAllStudentStateList();
            //상태별 수강생 고유번호 정렬
            Comparator<Student> comparing = Comparator.comparing(Student::getStudentStateType);
            findAllStudentStateList.sort(comparing);
            System.out.println(findAllStudentStateList);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }


    }

}