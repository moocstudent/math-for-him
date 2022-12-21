package fun.implementsstudio.mathforhim.service.impl;

import fun.implementsstudio.mathforhim.bo.AnswerRecordsBo;
import fun.implementsstudio.mathforhim.manager.MemberAnswerRecordsManager;
import fun.implementsstudio.mathforhim.service.IMemberAnswerRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MemberAnswerRecordsServiceImpl implements IMemberAnswerRecordsService {
    @Autowired
    private MemberAnswerRecordsManager memberAnswerRecordsManager;

    @Override
    public boolean answerRecords(AnswerRecordsBo answerRecordsBo, String memberId) {
        Long updateOrCreateCount = null;
        try {
            updateOrCreateCount = memberAnswerRecordsManager.updateOrCreateCount(answerRecordsBo.getQuestionId(),
                    memberId,
                    answerRecordsBo.isRight());
        } catch (Exception e) {
            return false;
        }
        if (!Objects.isNull(updateOrCreateCount)){
            return true;
        }
        return false;
    }
}
