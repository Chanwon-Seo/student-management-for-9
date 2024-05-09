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

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ScoreService {
    private final DBManager dbManager;
    private final StudentParser studentParser;
    private final SubjectParser subjectParser;
    private final ScoreParser scoreParser;

    public ScoreService(DBManager dbManager) {
        this.dbManager = dbManager;
        this.studentParser = new StudentParser(dbManager);
        this.subjectParser = new SubjectParser(dbManager);
        this.scoreParser = new ScoreParser(dbManager);
    }

    /**
     * @찬원 수강생 점수 등록 메서드
     */
    public void scoreCreateV2(Integer subjectIdInput, Integer studentIdInput, Integer roundInput, Integer scoreInput) {
        //FIXME 불필요 코드 완료
        Optional<Subject> findSubjectData;
        Optional<Student> findStudentData;
        try {
            findSubjectData = subjectParser.subjectEmptyCheckValid(subjectIdInput);
            findStudentData = studentParser.studentEmptyCheckValidV2(studentIdInput);
            studentParser.studentAndSubjectCheckValid(findSubjectData.get().getSubjectId(), findStudentData.get());
            scoreParser.scoreRoundInputOneToTenCheckValid(roundInput);
            scoreParser.scoreInputZeroToOneHundredCheckValid(scoreInput);

            scoreParser.scoreDuplicatedCheckValidv2(
                    findSubjectData.get().getSubjectId(),
                    findStudentData.get().getStudentId(),
                    roundInput
            );
        } catch (NullPointerException | IllegalArgumentException | IllegalStateException e) {
            throw new RuntimeException(e.getMessage());
        }


        Map<Integer, Integer> roundMap = new LinkedHashMap<>();
        roundMap.put(roundInput, scoreInput);

        Score score = new Score(findSubjectData.get().getSubjectId(),
                studentIdInput,
                roundMap,
                checkLevelType(findSubjectData.get().getSubjectType().getSubjectTypeValue(), scoreInput)
        );
        System.out.printf("%d / %d / %d회차에 %d점수 ( %s )등급이 저장 되었습니다.\n\n",
                findSubjectData.get().getSubjectId(),
                findStudentData.get().getStudentId(),
                roundInput,
                scoreInput,
                score.getLevelType()
        );

        dbManager.saveScore(score);
    }

    /**
     * @찬원 필수 또는 선택에 따른 등급 산정
     */
    private LevelType checkLevelType(String findSubjectData, Integer scoreInput) {
        LevelType levelType;
        if ("필수".equals(findSubjectData)) {
            levelType = LevelType.checkRequiredLevelType(scoreInput);
        } else {
            levelType = LevelType.checkElectiveLevelType(scoreInput);
        }
        return levelType;
    }

    /**
     * @차도범 수강생 아이디로 삭제
     */
    public void deleteScoreByStudentId(int studentId) {
        try {
            boolean b = dbManager.deleteScoreByStudentId(studentId);
            if (b) System.out.println("점수를 삭제했습니다..");
            else System.out.println("점수를 삭제하지 못햇습니다.");
            System.out.println();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }
        System.out.print("\n\n");
    }

    //FIXME 메서드명 완료

    /**
     * @세미 과목의 [회차: 등급] 전체조회 (필수 - 과목의 회차별 등급 조회)
     * print ex:
     * "1회차 D"
     * "2회차 A"
     * "3회차 N"
     */
    public void loadAllScore(Integer studentId, Integer subjectId) {

        try {
            // FIXME-Exception : 수강생>과목 가지고 있지 않을때 =>완료
            studentParser.studentAndSubjectCheckValid(subjectId, dbManager.findByStudents().get(studentId));
            scoreParser.studentScoreNullCheck(studentId, subjectId);

            Map<Integer, Integer> score = findScores(studentId, subjectId);
            if (score == null) throw new NullPointerException("점수가 없습니다.\n");

            System.out.println("학생고유번호: " + studentId + "  과목번호: " + subjectId);

            //등급계산
            SubjectType levelType = dbManager.findOneBySubject(subjectId).get().getSubjectType();
            //FIXME 초기화 =>완료
            LevelType tempLevelType;

            for (int i = 0; i < score.size(); i++) {
                System.out.print(score.keySet().toArray()[i] + " 회차: ");
                // 등급계산
                if (levelType == SubjectType.REQUIRED)
                    tempLevelType = LevelType.checkRequiredLevelType((Integer) score.values().toArray()[i]);
                else
                    tempLevelType = LevelType.checkElectiveLevelType((Integer) score.values().toArray()[i]);
                System.out.println(tempLevelType);
            }

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    //FIXME 메서드명 완료

    /**
     * @세미 회차 점수 수정 (필수 - 점수수정)
     * print ex: "2회차 : 78점 수정완료!"
     */
    public void updateScore(Integer studentId, Integer subjectId, Integer roundInput, Integer scoreInput) {

        try {
            Map<Integer, Integer> score = findScores(studentId, subjectId);

            if (score == null) throw new NullPointerException("점수가 없습니다.\n");
            // FIXME-Exception : 수강생>과목>점수 수정할 회차가 등록되어있지 않음 =>완료
            scoreParser.scoreUpdateCheckValid(subjectId, studentId, roundInput); //등록 된 회차인가?
            scoreParser.scoreInputZeroToOneHundredCheckValid(scoreInput); //점수 범위측정

            score.put(roundInput, scoreInput);
            System.out.println("[*수정완료*]\t" + roundInput + " 회차 : " + scoreInput + "점\n");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

    //FIXME 메서드명 =>완료

    /**
     * @세미 과목별 평균등급 조회 (추가 - 점수관리)
     * print ex: "JAVA과목의 평균은 B 입니다."
     */
    public void loadAvgScore(Integer studentId, Integer subjectId) {
        try {
            Map<Integer, Integer> score = findScores(studentId, subjectId);

            // FIXME-Exception : 수강생>과목>점수 가 없음 =>완료
            if (score == null) throw new NullPointerException("점수가 없습니다.\n");
            scoreParser.studentScoreNullCheck(studentId, subjectId); //이중체크

            double sum = 0;
            double avg = 0;
            for (int i = 0; i < score.size(); i++) {
                sum += score.get(i + 1);
            }
            avg = sum / score.size();
            Optional<Subject> subject = dbManager.findOneBySubject(subjectId);
            System.out.println("- " + subject.get().getSubjectName() + "과목의 평균은 " + avg + "입니다.\n");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    //FIXME 메서드명 =>완료

    /**
     * @세미 특정상태 수강생들의 필수 과목 평균 등급 (추가 - 점수관리)
     * print ex:
     * "박세미의 필수 과목 평균 등급: A"
     * "서찬원의 필수 과목 평균 등급: B"
     * "차도범의 필수 과목 평균 등급: C"
     */
    public void loadStudentStateOfRequiredSubject(int state) {

        try {
            StudentStateType stateType = switch (state) {
                case 1 -> StudentStateType.GREEN;
                case 2 -> StudentStateType.RED;
                case 3 -> StudentStateType.YELLOW;
                default -> null;
            };

            // FIXME-Exception : 상태 1,2,3 외의 값이 입력됨 =>완료
            studentParser.studentTypeCheckValid(stateType);

            List<Student> students = dbManager.findByStudents(); //basic
            List<Student> studentList = new LinkedList<>(); //가공
            for (Student student : students) {
                if (student.getStudentStateType() == stateType) {
                    studentList.add(student);
                }
            } //특정상태 수강생들 뽑음 (studentList)
            boolean isAnyScoreInquiry = false;

            double count = 0;
            double sum = 0;
            int avg = 0;
            //FIXME 초기화 =>완료
            LevelType resultLevel;
            for (Student student : studentList) {
                sum = 0;
                count = 0;
                for (Integer sub : student.getSubjectSet()) {
                    boolean isRequired = dbManager.findSubjectTypebySubjectId(sub);
                    if (isRequired) {
                        double tempValue = avgScoreCaculator(student.getStudentId(), sub);
                        if (tempValue != 0) {
                            sum += tempValue; //한 과목 평균을 넣기
                            count++; // 필수 과목수(점수가 있는)
                        }
                    }
                }
                avg = (int) (sum / count); //각 과목당 평균들의 전체평균을 계산
                resultLevel = LevelType.checkRequiredLevelType(avg); //평균점수를 등급화

                if (avg != 0) {
                    System.out.println(" - " + student.getStudentName() + "의 필수 과목 평균 등급: " + resultLevel + "\n");
                    isAnyScoreInquiry = true;
                }
            }
            if (!isAnyScoreInquiry) System.out.println("조회할 점수가 없습니다.\n");

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }


    /*=========================== Utils ===========================  */

    //FIXME 메서드명 완료

    /**
     * @세미 수강생 과목번호 받아 score 리스트 return
     * input  : student Id , subject Id
     * output : Score Map<회차,점수> (회차와 점수 맵)
     */
    public Map<Integer, Integer> findScores(Integer studentId, Integer subjectId) {

        try {
            // FIXME-Exception : 수강생 없음 =>완료
            Optional<Subject> findSubjectData = subjectParser.subjectEmptyCheckValid(subjectId);
            studentParser.studentEmptyCheckValidV2(studentId); //존재하는 수강생인가?

            // FIXME-Exception : 수강생>과목>점수가 없음 =>완료
            scoreParser.studentScoreNullCheck(studentId, subjectId);

            List<Score> score = dbManager.findByScores();

            for (Score s : score) {
                if (s.getStudentId().equals(studentId) && s.getSubjectId().equals(subjectId)) {
                    Map<Integer, Integer> temp = s.getScoreMap();
                    // FIXME-Exception : 수강생>과목>점수 없음
                    if (temp.size() <= 0) {
                        System.out.println("해당 과목의 점수가 없습니다.\n\n");
                        break;
                    }
                    return temp;
                }
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //FIXME 메서드명 완료

    /**
     * @세미 한 과목 모든 회차의 평균 계산해서 return
     * input  : student Id , subject Id
     * output : avg (한개의 과목 평균)
     */
    public double avgScoreCaculator(Integer studentId, Integer subjectId) {
        try {
            Map<Integer, Integer> score = findScores(studentId, subjectId);

            // FIXME-Exception : 수강생>과목>점수가 없음 =>완료
            if (score == null) return 0;
            //scoreParser.studentScoreNullCheck(studentId,subjectId);

            double sum = 0, avg = 0;
            for (int i = 0; i < score.size(); i++) {
                sum += (double) score.get(i + 1);
            }
            avg = sum / score.size();
            return avg;
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }


}
