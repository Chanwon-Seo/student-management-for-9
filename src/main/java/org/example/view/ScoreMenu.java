package org.example.view;

import org.example.db.DBManager;
import org.example.service.ScoreService;

import static org.example.view.Menu.sc;

public class ScoreMenu {
    private final ScoreService scoreService;

    public ScoreMenu(DBManager dbManager) {
        this.scoreService = new ScoreService(dbManager);
    }

    public void displayScoreView() {
        while (true) {
            System.out.println("***** 수강생 점수 관리 *****");
            System.out.println("메인메뉴> 수강생 점수 관리>...");
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
                                System.out.println("메인메뉴> 수강생 점수 관리>수강생 점수 등록...");

                                System.out.print("수강생 고유번호 입력 : ");
                                studentIdInput = Integer.parseInt(sc.nextLine());
                                System.out.print("과목 고유번호 입력 : ");
                                subjectIdInput = Integer.parseInt(sc.nextLine());
                                System.out.print("회차 번호 입력 : ");
                                roundInput = Integer.parseInt(sc.nextLine());
                                System.out.print("학생 점수 입력 : ");
                                scoreInput = Integer.parseInt(sc.nextLine());

                                scoreService.scoreCreateV3(subjectIdInput, studentIdInput, roundInput, scoreInput);

                            } catch (NumberFormatException e) {
                                throw new NumberFormatException();
                            } catch (RuntimeException e) {
                                System.out.printf("\n%s\n\n", e.getMessage());
                                break;
                            }
                            break;

                        case 2: /** @세미 */
                            try {
                                System.out.println("*****수강생 점수 조회*****");
                                System.out.println("메인메뉴> 수강생 점수관리>수강생 점수 조회...");

                                System.out.print("수강생 고유번호 입력 : ");
                                studentIdInput = Integer.parseInt(sc.nextLine());

                                System.out.print("과목 고유번호 입력 : ");
                                subjectIdInput = Integer.parseInt(sc.nextLine());
                                scoreService.loadAllScore(studentIdInput, subjectIdInput);
                            } catch (NullPointerException e) {
                                System.out.println(e.getMessage());
                                throw new NullPointerException();
                            }

                            break;

                        case 3: /** @세미 */
                            try {
                                System.out.println("*****수강생 과목별 회차점수 수정*****");
                                System.out.println("메인메뉴> 수강생 점수관리> 수강생 과목별 회차점수 수정...");
                                System.out.print("수강생 고유번호 입력 : ");
                                studentIdInput = Integer.parseInt(sc.nextLine());
                                System.out.print("과목 고유번호 입력 : ");
                                subjectIdInput = Integer.parseInt(sc.nextLine());
                                System.out.print("회차 번호 조회 : ");
                                int round = Integer.parseInt(sc.nextLine());
                                System.out.print("점수 입력 : ");
                                int score = Integer.parseInt(sc.nextLine());
                                scoreService.updateScore(studentIdInput, subjectIdInput, round, score);
                            } catch (NumberFormatException e) {
                                throw new NumberFormatException();
                            } catch (NullPointerException e) {
                                System.out.println(e.getMessage());
                                break;
                            }

                            break;

                        case 4:
                            try {
                                System.out.println("*****수강생의 과목별 평균 등급을 조회*****");
                                System.out.println("메인메뉴> 수강생 점수관리> 수강생의 과목별 평균 등급을 조회...");
                                System.out.print("수강생 고유번호 입력 : ");
                                studentIdInput = Integer.parseInt(sc.nextLine());
//                                System.out.print("과목 고유번호 입력 : ");
//                                subjectIdInput = Integer.parseInt(sc.nextLine());
                                scoreService.loadAvgScoreAll(studentIdInput);
                            } catch (NumberFormatException e) {
                                throw new NumberFormatException();
                            } catch (NullPointerException e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                            break;

                        case 5:
                            try {
                                System.out.println("*****특정 상태 수강생들의 필수 과목 평균 등급을 조회*****");
                                System.out.println("메인메뉴> 수강생 점수관리> 특정상태 수강생들의 필수 과목 평균등급을 조회...");
                                System.out.println("[1]Green [2]Yellow [3]Red ");
                                int state = Integer.parseInt(sc.nextLine());
                                scoreService.loadStudentStateOfRequiredSubject(state);
                            } catch (NumberFormatException e) {
                                throw new NumberFormatException();
                            } catch (NullPointerException e) {
                                System.out.println(e.getMessage());
                                break;
                            }
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
                    System.out.println("something wrong!, 다시 입력바랍니다.");
                    System.out.println();
                }
            }
        }
    }

}