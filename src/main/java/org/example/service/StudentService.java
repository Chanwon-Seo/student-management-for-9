package org.example.service;

import org.example.db.DBManager;
import org.example.domain.Student;
import org.example.domain.Subject;
import org.example.domain.enums.StudentStateType;
import org.example.domain.enums.SubjectType;
import org.example.parser.StudentParser;
import org.example.parser.SubjectParser;
import static org.example.Menu.sc;

import java.util.*;


public class StudentService {

    private final DBManager dbManager;
    private final StudentParser studentParser;
    private final SubjectParser subjectParser;

    public StudentService(DBManager dbManager) {
        this.dbManager = dbManager;
        this.studentParser = new StudentParser(dbManager);
        this.subjectParser = new SubjectParser(dbManager);
    }

    /**
     * @차도범 수강생 아이디로 수강생 반환
     */
    public Student studentFindById(int id) throws NullPointerException {
        Student findStudent = studentParser.studentEmptyCheckValidV2(id).get();
        System.out.println("수강생 " + findStudent.getStudentName());
        return findStudent;
    }

    /**
     * @차도범 수강생 목록을 출력
     */
    public void getStudentList() {
        System.out.println("[ 수강생 목록 (고유번호 / 이름) ]");
        for (Student student : dbManager.findByStudents()) {
            System.out.println(student.getStudentId() + " : " + student.getStudentName());
        }
        System.out.printf("\n");
    }

    /**
     * @차도범 수강생 상세 값 출력
     */
    public void getStudentDetail(int studentId) {
        try {
            Student student = studentParser.studentEmptyCheckValidV2(studentId).get();
            System.out.println("##### < 학생 상세 > #####");
            System.out.println("id : " + student.getStudentId());
            System.out.println("이름 : " + student.getStudentName());
            System.out.println("생년월일 : " + student.getBirthDay());
            System.out.println("상태 : " + student.getStudentStateType().getStudentTypeValue());

            //찾은 과목리스트와 과목리스트를
            for (Subject subject : dbManager.findBySubjects()) {
                for (Integer id : student.getSubjectSet()) {
                    if (Objects.equals(subject.getSubjectId(), id)) {
                        System.out.println(subject.getSubjectId() + " : " +
                                subject.getSubjectName() + " - " + subject.getSubjectType());
                    }
                }
            }
            System.out.println();
            System.out.println();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.print("\n\n");
        }
    }

    /**
     * @차도범 상태별 수강색 목록
     */
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

    /**
     * @차도범 수강생 수정
     */
    public void editStudent(Student student, String name, String birthDay, StudentStateType studentStateType) {
        try {
            studentParser.editStudentEmptyCheckValid(name, birthDay, studentStateType);
            dbManager.editStudent(student, name, birthDay, studentStateType);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @차도범 아이디로 수강생 삭제
     */
    public void deleteStudentById(int studentId) {
        try {
            Student student = studentParser.studentEmptyCheckValidV2(studentId).get();
            boolean b = dbManager.deleteStudentById(studentId);
            if (b) System.out.println(student.getStudentName() + "수강생을 삭제했습니다..");
            else System.out.println("수강생을 삭제하지 못햇습니다.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    //수강자 등록
    public void createStudent() {
        System.out.print("수강생 이름 입력: ");
        String name = sc.nextLine();
        System.out.print("수강생 생년월일 입력: ");
        String birth = sc.nextLine();
        System.out.println("현재 상태를 입력하세요.(번호 선택)\n1.green: 좋음\n2.yellow: 보통\n3.red: 나쁨");
        String status = sc.nextLine();

        String state = switch (status) {
            case "1" -> "green";
            case "2" -> "yellow";
            case "3" -> "red";
            default -> "nostatus";
        };

        StudentStateType stateType = inputStatus(state);
        System.out.println("※공통사항※ [필수]3과목,[선택]2과목이상 신청바랍니다.");
        List<Subject> sub = dbManager.findBySubjects();
        sub.forEach(subject -> {
            String output = String.format("고유ID: %-5d 제목: %-20s \t과목: %s",
                    subject.getSubjectId(),
                    subject.getSubjectName(),
                    subject.getSubjectType());
            System.out.println(output);
        });

        HashSet<Integer> duplicationSubjectId = new HashSet<>();
        Set<Integer> subjectId = new HashSet<>();
        Integer subId;
        int[] subCounts = new int[2];
        while ((subId = addSubject(subCounts, duplicationSubjectId)) != 0) {
            subjectId.add(subId);
            duplicationSubjectId.add(subId);
        }


        if (subjectMinCheck(subCounts[0], subCounts[1])) {
            System.out.println("수강자가 생성되었습니다.");

            dbManager.updateStudentIdNum(dbManager.findByStudentIdNum());
            Student st = new Student(dbManager.findByStudentIdNum(), name, birth, subjectId, stateType);
            dbManager.saveStudent(st);
        }

        System.out.println();
    }

    //상태 값 가져오기
    public StudentStateType inputStatus(String status) {
        return StudentStateType.studentStateType(status);
    }

    public Integer addSubject(int[] subCounts, HashSet<Integer> duplicationSubjectId) {
        while (true) {
            System.out.println("\n수강할 과목의 제목을 입력해주세요. (종료 exit)");
            String s = sc.nextLine();

            if ("exit".equals(s)) {
                return 0;
            }

            try {
                Integer id = Integer.parseInt(s);

                if (subjectIdCheck(id)) {
                    if (subjectIdDuplicationCheck(duplicationSubjectId, id)) {
                        System.out.println("과목 추가 완료.");

                        Subject subject = subjectReturn(id);

                        if (subject != null && subject.getSubjectType().equals(SubjectType.REQUIRED)) {
                            subCounts[0]++;
                            System.out.println("필수: " + subCounts[0] + ", 선택: " + subCounts[1]);
                        } else {
                            subCounts[1]++;
                            System.out.println("필수: " + subCounts[0] + ", 선택: " + subCounts[1]);
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
     * @성균 수강생 과목 등록 검증
     * id가 검증되면 참 반환
     */
    public boolean subjectIdCheck(Integer subjectId) {
        try {
            if (subjectParser.subjectIsEmptyCheck(subjectId)) {
                return true;
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * @성균 수강생 과목 중복 검증
     * 중복이면 거짓 반환
     */
    public boolean subjectIdDuplicationCheck(HashSet<Integer> dup, Integer subjectId) {
        try {
            if (subjectParser.subjectIdDuplicationCheck(dup, subjectId)) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    /**
     * @성균 수강생 과목 등록 검증
     * id가 검증되면 해당 subject 클래스 반환
     */
    public Subject subjectReturn(Integer subjectId) {
        Optional<Subject> subject = Optional.empty();
        try {
            subject = subjectParser.subjectEmptyCheckValid(subjectId);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return subject.get();
    }

    /**
     * @성균 과목 등록 검증
     */
    public boolean subjectMinCheck(int rSub, int eSub) {
        try {
            if (subjectParser.subjectMinCheck(rSub, eSub)) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return false;

    }


}