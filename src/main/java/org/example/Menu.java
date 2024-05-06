package org.example;

import java.util.Scanner;

public class Menu {
    static DBStorage dbStorage = new DBStorage();
    static Scanner sc = new Scanner(System.in);

    public void startPage() {
        while (true) {
            System.out.println("1. 수강생 관리");
            System.out.println("2. 수강생 점수관리");
            System.out.println("3. 프로그램 종료(exit)");

            try {
                switch (Integer.parseInt(sc.nextLine())) {
                    case 2: // 수강생 점수관리 이동
                        new ScoreMenu().displayScoreView();
                        break;
                    case 3: // 종료
                        System.out.println("종료합니다.");
                        return;
                    default:
                        throw new NumberFormatException("1 ~ 3 메뉴에 맞게 입력해주세요");
                }
            } catch (NumberFormatException e) {
                System.out.println("1 ~ 3 메뉴에 맞게 입력해주세요\n");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}

