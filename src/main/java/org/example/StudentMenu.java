
// "메인메뉴>수강생 관리"

package org.example;

import org.example.db.DBManager;
import org.example.domain.Student;
import org.example.service.StudentService;

import java.util.HashSet;
import java.util.Set;

import static org.example.Menu.sc;

public class StudentMenu {

    private final DBManager dbManager;

    public StudentMenu(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public void displayStudentView() {

        StudentService studentService = new StudentService(dbManager);

        while (true) {
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 조회");
            System.out.println("3. 이전메뉴로 이동");
            System.out.printf("%n");


            if (sc.hasNextLine()) {
                try {
                    int i = Integer.parseInt(sc.nextLine());

                    switch (i) {
                        case 1:  //연결되었습니다.
                            System.out.println("***** 수강생 등록*****");
                            System.out.println("메인메뉴> 수강생 관리>...");

                            studentService.createStudent();

                            break;
                        case 2: //연결되었습니다.
                            System.out.println("***** 수강생 조회*****");
                            System.out.println("메인메뉴> 수강생 관리>...");

                            studentService.getStudentList();
                            System.out.println("1. 수강생 조회");
                            System.out.println("2. 뒤로가기");
                            switch (sc.nextLine()) {
                                case "1" -> {
                                    System.out.println("조회할 수강생 아이디 입력");
                                    int studentId = Integer.parseInt(sc.nextLine());
                                    try {
                                        studentService.getStudentDetail(studentId);
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                case "2" -> {
                                    return;
                                }
                            }

                            break;

                        case 3:
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
}