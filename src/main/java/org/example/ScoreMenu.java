package org.example;

import org.example.db.DBManager;
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
    //FIXME 불필요한 선언
    private final StudentService studentService;
    private final ScoreService scoreService;
    //FIXME 불필요한 선언
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
            System.out.println("4. 수강생의 과목별 평균 등급을 조회");
            System.out.println("5. 특정 상태 수강생들의 필수 과목 평균 등급을 조회");
            System.out.println("0. 이전메뉴로 이동");
            System.out.printf("%n");

            int subjectIdInput, studentIdInput, roundInput, scoreInput;
            if (sc.hasNextLine()) {
                try {
                    int i = Integer.parseInt(sc.nextLine());
                    switch (i) {
                        case 1:
                            try {
                                System.out.println("***** 수강생 점수 등록 *****");
                                System.out.println("메인메뉴> 수강생 점수 관리>...");


                                System.out.print("과목 고유번호 입력 : ");
                                subjectIdInput = Integer.parseInt(sc.nextLine());
                                System.out.print("학생 고유번호 입력 : ");
                                studentIdInput = Integer.parseInt(sc.nextLine());
                                System.out.print("회차 번호 입력 : ");
                                roundInput = Integer.parseInt(sc.nextLine());
                                System.out.print("학생 점수 입력 : ");
                                scoreInput = Integer.parseInt(sc.nextLine());

                                scoreService.scoreCreateV1(subjectIdInput, studentIdInput, roundInput, scoreInput);

                            } catch (NumberFormatException e) {
                                throw new NumberFormatException();
                            } catch (RuntimeException e) {
                                break;
                            }
                            break;

                        case 2: /** @세미 */
                            try{
                                System.out.println("*****수강생 점수 조회*****");
                                System.out.println("메인메뉴> 수강생 점수관리>...");

                                System.out.print("수강생 고유번호 입력 : ");
                                studentIdInput = Integer.parseInt(sc.nextLine());

                                System.out.print("과목 고유번호 입력 : ");
                                subjectIdInput = Integer.parseInt(sc.nextLine());
                                new StudentScoreRead(dbManager).LoadScore(studentIdInput, subjectIdInput);
                            }
                            catch(NumberFormatException e){
                                throw new NumberFormatException();
                            }

                            break;

                        case 3: /** @세미 */
                            try{
                                System.out.println("*****수강생 과목별 회차점수 수정*****");
                                System.out.println("메인메뉴> 수강생 점수관리>...");
                                System.out.print("수강생 고유번호 입력 : ");
                                studentIdInput = Integer.parseInt(sc.nextLine());
                                System.out.print("과목 고유번호 입력 : ");
                                subjectIdInput = Integer.parseInt(sc.nextLine());
                                System.out.print("회차 번호 조회 : ");
                                int round = Integer.parseInt(sc.nextLine());
                                System.out.print("점수 입력 : ");
                                int score = Integer.parseInt(sc.nextLine());
                                new StudentScoreRead(dbManager).UpdateScore(studentIdInput, subjectIdInput, round,score);
                            }catch (NumberFormatException e){
                                throw new NumberFormatException();
                            }

                            break;

                        case 4:
                            System.out.println("*****수강생의 과목별 평균 등급을 조회*****");
                            System.out.println("메인메뉴> 수강생 점수관리>...");
                            System.out.print("수강생 고유번호 입력 : ");
                            studentIdInput = Integer.parseInt(sc.nextLine());
                            System.out.print("과목 고유번호 입력 : ");
                            subjectIdInput = Integer.parseInt(sc.nextLine());
                            new StudentScoreRead(dbManager).LoadAvgScore(studentIdInput,subjectIdInput);

                            break;

                        case 5:
                            System.out.println("*****특정 상태 수강생들의 필수 과목 평균 등급을 조회*****");
                            System.out.println("메인메뉴> 수강생 점수관리>...");
                            System.out.println("1.Green 2.Yellow 3.Red ");
                            int state = Integer.parseInt(sc.nextLine());
                            new StudentScoreRead(dbManager).LoadStudentStateOfRequiredSubject(state);
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