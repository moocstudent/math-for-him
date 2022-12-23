package fun.implementsstudio.mathforhim.service.impl;

import fun.implementsstudio.mathforhim.bo.AnswerRecordsBo;
import fun.implementsstudio.mathforhim.bo.AnswerRecordsSearchBo;
import fun.implementsstudio.mathforhim.entity.MemberAnswerRecords;
import fun.implementsstudio.mathforhim.manager.MemberAnswerRecordsManager;
import fun.implementsstudio.mathforhim.service.IMemberAnswerRecordsService;
import fun.implementsstudio.mathforhim.vo.MemberAnswerRecordsEchartsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class MemberAnswerRecordsServiceImpl implements IMemberAnswerRecordsService {
    @Autowired
    private MemberAnswerRecordsManager memberAnswerRecordsManager;

    @Override
    public boolean answerRecords(AnswerRecordsBo answerRecordsBo, String memberId) {
        log.info("answer records save begin");
        Long updateOrCreateCount = null;
        try {
            updateOrCreateCount
                    = memberAnswerRecordsManager.updateOrCreateCount(answerRecordsBo.getQuestionId(),
                    memberId,
                    answerRecordsBo.getIsRight(),
                    answerRecordsBo.getType());
        } catch (Exception e) {
            return false;
        }
        if (!Objects.isNull(updateOrCreateCount)){
            return true;
        }
        return false;
    }

    @Override
    public List<MemberAnswerRecords> searchRecords(AnswerRecordsSearchBo searchBo, String memberId) {
        return memberAnswerRecordsManager.findRecordsByConditions2(searchBo.getQuestionId(),
                searchBo.getType(),
                memberId);
    }

    @Override
    public MemberAnswerRecordsEchartsVo recordsToEchartsDatas(AnswerRecordsSearchBo searchBo, String memberId) {
        MemberAnswerRecordsEchartsVo echartsDataVo = memberAnswerRecordsManager.findEchartsDataVo(searchBo.getQuestionId(), searchBo.getType(), memberId);
        log.info("echartsDataVo:{}",echartsDataVo);
        return echartsDataVo;
    }
}
