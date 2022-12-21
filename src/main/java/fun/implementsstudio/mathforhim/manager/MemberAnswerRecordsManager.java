package fun.implementsstudio.mathforhim.manager;

import fun.implementsstudio.mathforhim.dao.MemberAnswerRecordsRepository;
import fun.implementsstudio.mathforhim.entity.MemberAnswerRecords;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class MemberAnswerRecordsManager {

    private MemberAnswerRecordsRepository memberAnswerRecordsRepository;

    /**
     * update or create answer records
     * @param questionId
     * @param memberId
     * @param isRight
     * @return
     */
    public Long updateOrCreateCount(String questionId,String memberId,boolean isRight){
        MemberAnswerRecords answerRecords
                = memberAnswerRecordsRepository.findByQuestionIdAndMemberId(questionId, memberId);
        if (Objects.isNull(answerRecords)){
            MemberAnswerRecords records = MemberAnswerRecords.builder()
                    .questionId(questionId)
                    .memberId(memberId)
                    .build();
            if (isRight){
                records.setRightCount(1L);
            }else{
                records.setWrongCount(1L);
            }
            MemberAnswerRecords save = memberAnswerRecordsRepository.save(records);
            return save.getId();
        }
        if (isRight){
            answerRecords.setRightCount(answerRecords.getRightCount()+1);
        }else{
            answerRecords.setWrongCount(answerRecords.getWrongCount()+1);
        }
        MemberAnswerRecords save = memberAnswerRecordsRepository.save(answerRecords);
        return save.getId();
    }
}
