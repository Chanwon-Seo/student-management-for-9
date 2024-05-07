package org.example;

import org.example.db.DBManager;
import org.example.db.DBStorage;

import java.util.Scanner;

public class Menu {
    static Scanner sc = new Scanner(System.in);

    public void startPage() {
        DBStorage dbStorage = new DBStorage();
        DBManager dbManager = new DBManager(dbStorage);

        //초기값
        dbManager.initData();

        while (true) {
            System.out.printf("%n");
            System.out.println("[스파르타코딩클럽내일배움부트캠프 B_09조 팀프로젝트 수강생관리프로그램]");
            System.out.println("***** 이동하실 메뉴를 입력하여주십시오. *****");
            System.out.println("1. 수강생 관리");
            System.out.println("2. 수강생 점수관리");
            System.out.println("3. 시스템 종료");
            System.out.printf("%n");

            if (sc.hasNextLine()) {
                try {

                    switch (Integer.parseInt(sc.nextLine())) {
                        case 1:
                            System.out.println("***** 수강생 관리 *****");
                            System.out.println("초기메뉴>수강생 관리>...");
                            new StudentMenu(dbManager).displayStudentView();
                            break;


                        case 2:
                            System.out.println("***** 수강생 점수관리 *****");
                            System.out.println("메인메뉴>수강생 점수관리>...");
                            new ScoreMenu(dbManager).displayScoreView();
                            break;


                        case 3: // 종료
                            System.out.println("시스템을 종료합니다.");
                            return;


                        default:
                            System.out.println("다시 입력바랍니다.");


                    }
                } catch (NumberFormatException i) {
                    System.out.println("숫자외 문자를 입력하였습니다,다시 입력바랍니다.");
                }

            }
//else {
//System.out.println("입력이 없습니다, 다시 입력바랍니다.");
//fixme: 입력없다면 작동해야지....넌왜 작동안하니..
        }
    }
}
