package org.example;

import org.example.db.DBManager;
import org.example.db.DBStorage;

import java.util.Scanner;

public class Menu {
    static DBStorage dbStorage = new DBStorage();
    static DBManager dbManager = new DBManager(dbStorage);
    static Scanner sc = new Scanner(System.in);
    private final StudentMenu studentMenu;
    private final ScoreMenu scoreMenu;

    public Menu() {
        studentMenu = new StudentMenu(dbManager);
        scoreMenu = new ScoreMenu(dbManager);
    }

    public void startPage() {
        dbManager.initData();

        studentMenu.testData();
        while (true) {
            System.out.printf("%n");
            System.out.println("[스파르타코딩클럽내일배움부트캠프 B_09조 팀프로젝트 수강생관리프로그램]");
            System.out.println("***** 이동하실 메뉴를 입력하여주십시오. *****");
            System.out.println("1. 수강생 관리");
            System.out.println("2. 수강생 점수 관리");
            System.out.println("0. 시스템 종료");
            System.out.printf("%n");

            if (sc.hasNextLine()) {
                try {

                    switch (Integer.parseInt(sc.nextLine())) {
                        case 1:
                            System.out.println("***** 수강생 관리 *****");
                            System.out.println("메인메뉴> 수강생 관리>...");
                            studentMenu.displayStudentView();
                            break;


                        case 2:
                            System.out.println("***** 수강생 점수 관리 *****");
                            System.out.println("메인메뉴> 수강생 점수 관리>...");
                            scoreMenu.displayScoreView();
                            break;


                        case 0:
                            System.out.println("시스템을 종료합니다.");
                            return;


                        default:
                            System.out.println("다시 입력바랍니다.");


                    }
                } catch (NumberFormatException i) {
                    System.out.println("숫자외 문자를 입력하였습니다,다시 입력바랍니다.");
                }

            }
        }
    }
}