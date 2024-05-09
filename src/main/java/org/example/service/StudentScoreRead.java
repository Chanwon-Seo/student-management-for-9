package org.example.service;

import org.example.db.DBManager;
import org.example.domain.Score;
import org.example.domain.Student;
import org.example.domain.Subject;
import org.example.domain.enums.LevelType;
import org.example.domain.enums.StudentStateType;
import org.example.domain.enums.SubjectType;
import org.example.parser.ScoreParser;
import org.example.parser.StudentParser;
import org.example.parser.SubjectParser;

import java.util.*;


public class StudentScoreRead {

    private final DBManager dbManager;
    private final StudentParser studentParser;
    private final SubjectParser subjectParser;
    private final ScoreParser scoreParser;

    public StudentScoreRead(DBManager dbManager) {
        this.dbManager = dbManager;
        this.studentParser = new StudentParser(dbManager);
        this.subjectParser = new SubjectParser(dbManager);
        this.scoreParser = new ScoreParser(dbManager);
    }

    //FIXME 메서드명
    // 과목의 [회차: 등급] 전체조회 (필수 - 과목의 회차별 등급 조회)
    /**
     * @세미 과목의 [회차: 등급] 전체조회 (필수 - 과목의 회차별 등급 조회)
     * print ex:
     * "1회차 D"
     * "2회차 A"
     * "3회차 N"
     */
    public void loadScore(Integer studentId, Integer subjectId) {
        Map<Integer, Integer> score = findScoresByStudentIdANDSubjectId(studentId, subjectId);

        if (score == null) { //TEMPEXCEPTION
            System.out.println("해당 과목의 점수가 존재하지 않습니다");
            return;
        }

        System.out.println("학생고유번호: " + studentId + "  과목번호: " + subjectId);

        //등급계산
        SubjectType levelType = dbManager.findOneBySubject(subjectId).get().getSubjectType();

        //FIXME 초기화
        LevelType tempLevelType = LevelType.A;

        for (int i = 0; i < score.size(); i++) {
            System.out.print(score.keySet().toArray()[i] + " 회차: ");
            // 등급계산
            if (levelType == SubjectType.REQUIRED)
                tempLevelType = LevelType.checkRequiredLevelType((Integer) score.values().toArray()[i]);
            else
                tempLevelType = LevelType.checkElectiveLevelType((Integer) score.values().toArray()[i]);
            System.out.println(tempLevelType);
        }
    }

    //FIXME 메서드명
    // 회차 점수 수정 (필수 - 점수수정)
    /**
     * @세미 회차 점수 수정 (필수 - 점수수정)
     * print ex: "2회차 : 78점 수정완료!"
     */
    public void updateScore(Integer studentId, Integer subjectId, Integer roundInput, Integer scoreInput) {

        Map<Integer, Integer> score = findScoresByStudentIdANDSubjectId(studentId, subjectId);

        if (score == null || !score.containsKey(roundInput)) { //TEMPEXCEPTION
            System.out.println("해당 과목의 회차가 존재하지 않습니다");
            return;
        }

        scoreParser.scoreInputZeroToOneHundredCheckValid(scoreInput); //점수 범위측정

        score.put(roundInput, scoreInput);
        System.out.println(roundInput + " 회차 : " + scoreInput + "점 수정완료!");
    }

    //FIXME 메서드명
    //과목별 평균등급 조회 (추가 - 점수관리)
    /**
     * @세미 과목별 평균등급 조회 (추가 - 점수관리)
     * print ex: "JAVA과목의 평균은 B 입니다."
     */
    public void loadAvgScore(Integer studentId, Integer subjectId) {
        Map<Integer, Integer> score = findScoresByStudentIdANDSubjectId(studentId, subjectId);
        if (score == null) { //TEMP EXCEPTION
            System.out.println("해당 과목의 점수가 존재하지 않습니다");
            return;
        }

        double sum = 0;
        double avg = 0;

        for (int i = 0; i < score.size(); i++) {
            sum += score.get(i + 1);
        }
        avg = sum / score.size();
        Optional<Subject> subject = dbManager.findOneBySubject(subjectId);
        System.out.println(subject.get().getSubjectName() + "과목의 평균은 " + avg + "입니다.");

    }

    //FIXME 메서드명
    /**
     * @세미 특정상태 수강생들의 필수 과목 평균 등급 (추가 - 점수관리)
     * print ex:
     * "박세미의 필수 과목 평균 등급: A"
     * "서찬원의 필수 과목 평균 등급: B"
     * "차도범의 필수 과목 평균 등급: C"
     */
    public void LoadStudentStateOfRequiredSubject(int state) {

        StudentStateType stateType = switch (state) {
            case 1 -> StudentStateType.GREEN;
            case 2 -> StudentStateType.RED;
            case 3 -> StudentStateType.YELLOW;
            default -> null;
        };

        if(stateType==null) { //매개변수 TEMP EXCEPTION
            System.out.println("잘못된 상태를 입력하셨습니다.");
        }


        List<Student> students = dbManager.findByStudents(); //basic
        List<Student> studentList = new LinkedList<>(); //가공
        for (Student student : students) {
            if (student.getStudentStateType() == stateType) {
                studentList.add(student);
            }
        } //특정상태 수강생들 뽑음 (studentList)

        double count = 0;
        double sum = 0;
        int avg = 0;
        //FIXME 초기화
        LevelType resultLevel = LevelType.A;
        for (Student student : studentList) {
            sum = 0;
            count = 0;
            for (Integer sub : student.getSubjectSet()) {
                boolean isRequired = dbManager.FindSubjectTypebySubjectId(sub);
                if (isRequired) {
                    if (loadAvgScoreRequired(student.getStudentId(), sub) != 0) {
                        sum += loadAvgScoreRequired(student.getStudentId(), sub); //해당 과목 평균을 넣기
                        count++;
                    }
                }
            }
            avg = (int) (sum / count); //각 과목당 평균들의 전체평균을 계산
            resultLevel = LevelType.checkRequiredLevelType(avg); //평균점수를 등급화

            if (avg != 0) System.out.println(student.getStudentName() + "의 필수 과목 평균 등급: " + resultLevel);
        }

    }




    /*=========================== Utils ===========================  */

    //FIXME 메서드명
    // 수강생 과목번호 받아 score 리스트 return
    /**
     * @세미 수강생 과목번호 받아 score 리스트 return
     * input  : student Id , subject Id
     * output : Score Map<회차,점수> (회차와 점수 맵)
     */
    public Map<Integer, Integer> findScoresByStudentIdANDSubjectId(Integer studentId, Integer subjectId) {

        Optional<Subject> findSubjectData = subjectParser.subjectEmptyCheckValid(subjectId);
        studentParser.studentEmptyCheckValidV2(studentId);

        if (dbManager.findByScores().isEmpty() || dbManager.findByScores() == null) return null;

        List<Score> score = dbManager.findByScores();

        for (Score s : score) {
            if (s.getStudentId().equals(studentId) && s.getSubjectId().equals(subjectId)) {
                Map<Integer, Integer> temp = s.getScoreMap();
                if (temp.size() <= 0) { //TEMP EXCEPTION
                    System.out.println("해당 과목의 점수가 없습니다");
                    break;
                }
                return temp;
            }
        }
        return null;
    }

    //FIXME 메서드명
    /**
     * @세미 모든 회차의 평균 계산
     * input  : student Id , subject Id
     * output : avg (해당과목 평균)
     */
    public double loadAvgScoreRequired(Integer studentId, Integer subjectId) {
        Map<Integer, Integer> score = findScoresByStudentIdANDSubjectId(studentId, subjectId);
        if (score == null || score.isEmpty()) {
            return 0;
        }

        double sum = 0, avg = 0;

        for (int i = 0; i < score.size(); i++) {
            sum += (double) score.get(i + 1);
        }
        avg = sum / score.size();
        return avg;
    }

}
