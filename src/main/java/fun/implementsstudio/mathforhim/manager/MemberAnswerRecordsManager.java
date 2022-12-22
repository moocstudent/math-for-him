package fun.implementsstudio.mathforhim.manager;

import fun.implementsstudio.mathforhim.dao.MemberAnswerRecordsRepository;
import fun.implementsstudio.mathforhim.entity.MemberAnswerRecords;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 一时轻松过后 面对的又是许多的困难
 * 所以任何时候 不能太过松懈
 */
@Slf4j
@Component
public class MemberAnswerRecordsManager {
    @Autowired
    private MemberAnswerRecordsRepository memberAnswerRecordsRepository;

    /**
     * update or create answer records
     * @param questionId
     * @param memberId
     * @param isRight
     * @return
     */
    public Long updateOrCreateCount(String questionId,String memberId,boolean isRight,String type){
        log.info("isRight:{}",isRight);
        MemberAnswerRecords answerRecords
                = memberAnswerRecordsRepository.findByQuestionIdAndMemberId(questionId, memberId);
        if (Objects.isNull(answerRecords)){
            MemberAnswerRecords records = MemberAnswerRecords.builder()
                    .questionId(questionId)
                    .memberId(memberId)
                    /**
                     * 人生犹如下棋，落棋不悔
                     * 职业发展犹如逆水行舟，不进则退
                     */
                    .type(type)
                    .build();
            if (isRight){
                log.info("set right count 1");
                records.setRightCount(1L);
            }else{
                records.setWrongCount(1L);
            }
            MemberAnswerRecords save = memberAnswerRecordsRepository.save(records);
            return save.getId();
        }
        if (isRight){
            log.info("set right count +1");
            answerRecords.setRightCount((answerRecords.getRightCount()==null?0:answerRecords.getRightCount())+1);
        }else{
            answerRecords.setWrongCount((answerRecords.getWrongCount()==null?0:answerRecords.getWrongCount())+1);
        }
        MemberAnswerRecords save = memberAnswerRecordsRepository.save(answerRecords);
        return save.getId();
    }

    /**
     * 较量2
     * 有时 决定下的事情 直接去做 效果往往比什么都好
     * @param questionId
     * @param memberId
     * @return
     */
    public Object findRecordsByConditions(String questionId,String memberId){
        if (StringUtils.isBlank(questionId)){
            return memberAnswerRecordsRepository.findByMemberId(memberId, Sort.by(Sort.Direction.DESC,"createTime"));
        }
        return memberAnswerRecordsRepository.findByQuestionIdAndMemberId(questionId, memberId);
    }

    public List<MemberAnswerRecords> findRecordsByConditions2(String questionId, String type, String memberId){
       return memberAnswerRecordsRepository.findByQIdTypeAndMemberIdNative(questionId,type,memberId);
    }




    /**
     * 有些人不会讲话会吃多大亏
     * 有些人太会讲话会吃多大亏
     */
}
