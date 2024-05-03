package org.example.service;


import lombok.Getter;
import org.example.DBStorage;
import org.example.Main;
import org.example.domain.Student;
import org.example.domain.Subject;

import java.util.*;

public class StudentService {
    static final int MIN_REQUIRED_SUBJECTS = 3;
    static final int MIN_ELECTIVE_SUBJECTS = 2;
    List<Subject> sub = DBStorage.getSubjectList();
    Set<Integer> subjectId = new HashSet<>();
    Scanner sc = new Scanner(System.in);
    int rSub = 0;
    int eSub = 0;

    //수강생 등록, 조회 화면
    public void displayStudentView () {
        while(true) {
            System.out.println("==================================");
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 목록 조회");
            System.out.println("3. 메인 화면 이동");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> createStudent();
                //case 2 ->
                case 3 -> {return;}
                default -> System.out.println("잘못 입력하셨습니다.");
            }
        }
    }

    //수강자 생성
    public void createStudent () {
        String name = inputString("수강생 이름 입력: ");
        String birth = inputString("수강생 생년월일 입력: ");

        sub.forEach(subject -> {
           System.out.println("고유ID: " + subject.getSubjectId() + ", 제목: " + subject.getSubjectName() + ", 과목: " + subject.getSubjectType());
        });

        System.out.println("\n수강할 과목의 제목을 입력해주세요. (종료 exit)");

        Integer subId;
        while((subId=addSubject()) != 0){
            subjectId.add(subId);
        }

        if(rSub>=MIN_REQUIRED_SUBJECTS && eSub>=MIN_ELECTIVE_SUBJECTS) {
            System.out.println("수강자가 생성되었습니다.");
            Student st = new Student(++Main.uNumber, name, birth, subjectId);
            DBStorage.addStudentList(st);
            rSub=0;
            eSub=0;
        }
        else if(rSub<MIN_REQUIRED_SUBJECTS && eSub<MIN_ELECTIVE_SUBJECTS){
            System.out.println("필수과목이 " + (MIN_REQUIRED_SUBJECTS-rSub) + "과목, 선택과목이 " + (MIN_ELECTIVE_SUBJECTS-eSub) + "선택과목이 부족해 수강생이 등록되지 않습니다.");
        }
        else if(rSub<MIN_REQUIRED_SUBJECTS){
            System.out.println("필수과목이 " + (MIN_REQUIRED_SUBJECTS-rSub) + "과목 부족해 수강생이 등록되지 않습니다.");
        }
        else {
            System.out.println("선택과목이 " + (MIN_ELECTIVE_SUBJECTS-eSub) + "과목 부족해 수강생이 등록되지 않습니다.");

        }


    }

    //String 값 입력
    public String inputString(String m){
        System.out.print(m);
        return sc.nextLine();
    }

    //수강 과목 추가
    public Integer addSubject(){
        while(true){
            String s = sc.nextLine();
            if("exit".equals(s)){
                return 0;
            }
            for(Subject si:sub){
                if(si.getSubjectName().equals(s)){
                    System.out.println("과목 추가 완료");

                    if(si.getSubjectType().equals("SUBJECT_TYPE_MANDATORY")){
                        rSub++;
                    }
                    else{
                        eSub++;
                    }

                    return si.getSubjectId();
                }
            }
            System.out.println("일치하는 과목이 없습니다. 다시 입력해주세요.");
        }
    }
}