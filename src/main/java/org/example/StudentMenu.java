
// "메인메뉴>수강생 관리"

package org.example;

import static org.example.Menu.dbStorage;
import static org.example.Menu.sc;

public class StudentMenu {
    public void displayStudentView() {

        while (true) {
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 조회");
            System.out.println("3. 이전메뉴로 이동");
            System.out.printf("%n");

            int i = Integer.parseInt(sc.nextLine());
            if (sc.hasNextLine()) ;
            try {
                switch (i) {
                    case 1:
                        System.out.println("***** 수강생 등록*****");
                        System.out.println("메인메뉴> 수강생 관리>...");


                        break;
                    case 2:
                        System.out.println("***** 수강생 조회*****");
                        System.out.println("메인메뉴> 수강생 관리>...");


                        break;

                    case 3:
                        System.out.println("이전 화면으로 돌아갑니다.");
                        System.out.println("메인메뉴> 이전메뉴로 이동>...");
                        System.out.printf("%n");
                        return;


                    default:
                        System.out.println("다시입력바랍니다.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자외 문자를 입력하였습니다, 다시 입력바랍니다.");
            }
//else{
//System.out.println("입력이 없습니다, 다시입력바랍니다.");
        }
    }
}