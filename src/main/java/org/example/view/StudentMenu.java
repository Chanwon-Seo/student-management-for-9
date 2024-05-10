package org.example.view;

import org.example.db.DBManager;
import org.example.domain.Student;
import org.example.domain.enums.StudentStateType;
import org.example.service.ScoreService;
import org.example.service.StudentService;

import static org.example.view.Menu.sc;

public class StudentMenu {
    private final ScoreService scoreService;
    private final StudentService studentService;

    public StudentMenu(DBManager dbManager) {
        this.scoreService = new ScoreService(dbManager);
        this.studentService = new StudentService(dbManager);
    }

    public void displayStudentView() {
        int studentId;
        StudentStateType studentStateType;

        while (true) {
            System.out.println("***** 수강생 관리 *****");
            System.out.println("메인메뉴> 수강생 관리>...");
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 조회");
            System.out.println("0. 이전메뉴로 이동");
            System.out.printf("%n");


            if (sc.hasNextLine()) {
                loopA:
                try {
                    int i = Integer.parseInt(sc.nextLine());

                    switch (i) {
                        case 1 -> {
                            System.out.println("***** 수강생 등록*****");
                            System.out.println("메인메뉴> 수강생 관리>수강생 등록...");

                            studentService.createStudent();
                        }
                        case 2 -> {
                            System.out.println("***** 수강생 조회 *****");
                            System.out.println("메인메뉴> 수강생 관리>수강생 조회...");


                            while (true) {
                                studentService.getStudentList();
                                System.out.println();
                                System.out.println();
                                System.out.println("1. 수강생 조회");
                                System.out.println("2. 수강생 정보 수정");
                                System.out.println("3. 상태별 수강생 목록");
                                System.out.println("4. 수강생 삭제");
                                System.out.println("0. 뒤로가기");
                                try {
                                    switch (Integer.parseInt(sc.nextLine())) {
                                        case 1 -> {
                                            System.out.println("조회할 수강생 아이디 입력 : ");
                                            studentId = Integer.parseInt(sc.nextLine());
                                            try {
                                                studentService.getStudentDetail(studentId);
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                        }
                                        case 2 -> {
                                            System.out.println("수정할 수강생의 아이디 입력 : ");
                                            studentId = Integer.parseInt(sc.nextLine());
                                            Student findStudent = null;
                                            try {
                                                findStudent = studentService.studentFindById(studentId);
                                            } catch (NullPointerException e) {
                                                System.out.println(e.getMessage());
                                            }
                                            if (findStudent != null) {
                                                System.out.println("수정할 정보를 입력: [1]이름 [2]생년월일 [3]상태 ");
                                                switch (Integer.parseInt(sc.nextLine())) {
                                                    case 1 -> {
                                                        System.out.print("수정할 이름 : ");
                                                        String editName = sc.nextLine();
                                                        studentService.editStudent(
                                                                findStudent,
                                                                editName,
                                                                findStudent.getBirthDay(),
                                                                findStudent.getStudentStateType());
                                                    }
                                                    case 2 -> {
                                                        System.out.print("수정할 생년월일(6자리) : ");
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
                                                        //만약 값이 일치하는 것이 없으면 기존 사용자의 값을 가져온다.
                                                        if (studentStateType == null) {
                                                            studentStateType = findStudent.getStudentStateType();
                                                            System.out.println("#####불일치 합니다. 기존값으로 돌아갑니다.#####");
                                                            System.out.println();
                                                        }
                                                        studentService.editStudent(
                                                                findStudent,
                                                                findStudent.getStudentName(),
                                                                findStudent.getBirthDay(),
                                                                studentStateType);
                                                    }
                                                    default -> System.out.println("##### !!!불일치!!! #####");
                                                }
                                            } else System.out.println();
                                        }
                                        case 3 -> {
                                            System.out.println("***** 수강생 상세 관리 *****");
                                            System.out.println("메인메뉴> 수강생 관리> 수강생 상세 관리>상태별 수강생 목록");
                                            studentStateType = getStudentStateType();
                                            studentService.studentListByStatus(studentStateType);
                                        }
                                        case 4 -> {
                                            System.out.println("삭제할 수강생 아이디 입력>");
                                            studentId = Integer.parseInt(sc.nextLine());
                                            studentService.deleteStudentById(studentId);
                                            scoreService.deleteScoreByStudentId(studentId);
                                        }
                                        case 0 -> {
                                            System.out.println("이전 화면으로 돌아갑니다.");
                                            System.out.println("메인메뉴> 이전메뉴로 이동>...");
                                            break loopA;
                                        }
                                    }
                                } catch (IllegalStateException e) {
                                    System.out.println(e.getMessage());
                                    System.out.println();

                                } catch (NumberFormatException e2) {
                                    System.out.print("일치하는 숫자만 입력하세요 !!\n\n");
                                }
                            }
                        }
                        case 0 -> {
                            System.out.println("이전 화면으로 돌아갑니다.");
                            System.out.println("메인메뉴> 이전메뉴로 이동>...");
                            return;
                        }
                        default -> System.out.println("다시입력바랍니다.");
                    }
                } catch (NumberFormatException e) {
                    System.out.print("일치하는 숫자만 입력하세요 !!\n\n");
                }
            }
        }
    }

    /**
     * @차도범 입력받은 수강생 상태에 String -> StudentStateType enum으로 변경해서 반환
     */
    private StudentStateType getStudentStateType() {
        System.out.print("[1]green [2]red [3]yellow ");
        return switch (sc.nextLine()) {
            case "1" -> StudentStateType.GREEN;
            case "2" -> StudentStateType.RED;
            case "3" -> StudentStateType.YELLOW;
            default -> throw new IllegalStateException("\n상태 불일치 항목!! \n\n");
        };
    }
}