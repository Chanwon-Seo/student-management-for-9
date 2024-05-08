
// "메인메뉴>수강생 관리"

package org.example;

import org.example.db.DBManager;
import org.example.domain.Student;
import org.example.domain.enums.StudentStateType;
import org.example.service.ScoreService;
import org.example.service.StudentService;
import org.example.service.SubjectService;

import java.util.Set;

import static org.example.Menu.sc;

public class StudentMenu {

    private final DBManager dbManager;
    private final StudentService studentService;
    private final ScoreService scoreService;
    private final SubjectService subjectService;

    public StudentMenu(DBManager dbManager) {
        this.dbManager = dbManager;
        this.studentService = new StudentService(dbManager);
        this.scoreService = new ScoreService(dbManager);
        this.subjectService = new SubjectService(dbManager);
    }


    boolean next = true;

    public void displayStudentView() {
        int studentId;
        StudentStateType studentStateType;
        StudentService studentService = new StudentService(dbManager);

        testData();
        while (true) {
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 조회");
            System.out.println("0. 이전메뉴로 이동");
            System.out.printf("%n");


            if (sc.hasNextLine()) {
                loopA:
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


                            while (next) {
                                studentService.getStudentList();
                                System.out.println();
                                System.out.println();
                                System.out.println("1. 수강생 조회");
                                System.out.println("2. 수강생 정보 수정");
                                System.out.println("3. 상태별 수강생 목록");
                                System.out.println("4. 수강생 삭제");
                                System.out.println("0. 뒤로가기");
                                switch (Integer.parseInt(sc.nextLine())) {
                                    case 1 -> {
                                        studentId = inputStudentId("조회할 수강생 아이디 입력");
                                        try {
                                            studentService.getStudentDetail(studentId);
                                        } catch (Exception e) {
                                            System.out.println(e.getMessage());
                                        }
                                        break;
                                    }
                                    case 2 -> {
                                        studentId = inputStudentId("수정할 수강생의 아이디 입력");
                                        Student findStudent = null;
                                        try {
                                            findStudent = studentService.studentFindById(studentId);
                                        } catch (NullPointerException e) {
                                            System.out.println(e.getMessage());
                                        }
                                        if (findStudent != null) {
                                            System.out.println("수정할 정보를 입력(1.이름, 2.생일, 3.상태)>");
                                            switch (Integer.parseInt(sc.nextLine())) {
                                                case 1 -> {
                                                    System.out.println("수정할 이름>");
                                                    String editName = sc.nextLine();
                                                    studentService.editStudent(
                                                            findStudent,
                                                            editName,
                                                            findStudent.getBirthDay(),
                                                            findStudent.getStudentStateType());
                                                }
                                                case 2 -> {
                                                    System.out.println("수정할 생일>");
                                                    String editBrithDay = sc.nextLine();
                                                    studentService.editStudent(
                                                            findStudent,
                                                            findStudent.getStudentName(),
                                                            editBrithDay,
                                                            findStudent.getStudentStateType());
                                                }
                                                case 3 -> {
                                                    //사용자가 입력받은 사용자의 상태
                                                    studentStateType = getStudentStateType();
                                                    //만약 값이 일치한느 것이 없으면 기존 사용자의 값을 가져온다.
                                                    if (studentStateType == null) {
                                                        studentStateType = findStudent.getStudentStateType();
                                                        System.out.println("불일치 !! 기존값으로 ");
                                                        System.out.println();
                                                    }
                                                    studentService.editStudent(
                                                            findStudent,
                                                            findStudent.getStudentName(),
                                                            findStudent.getBirthDay(),
                                                            studentStateType);
                                                }
                                                default -> System.out.println("불일치!!!");
                                            }
                                        } else System.out.println();
                                    }
                                    case 3 -> {
                                        studentStateType = getStudentStateType();
                                        studentService.studentListByStatus(studentStateType);
                                    }
                                    case 4 -> {
                                        studentId = inputStudentId("삭제할 수각생 아이디 입력>");
                                        studentService.deleteStudentById(studentId);
                                        scoreService.deleteScoreByStudentId(studentId);
                                    }
                                    case 0 -> {
                                        break loopA;
                                    }
                                }
                            }

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

    /*
     * @차도범
     * 입력받은 아이디 값을 반환
     * */
    private static int inputStudentId(String 아이디_입력) {
        System.out.println(아이디_입력);
        return Integer.parseInt(sc.nextLine());
    }

    /*
     * @차도범
     * 일력박은 수강생 샅애 String -> StudentStateType enum으로 변경해서 반환
     * */
    private static StudentStateType getStudentStateType() {
        System.out.println("수정할 상태(1.green, 2.red, 3.yellow)>");
        return switch (sc.nextLine()) {
            case "1" -> StudentStateType.GREEN;
            case "2" -> StudentStateType.RED;
            case "3" -> StudentStateType.YELLOW;
            default -> null;
        };
    }


    //초기값
    public void testData() {
        Set<Integer> set = Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        dbManager.findByStudents().add(new Student(1, "서찬원", "990204", set, StudentStateType.GREEN));
        dbManager.findByStudents().add(new Student(2, "박세미", "990204", set, StudentStateType.RED));
        dbManager.findByStudents().add(new Student(3, "박상균", "990204", set, StudentStateType.RED));
        dbManager.findByStudents().add(new Student(4, "차도범", "990204", set, StudentStateType.YELLOW));
        dbManager.findByStudents().add(new Student(5, "이근수", "990204", set, StudentStateType.GREEN));
    }
}