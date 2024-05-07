package org.example.service;


import org.example.domain.Student;
import org.example.domain.Subject;
import org.example.parser.Parser;

import java.util.*;

import java.util.List;

public class StudentService {
    static final int MIN_REQUIRED_SUBJECTS = 3;
    static final int MIN_ELECTIVE_SUBJECTS = 2;
    //TODO
//    List<Subject> sub = DBStorage.getSubjectList();
    Set<Integer> subjectId = new HashSet<>();
    Scanner sc = new Scanner(System.in);
    Integer stNum = DBStorage.getStudentIdNum();
    Parser parser = new Parser();
    int rSub = 0;
    int eSub = 0;

//    DBStorage db = new DBStorage();
    //TODO
//    List<Student> studentList = DBStorage.getStudentList();

    //학생 리스트
    public void getStudentList() {
        System.out.println("id / name");
        for (Student student : studentList) {
            System.out.println(student.getStudentId() + " : " + student.getStudentName());
        }
        System.out.println("확인할 학생 아이디 입력 (종류 -1)>");
        int id = Integer.parseInt(sc.nextLine());
        if (id == -1) return;
        Student student = studentFindById(id);
        getStudentDetail(student);
    }

    //학생 상세
    public void getStudentDetail(Student student) {
        System.out.println("----학생 상세-----");
        System.out.println("id : " + student.getStudentId());
        System.out.println("이름 : " + student.getStudentName());
        System.out.println("생년월일 : " + student.getBirthDay());
        System.out.println("상태 : " + student.getStudentState());
        for (Integer subjectId : student.getSubjectId()) {
            System.out.println("과목 : " + subjectId);
        }
    }

    //학생 아이디로 검색
    public Student studentFindById(Integer studentId) {
        for (Student student : studentList) {
            if (student.getStudentId() == studentId) {
                return student;
            }
        }
        return null;
    }


    //수강생 등록, 조회 화면


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
            //TODO
            Student st = new Student(stNum, name, birth, subjectId);
            DBStorage.setStudentIdNum(++stNum);
            DBStorage.getStudentList().add(st);
            rSub=0;
            eSub=0;
        }
        else if(rSub<MIN_REQUIRED_SUBJECTS && eSub<MIN_ELECTIVE_SUBJECTS){
            System.out.println("필수과목이 " + (MIN_REQUIRED_SUBJECTS-rSub) + "과목, 선택과목이 " + (MIN_ELECTIVE_SUBJECTS-eSub) + "과목이 부족해 수강생이 등록되지 않습니다.");
        }
        else if(rSub<MIN_REQUIRED_SUBJECTS){
            System.out.println("필수과목이 " + (MIN_REQUIRED_SUBJECTS-rSub) + "과목 부족해 수강생이 등록되지 않습니다.");
        }
        else {
            System.out.println("선택과목이 " + (MIN_ELECTIVE_SUBJECTS - eSub) + "과목 부족해 수강생이 등록되지 않습니다.");

            rSub = 0;
            eSub = 0;
        }
        System.out.println();
    }

    //String 값 입력
    public String inputString(String m) {
        System.out.print(m);
        return sc.nextLine();
    }

    public Integer addSubject(){
        while(true){
            String s = sc.nextLine();

            if("exit".equals(s)) {
                return 0;
            }

            try {
                Integer id = Integer.parseInt(s);

                if (parser.subjectIdCheck(id)) {
                    System.out.println("과목 추가 완료");
                    Subject subject = parser.subjectReturn(id);

                    if (subject != null && subject.getSubjectType().equals("SUBJECT_TYPE_MANDATORY")) {
                        rSub++;
                    } else {
                        eSub++;
                    }
                    return id;
                }

            } catch (NumberFormatException e){
                System.out.println("잘못된 입력입니다. 숫자 또는 \"exit\"만 입력해주세요.");
            }

        }
    }

}