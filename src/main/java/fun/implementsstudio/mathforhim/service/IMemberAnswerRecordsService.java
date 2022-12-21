package fun.implementsstudio.mathforhim.service;

import fun.implementsstudio.mathforhim.bo.AnswerRecordsBo;

public interface IMemberAnswerRecordsService {

    //答题记录！
    boolean answerRecords(AnswerRecordsBo answerRecordsBo,String memberId);
}
