package org.example;

import org.example.domain.Subject;
import org.example.parser.Parser;
import org.example.service.ScoreService;

import static org.example.Menu.dbStorage;
import static org.example.Menu.sc;

public class ScoreMenu {
    public void displayScoreView() {

        while (true) {
            System.out.println("1. 수강생 점수 등록");
            System.out.println("2. 수강생 점수 조회");
            System.out.println("3. 수강생 과목별 회차 점수 수정");
            System.out.println("4. 메인으로 이동");
            try {
                switch (Integer.parseInt(sc.nextLine())) {
                    case 1:
                        System.out.print("과목 고유번호 입력 : ");
                        int subjectIdInput = Integer.parseInt(sc.nextLine());
                        System.out.print("학생 고유번호 입력 : ");
                        int studentIdInput = Integer.parseInt(sc.nextLine());
                        System.out.print("회차 번호 입력 : ");
                        int roundInput = Integer.parseInt(sc.nextLine());
                        System.out.print("학생 점수 입력 : ");
                        int scoreInput = Integer.parseInt(sc.nextLine());
                        Subject findSubjectData = new Parser().scoreCreate(subjectIdInput, studentIdInput, roundInput, scoreInput);
                        dbStorage.saveScoreList(new ScoreService().scoreCreateV1(findSubjectData, studentIdInput, roundInput, scoreInput));
                        break;
                    case 4:
                        return;
                    default:
                        throw new RuntimeException("에러~");
                }
            } catch (NumberFormatException e) {
                System.out.println("1 ~ 4 메뉴에 맞게 입력해주세요\n");
            }
        }

    }
}
