package fun.implementsstudio.mathforhim.service;

import fun.implementsstudio.mathforhim.bo.AnswerRecordsBo;
import fun.implementsstudio.mathforhim.bo.AnswerRecordsSearchBo;

public interface IMemberAnswerRecordsService {

    //答题记录！
    boolean answerRecords(AnswerRecordsBo answerRecordsBo,String memberId);

    Object searchRecords(AnswerRecordsSearchBo searchBo,String memberId);

}
