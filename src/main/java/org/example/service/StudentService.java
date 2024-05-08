package org.example.service;

import org.example.domain.Student;
import org.example.domain.Subject;
import org.example.domain.enums.StudentStateType;
import org.example.domain.enums.SubjectType;
import org.example.parser.Parser;
import org.example.db.DBManager;
import org.example.parser.StudentParser;

import java.util.*;

import java.util.List;

public class StudentService {

    //private final DBManager dbManager;

    static final int MIN_REQUIRED_SUBJECTS = 3;
    static final int MIN_ELECTIVE_SUBJECTS = 2;
    //TODO
    List<Subject> sub;
    Set<Integer> subjectId = new HashSet<>();
    Scanner sc = new Scanner(System.in);
    DBManager dbManager;
    Parser parser;
    int rSub = 0;
    int eSub = 0;

    List<Student> studentList;

    public StudentService(DBManager dbManager) {
        this.dbManager = dbManager;
        parser = new Parser(dbManager);
        sub = dbManager.findBySubjects();
        studentList = dbManager.findByStudents();
    }


    /**
     * @차도범 수강생 목록을 출력
     */
    public void getStudentList() {
        System.out.println("id / name");
        for (Student student : dbManager.findByStudents()) {
            System.out.println(student.getStudentId() + " : " + student.getStudentName());
        }
    }

    /**
     * @차도범 수강생 상세 값 출력
     */
    public void getStudentDetail(int studentId) {
        StudentParser studentParser = new StudentParser(dbManager);
        Student student = studentParser.studentFindByIdEmptyCheckValid(studentId);
        if (student == null) return;
        else {
            System.out.println("----학생 상세-----");
            System.out.println("id : " + student.getStudentId());
            System.out.println("이름 : " + student.getStudentName());
            System.out.println("생년월일 : " + student.getBirthDay());
            System.out.println("상태 : " + student.getStudentStateType());

            //찾은 과목리스트와 과목리스트를
            for (Subject subject : dbManager.findBySubjects()) {
                for (Integer id : student.getSubjectId()) {
                    if (Objects.equals(subject.getSubjectId(), id)) {
                        System.out.println(subject.getSubjectId() + " : " +
                                subject.getSubjectName() + " - " + subject.getSubjectType());
                    }
                }
            }
        }
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

//        sub.forEach(subject -> {
//            System.out.println("고유ID: " + subject.getSubjectId() + ", 제목: " + subject.getSubjectName() + ", 과목: " + subject.getSubjectType());
//        });

        System.out.println("\n수강할 과목의 제목을 입력해주세요. (종료 exit)");

        Integer subId;
        while ((subId = addSubject()) != 0) {
            subjectId.add(subId);
        }

        if (rSub >= MIN_REQUIRED_SUBJECTS && eSub >= MIN_ELECTIVE_SUBJECTS) {
            System.out.println("수강자가 생성되었습니다.");
            //TODO
            dbManager.updateStudentIdNum(dbManager.findByStudentIdNum());
            Student st = new Student(dbManager.findByStudentIdNum(), name, birth, subjectId);
            dbManager.saveStudent(st);
            rSub = 0;
            eSub = 0;
        } else if (rSub < MIN_REQUIRED_SUBJECTS && eSub < MIN_ELECTIVE_SUBJECTS) {
            System.out.println("필수과목이 " + (MIN_REQUIRED_SUBJECTS - rSub) + "과목, 선택과목이 " + (MIN_ELECTIVE_SUBJECTS - eSub) + "과목이 부족해 수강생이 등록되지 않습니다.");
        } else if (rSub < MIN_REQUIRED_SUBJECTS) {
            System.out.println("필수과목이 " + (MIN_REQUIRED_SUBJECTS - rSub) + "과목 부족해 수강생이 등록되지 않습니다.");
        } else {
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

    public Integer addSubject() {
        while (true) {
            String s = sc.nextLine();

            if ("exit".equals(s)) {
                return 0;
            }

            try {
                Integer id = Integer.parseInt(s);

                if (parser.subjectIdCheck(id)) {
                    System.out.println("과목 추가 완료");
                    Subject subject = parser.subjectReturn(id);

                    if (subject != null && subject.getSubjectType().equals(SubjectType.REQUIRED)) {
                        rSub++;
                    } else {
                        eSub++;
                    }
                    return id;
                }

            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자 또는 \"exit\"만 입력해주세요.");
            }
        }
    }

}