
// "메인메뉴>수강생 관리"

package org.example;

import org.example.db.DBManager;
import org.example.domain.Student;
import org.example.domain.enums.StudentStateType;
import org.example.service.StudentService;

import static org.example.Menu.sc;

public class StudentMenu {

    private final DBManager dbManager;

    public StudentMenu(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    boolean next = true;

    public void displayStudentView() {
        int studentId;
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
                            System.out.println();
                            System.out.println();
                            while (next) {
                                System.out.println("1. 수강생 조회");
                                System.out.println("2. 수강생 정보 수정");
                                System.out.println("3. 상태별 수강생 목록");
                                System.out.println("4. 수강생 삭제");
                                System.out.println("0. 뒤로가기");
                                System.out.println(dbManager.findByStudents().size());
                                switch (sc.nextLine()) {
                                    case "1" -> {
                                        System.out.println("조회할 수강생 아이디 입력");
                                        studentId = Integer.parseInt(sc.nextLine());
                                        try {
                                            studentService.getStudentDetail(studentId);
                                        } catch (Exception e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                    case "2" -> {
                                        System.out.println("수정할 수강생의 아이디 입력");
                                        studentId = Integer.parseInt(sc.nextLine());
                                        Student findStudent = dbManager.studentFindById(studentId);
                                        if (findStudent != null) {
                                            System.out.println("수정할 정보를 입력(1.이름, 2.생일, 3.상태)>");
                                            switch (sc.nextLine()) {
                                                case "1" -> {
                                                    System.out.println("수정할 이름>");
                                                    String editName = sc.nextLine();
                                                    dbManager.editStudent(
                                                            findStudent,
                                                            editName,
                                                            findStudent.getBirthDay(),
                                                            findStudent.getStudentStateType());
                                                }
                                                case "2" -> {
                                                    System.out.println("수정할 생일>");
                                                    String editBrithDay = sc.nextLine();
                                                    dbManager.editStudent(
                                                            findStudent,
                                                            findStudent.getStudentName(),
                                                            editBrithDay,
                                                            findStudent.getStudentStateType());
                                                }
                                                case "3" -> {
                                                    System.out.println("수정할 상태(1.green, 2.red, 3.yellow)>");
                                                    StudentStateType studentStateType = switch (sc.nextLine()) {
                                                        case "1" -> StudentStateType.GREEN;
                                                        case "2" -> StudentStateType.RED;
                                                        case "3" -> StudentStateType.YELLOW;
                                                        default -> findStudent.getStudentStateType();
                                                    };
                                                    dbManager.editStudent(
                                                            findStudent,
                                                            findStudent.getStudentName(),
                                                            findStudent.getBirthDay(),
                                                            studentStateType);
                                                }
                                            }
                                        } else System.out.println("없는 수강생!!");


                                    }
                                    case "3" -> {

                                    }
                                    case "4" -> {

                                    }
                                    case "0" -> {
                                        next = false;
                                    }
                                }
                            }

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
            } else {
                System.out.println("입력이 없습니다, 다시입력바랍니다.");
            }
        }
    }
}