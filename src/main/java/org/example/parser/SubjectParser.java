package org.example.parser;

import org.example.db.DBManager;
import org.example.domain.Subject;

import java.util.HashSet;

public class SubjectParser {
    private final DBManager dbManager;

    final int MIN_REQUIRED_SUBJECTS = 3;
    final int MIN_ELECTIVE_SUBJECTS = 2;

    public SubjectParser(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * @return
     * @찬원 과목 정보 조회
     * throw 조회된 수강생 정보가 없을 경우
     */
    public Subject subjectEmptyCheckValid(Integer subjectIdInput) {
        try {
            return dbManager.findOneBySubject(subjectIdInput);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @return
     * @성균 과목 이름 조회
     * throw 조회된 과목이 없을 경우
//     */
    public boolean subjectIsEmptyCheck(Integer subjectId) throws Exception {
        for (Subject subject : dbManager.findBySubjects()) {
            if (subjectId == subject.getSubjectId()) {
                return true;
            }
        }
        throw new Exception("올바른 과목 ID가 아닙니다.");
    }

    /**
     * @return
     * @성균 subjectId 중복 조회
     * throw 중복인 경우
    //     */
    public boolean subjectIdDuplicationCheck(HashSet<Integer> dup, Integer subjectId) throws Exception {
        if(!dup.contains(subjectId)){
            return true;
        }
        throw new Exception("이미 등록된 과목입니다.");
    }


    /**
     * @return
     * @성균 필수 ,선택 과목 검사
     * throw 부족한 필수, 선택 과목
    //     */
    public boolean subjectMinCheck(int rSub, int eSub) throws Exception {

        if (rSub >= MIN_REQUIRED_SUBJECTS && eSub >= MIN_ELECTIVE_SUBJECTS) {
            return true;
        }
        else if(rSub<MIN_REQUIRED_SUBJECTS && eSub<MIN_ELECTIVE_SUBJECTS){
            throw new Exception("필수과목이 " + (MIN_REQUIRED_SUBJECTS-rSub) + "과목, 선택과목이 " + (MIN_ELECTIVE_SUBJECTS-eSub) + "과목이 부족해 수강생이 등록되지 않습니다.");

        }
        else if(rSub<MIN_REQUIRED_SUBJECTS){
            throw new Exception("필수과목이 " + (MIN_REQUIRED_SUBJECTS-rSub) + "과목 부족해 수강생이 등록되지 않습니다.");
        }
        else {
            throw new Exception("선택과목이 " + (MIN_ELECTIVE_SUBJECTS - eSub) + "과목 부족해 수강생이 등록되지 않습니다.");
        }

    }


}
