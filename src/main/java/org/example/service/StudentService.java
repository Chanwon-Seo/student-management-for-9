package org.example.service;


import org.example.DBStorage;
import org.example.Main;
import org.example.domain.Student;
import org.example.domain.Subject;

import java.util.*;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class StudentService {


    static final int MIN_REQUIRED_SUBJECTS = 3;
    static final int MIN_ELECTIVE_SUBJECTS = 2;
    List<Subject> sub = DBStorage.getSubjectList();
    Set<Integer> subjectId = new HashSet<>();
    Scanner sc = new Scanner(System.in);
    int rSub = 0;
    int eSub = 0;

    DBStorage db = new DBStorage();
    List<Student> studentList = DBStorage.getStudentList();

    //학생 리스트
    public void getStudentList() {
        System.out.println("id / name");
        for (Student student : studentList) {
            System.out.println(student.getStudentIdInteger() + " : " + student.getStudentName());
        }
        System.out.println("확인할 학생 아이디 입력 (종류 -1)>");
        int id = Integer.parseInt(sc.nextLine());
        if (id == -1) return;
        Student student;
        if(studentFindById(id)) getStudentDetail(studentList.get(id));
    }

    //학생 상세
    public void getStudentDetail(Student student) {
        System.out.println("----학생 상세-----");
        System.out.println("id : " + student.getStudentIdInteger());
        System.out.println("이름 : " + student.getStudentName());
        System.out.println("생년월일 : " + student.getBirthDay());
        System.out.println("상태 : " + student.getStudentState());
        for (Integer subjectId : student.getSubjectId()) {
            System.out.println("과목 : " + subjectId);
        }
    }

    //학생 아이디로 검색
    public boolean studentFindById(Integer studentId) {
        for (Student student : studentList) {
            if (student.getStudentIdInteger() == studentId) {
                return true;
            }
        }
//        throw new Exception("null");
        return false;
    }


    //수강생 등록, 조회 화면
    public void displayStudentView() {
        while (true) {
            System.out.println("==================================");
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 목록 조회");
            System.out.println("3. 메인 화면 이동");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> createStudent();
                case 2 -> getStudentList();
                case 3 -> {
                    return;
                }
                default -> System.out.println("잘못 입력하셨습니다.");
            }
        }
    }

    //수강자 생성
    public void createStudent() {
        String name = inputString("수강생 이름 입력: ");
        String birth = inputString("수강생 생년월일 입력: ");

        sub.forEach(subject -> {
            System.out.println("고유ID: " + subject.getSubjectId() + ", 제목: " + subject.getSubjectName() + ", 과목: " + subject.getSubjectType());
        });

        System.out.println("\n수강할 과목의 제목을 입력해주세요. (종료 exit)");

        Integer subId;
        while ((subId = addSubject()) != 0) {
            subjectId.add(subId);
        }

        if (rSub >= MIN_REQUIRED_SUBJECTS && eSub >= MIN_ELECTIVE_SUBJECTS) {
            System.out.println("수강자가 생성되었습니다.");
            Student st = new Student(++Main.uNumber, name, birth, subjectId);
            DBStorage.addStudentList(st);
            rSub = 0;
            eSub = 0;
        } else if (rSub < MIN_REQUIRED_SUBJECTS && eSub < MIN_ELECTIVE_SUBJECTS) {
            System.out.println("필수과목이 " + (MIN_REQUIRED_SUBJECTS - rSub) + "과목, 선택과목이 " + (MIN_ELECTIVE_SUBJECTS - eSub) + "선택과목이 부족해 수강생이 등록되지 않습니다.");
        } else if (rSub < MIN_REQUIRED_SUBJECTS) {
            System.out.println("필수과목이 " + (MIN_REQUIRED_SUBJECTS - rSub) + "과목 부족해 수강생이 등록되지 않습니다.");
        } else {
            System.out.println("선택과목이 " + (MIN_ELECTIVE_SUBJECTS - eSub) + "과목 부족해 수강생이 등록되지 않습니다.");

        }


    }

    //String 값 입력
    public String inputString(String m) {
        System.out.print(m);
        return sc.nextLine();
    }

    //수강 과목 추가
    public Integer addSubject() {
        while (true) {
            String s = sc.nextLine();
            if ("exit".equals(s)) {
                return 0;
            }
            for (Subject si : sub) {
                if (si.getSubjectName().equals(s)) {
                    System.out.println("과목 추가 완료");

                    if (si.getSubjectType().equals("SUBJECT_TYPE_MANDATORY")) {
                        rSub++;
                    } else {
                        eSub++;
                    }

                    return si.getSubjectId();
                }
            }
            System.out.println("일치하는 과목이 없습니다. 다시 입력해주세요.");
        }
    }
}