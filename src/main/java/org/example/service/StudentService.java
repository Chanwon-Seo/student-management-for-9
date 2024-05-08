package org.example.service;

import org.example.db.DBManager;
import org.example.domain.Student;
import org.example.domain.Subject;
import org.example.domain.enums.StudentStateType;
import org.example.domain.enums.SubjectType;
import org.example.parser.Parser;
import org.example.parser.ScoreParser;
import org.example.parser.StudentParser;
import org.example.parser.SubjectParser;

import java.util.*;


public class StudentService {

    private final DBManager dbManager;
    private final StudentParser studentParser;
    private final SubjectParser subjectParser;
    private final ScoreParser scoreParser;

    static final int MIN_REQUIRED_SUBJECTS = 3;
    static final int MIN_ELECTIVE_SUBJECTS = 2;

    //TODO
    List<Subject> sub;
    HashSet<Integer> dup = new HashSet<>();
    Set<Integer> subjectId = new HashSet<>();
    Scanner sc = new Scanner(System.in);
    Parser parser;
    int rSub = 0;
    int eSub = 0;

    List<Student> studentList;
    public StudentService(DBManager dbManager) {
        this.dbManager = dbManager;
        this.studentParser = new StudentParser(dbManager);
        this.subjectParser = new SubjectParser(dbManager);
        this.scoreParser = new ScoreParser(dbManager);
        sub = dbManager.findBySubjects();
        studentList = dbManager.findByStudents();
        //TODO 코드 변경
//        parser = new Parser(dbManager);
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
            System.out.println("상태 : " + student.getStudentStateType().getStudentTypeValue());

            //찾은 과목리스트와 과목리스트를
            for (Subject subject : dbManager.findBySubjects()) {
                for (Integer id : student.getSubjectId()) {
                    if (Objects.equals(subject.getSubjectId(), id)) {
                        System.out.println(subject.getSubjectId() + " : " +
                                subject.getSubjectName() + " - " + subject.getSubjectType());
                    }
                }
            }
            System.out.println();
            System.out.println();
        }
    }

    /*
     * @차도범
     * 상태별 수강색 목록
     * */
    public void studentListByStatus(StudentStateType studentStateType) {
        List<Student> studentList = dbManager.findByStudents();
        System.out.println("아이디 / 이름 / 상태");
        for (Student student : studentList) {
            if (student.getStudentStateType().equals(studentStateType)) {
                System.out.println(student.getStudentId() + " : "
                        + student.getStudentName() + " - " + student.getStudentStateType());
            }
        }
        System.out.print("\n\n");
    }

    //수강자 등록
    public void createStudent() {
        String name = inputString("수강생 이름 입력: ");
        String birth = inputString("수강생 생년월일 입력: ");
        String status = inputString("현재 상태를 입력하세요.(선택) green: 좋음, yellow: 보통, red: 나쁨, nostatus: 모름\n");

        StudentStateType stateType = inputStatus(status);

        sub.forEach(subject -> {
            String output = String.format("고유ID: %-5d 제목: %-20s \t과목: %s",
                    subject.getSubjectId(),
                    subject.getSubjectName(),
                    subject.getSubjectType());
            System.out.println(output);
        });

        Integer subId;
        while ((subId = addSubject()) != 0) {
            subjectId.add(subId);
            dup.add(subId);
        }


        if (subjectMinCheck(rSub, eSub)) {
            System.out.println("수강자가 생성되었습니다.");
            //TODO
            dbManager.updateStudentIdNum(dbManager.findByStudentIdNum());
            Student st = new Student(dbManager.findByStudentIdNum(), name, birth, subjectId, stateType);
            dbManager.saveStudent(st);
        }
        rSub = 0;
        eSub = 0;
        dup.clear();
        System.out.println();
    }

    //String 값 입력
    public String inputString(String m) {
        System.out.print(m);
        return sc.nextLine();
    }

    //상태 값 가져오기
    public StudentStateType inputStatus(String status) {
        return StudentStateType.studentStateType(status);
    }

    public Integer addSubject() {
        while (true) {
            System.out.println("\n수강할 과목의 제목을 입력해주세요. (종료 exit)");
            String s = sc.nextLine();

            if ("exit".equals(s)) {
                return 0;
            }

            try {
                Integer id = Integer.parseInt(s);

                if (subjectIdCheck(id)) {
                    if (subjectIdDuplicationCheck(dup, id)) {
                        System.out.println("과목 추가 완료.");
                        Subject subject = subjectReturn(id);

                        if (subject != null && subject.getSubjectType().equals(SubjectType.REQUIRED)) {
                            rSub++;
                            System.out.println("필수: " + rSub + ", 선택: " + eSub);
                        } else {
                            eSub++;
                            System.out.println("필수: " + rSub + ", 선택: " + eSub);
                        }
                        return id;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자 또는 \"exit\"만 입력해주세요.");
            }

        }
    }

    /**
     * @return
     * @성균 수강생 과목 등록 검증
     * id가 검증되면 참 반환
     */
    public boolean subjectIdCheck(Integer subjectId) {
        try {
            if (subjectParser.subjectIsEmptyCheck(subjectId)) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * @return
     * @성균 수강생 과목 중복 검증
     * 중복이면 거짓 반환
     */
    public boolean subjectIdDuplicationCheck(HashSet<Integer> dup, Integer subjectId){
        try{
            if(subjectParser.subjectIdDuplicationCheck(dup, subjectId)) {
                return true;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * @return
     * @성균 수강생 과목 등록 검증
     * id가 검증되면 해당 subject 클래스 반환
     */
    public Subject subjectReturn(Integer subjectId){
        Subject subject = null;
        try{
            subject = subjectParser.subjectEmptyCheckValid(subjectId);
        }catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
        return subject;
    }

    /**
     * @return
     * @성균 과목 등록 검증
     */

    public boolean subjectMinCheck(int rSub, int eSub){
        try{
            if(subjectParser.subjectMinCheck(rSub,eSub)) {
                return true;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return false;
    }


}