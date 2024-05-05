package org.example;

import lombok.RequiredArgsConstructor;
import org.example.parser.Parser;
import org.example.service.ScoreService;

import static org.example.Menu.dbStorage;
import static org.example.Menu.sc;

@RequiredArgsConstructor
public class ScoreMenu {
    public void displayScoreView() {
        while (true) {
            System.out.println("1. 수강생 점수 등록");
            System.out.println("2. 수강생 점수 조회");
            System.out.println("3. 수강생 과목별 회차 점수 수정");
            System.out.println("4. 메인으로 이동");
            int i = Integer.parseInt(sc.nextLine());
            switch (i) {
                case 1:
                    new Parser().scoreCreate(1, 1, 1, 1);
                    dbStorage.saveScoreList(new ScoreService().scoreCreateV1(1, 1, 1, 1));
                    break;
            }
        }
    }
}
