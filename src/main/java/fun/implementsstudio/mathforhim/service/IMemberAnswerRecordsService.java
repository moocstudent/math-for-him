package fun.implementsstudio.mathforhim.service;

import fun.implementsstudio.mathforhim.bo.AnswerRecordsBo;
import fun.implementsstudio.mathforhim.bo.AnswerRecordsSearchBo;
import fun.implementsstudio.mathforhim.entity.MemberAnswerRecords;
import fun.implementsstudio.mathforhim.vo.MemberAnswerRecordsEchartsVo;

import java.util.List;

public interface IMemberAnswerRecordsService {

    //答题记录！
    boolean answerRecords(AnswerRecordsBo answerRecordsBo,String memberId);

    List<MemberAnswerRecords> searchRecords(AnswerRecordsSearchBo searchBo, String memberId);

    MemberAnswerRecordsEchartsVo recordsToEchartsDatas(AnswerRecordsSearchBo searchBo,String memberId);
}
