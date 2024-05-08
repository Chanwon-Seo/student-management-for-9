package org.example;

import org.example.db.DBManager;
import org.example.domain.Subject;
import org.example.parser.Parser;
import org.example.parser.ScoreParser;
import org.example.parser.StudentParser;
import org.example.parser.SubjectParser;
import org.example.service.ScoreService;
import org.example.service.StudentScoreRead;

import static org.example.Menu.sc;

/**
 * 메인메뉴>수강생 점수관리"
 */
//@RequiredArgsConstructor
public class ScoreMenu {

    private final DBManager dbManager;

    StudentParser studentParser;
    SubjectParser subjectParser;
    ScoreParser scoreParser;


    public ScoreMenu(DBManager dbManager) {

        this.dbManager = dbManager;
        studentParser = new StudentParser(dbManager);
        subjectParser = new SubjectParser(dbManager);
        scoreParser = new ScoreParser(dbManager);
    }

    public void displayScoreView() {
        while (true) {
            System.out.println("1. 수강생 점수 등록");
            System.out.println("2. 수강생 점수 조회");
            System.out.println("3. 수강생 과목별 회차 점수 수정");
            System.out.println("4. 이전메뉴로 이동");
            System.out.printf("%n");

            int subjectIdInput, studentIdInput, roundInput, scoreInput;
            if (sc.hasNextLine()) {
                try {
                    int i = Integer.parseInt(sc.nextLine());
                    switch (i) {
                        case 1:
                            //int subjectIdInput, studentIdInput, roundInput, scoreInput;
                            Subject findSubjectData;
                            try {
                                System.out.println("***** 수강생 점수 등록*****");
                                System.out.println("메인메뉴> 수강생 점수관리>...");


                                System.out.print("과목 고유번호 입력 : ");
                                subjectIdInput = Integer.parseInt(sc.nextLine());
                                System.out.print("학생 고유번호 입력 : ");
                                studentIdInput = Integer.parseInt(sc.nextLine());
                                System.out.print("회차 번호 입력 : ");
                                roundInput = Integer.parseInt(sc.nextLine());
                                System.out.print("학생 점수 입력 : ");
                                scoreInput = Integer.parseInt(sc.nextLine());

                                findSubjectData = new Parser(dbManager).scoreCreate(subjectIdInput, studentIdInput, roundInput, scoreInput);
                                dbManager.saveScore(new ScoreService().scoreCreateV1(findSubjectData, studentIdInput, roundInput, scoreInput));
                                System.out.println("수강생의 점수를 등록하였습니다.\n");

                            } catch (NumberFormatException e) {
                                throw new NumberFormatException();
                            } catch (RuntimeException e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                            break;

                        case 2: /* @세미 */
                            try{
                                System.out.println("*****수강생 점수 조회*****");
                                System.out.println("메인메뉴> 수강생 점수관리>...");

                                System.out.print("수강생 고유번호 입력 : ");
                                studentIdInput = Integer.parseInt(sc.nextLine());
                                studentParser.studentFindByIdEmptyCheckValid(studentIdInput);  //존재하는 수강생인가?

                                System.out.print("과목 고유번호 입력 : ");
                                subjectIdInput = Integer.parseInt(sc.nextLine());
                                subjectParser.HavingsubjectCheck(studentIdInput,subjectIdInput);  // 수강생이 가지고있는 과목인가?

                                new StudentScoreRead(dbManager).LoadScore(studentIdInput, subjectIdInput);
                            }
                            catch(NumberFormatException e){
                                throw new NumberFormatException();
                            }

                            break;

                        case 3: /* @세미 */
                            try{
                                System.out.println("*****수강생 과목별 회차점수 수정*****");
                                System.out.println("메인메뉴> 수강생 점수관리>...");
                                System.out.print("수강생 고유번호 입력 : ");
                                studentIdInput = Integer.parseInt(sc.nextLine());
                                studentParser.studentFindByIdEmptyCheckValid(studentIdInput);  //존재하는 수강생인가?

                                System.out.print("과목 고유번호 입력 : ");
                                subjectIdInput = Integer.parseInt(sc.nextLine());
                                subjectParser.HavingsubjectCheck(studentIdInput,subjectIdInput);  // 수강생이 가지고있는 과목인가?

                                System.out.print("회차 번호 조회 : ");
                                int round = Integer.parseInt(sc.nextLine());
                                //scoreParser.scoreUpdateCheckValid(studentIdInput,subjectIdInput,round); // 존재하는 회차인가?

                                System.out.print("점수 입력 : ");
                                int score = Integer.parseInt(sc.nextLine());
                                scoreParser.scoreInputZeroToOneHundredCheckValid(score); //알맞은 점수범위인가

                                new StudentScoreRead(dbManager).UpdateScore(scoreParser,studentIdInput, subjectIdInput, round,score);
                            }catch (NumberFormatException e){
                                throw new NumberFormatException();
                            }

                            break;

                        case 4:
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
