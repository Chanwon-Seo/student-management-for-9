package org.example;

import org.example.db.DBManager;
import org.example.domain.Subject;
import org.example.parser.SubjectParser;
import org.example.service.ScoreService;
import org.example.service.StudentScoreRead;
import org.example.service.StudentService;
import org.example.service.SubjectService;

import static org.example.Menu.sc;

/**
 * 메인메뉴>수강생 점수관리"
 */
//@RequiredArgsConstructor
public class ScoreMenu {

    private final DBManager dbManager;

    private final StudentService studentService;
    private final ScoreService scoreService;
    private final SubjectService subjectService;

    public ScoreMenu(DBManager dbManager) {
        this.dbManager = dbManager;
        this.studentService = new StudentService(dbManager);
        this.scoreService = new ScoreService(dbManager);
        this.subjectService = new SubjectService(dbManager);
    }

    public void displayScoreView() {
        while (true) {
            System.out.println("1. 수강생 점수 등록");
            System.out.println("2. 수강생 점수 조회");
            System.out.println("3. 수강생 과목별 회차 점수 수정");
            System.out.println("0. 이전메뉴로 이동");
            System.out.printf("%n");

            if (sc.hasNextLine()) {
                try {
                    int i = Integer.parseInt(sc.nextLine());
                    switch (i) {
                        case 1:
//                            int subjectIdInput, studentIdInput, roundInput, scoreInput;
//                            Subject findSubjectData;
                            try {
                                System.out.println("***** 수강생 점수 등록 *****");
                                System.out.println("메인메뉴> 수강생 점수 관리>...");


                                System.out.print("과목 고유번호 입력 : ");
                                int subjectIdInput = Integer.parseInt(sc.nextLine());
                                System.out.print("학생 고유번호 입력 : ");
                                int studentIdInput = Integer.parseInt(sc.nextLine());
                                System.out.print("회차 번호 입력 : ");
                                int roundInput = Integer.parseInt(sc.nextLine());
                                System.out.print("학생 점수 입력 : ");
                                int scoreInput = Integer.parseInt(sc.nextLine());
                                //TODO v1
//                                findSubjectData = new Parser(dbManager).scoreCreate(subjectIdInput, studentIdInput, roundInput, scoreInput);
//                                dbManager.saveScore(new ScoreService().scoreCreateV1(findSubjectData, studentIdInput, roundInput, scoreInput));
//                                System.out.println("수강생의 점수를 등록하였습니다.\n");
                                //TODO v2
                                scoreService.scoreCreateV1(subjectIdInput, studentIdInput, roundInput, scoreInput);

                            } catch (NumberFormatException e) {
                                throw new NumberFormatException();
                            } catch (RuntimeException e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                            break;

                        case 2: /* @세미 */
                            System.out.println("***** 수강생 점수 조회 *****");
                            System.out.println("메인메뉴> 수강생 점수 관리>...");
                            System.out.print("수강생 고유번호 입력 : ");
                            int studentInput = Integer.parseInt(sc.nextLine());
                            System.out.print("과목 고유번호 입력 : ");
                            int subjectInput = Integer.parseInt(sc.nextLine());
                            new StudentScoreRead(dbManager).LoadScore(studentInput, subjectInput);
                            break;

                        case 3: /* @세미 */
                            System.out.println("***** 수강생 과목별 회차 점수 수정 *****");
                            System.out.println("메인메뉴> 수강생 점수 관리>...");
                            System.out.print("수강생 고유번호 입력 : ");
                            studentInput = Integer.parseInt(sc.nextLine());
                            System.out.print("과목 고유번호 입력 : ");
                            subjectInput = Integer.parseInt(sc.nextLine());
                            System.out.print("회차 번호 조회 : ");
                            int round = Integer.parseInt(sc.nextLine());
                            new StudentScoreRead(dbManager).UpdateScore(studentInput, subjectInput, round);
                            break;

                        case 0:
                            System.out.println("이전 화면으로 돌아갑니다.");
                            System.out.println("메인메뉴> 이전메뉴로 이동>...");
                            System.out.printf("%n");
                            return;

                        default:
                            System.out.println("다시 입력바랍니다.");

                    }
                } catch (NumberFormatException e) {
                    System.out.println("something wrong~, 다시 입력바랍니다.");
                }
            }
        }
    }
}