package fun.implementsstudio.mathforhim.manager;

import com.alibaba.fastjson2.JSON;
import fun.implementsstudio.mathforhim.dao.MathQuestionRepository;
import fun.implementsstudio.mathforhim.entity.MathQuestion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * 冬日，踏着晶莹的雪。
 * 那是我眼里最美的样子。
 *
 * 欲成大树，莫与草争；将军有剑，不斩苍蝇。
 */
@Component
@Slf4j
public class MathQuestionManager {
    @Autowired
    private MathQuestionRepository mathQuestionRepository;
    /**
     * bank full limit
     * todo limit by vip level
     */
    @Value("${math.bank_full_limit}")
    private Long bankFullLimit;

    public Future<Integer> saveQuestionListAsync(List<MathQuestion> theOwnerMathQuestions){
        int saveSize = 0;
        for (MathQuestion theOwnerMathQuestion : theOwnerMathQuestions) {
           saveSize+= this.saveOneByOneUsingKafka(JSON.toJSONString(theOwnerMathQuestion));
        }
        return CompletableFuture.completedFuture(saveSize);
    }

    public Long saveOneByOneUsingKafka(String questionJson){
        log.info("save one by one by kafka");
        return 1L;
    }
    /**
     * justify generate size to generate
     * @param memberId
     * @param generateSize
     * @return
     */
    public Long justifyGeneSize(String memberId,Long generateSize){
        Long countByMemberId = mathQuestionRepository.countByMemberId(memberId);
        if (generateSize+countByMemberId>=bankFullLimit){
            return bankFullLimit-countByMemberId;
        }else{
           return generateSize;
        }
    }

    /**
     * is bank full ?
     * @param memberId
     * @param generateSize
     * @return
     */
    public boolean isBankFull(String memberId,Long generateSize){
        Long countByMemberId = mathQuestionRepository.countByMemberId(memberId);
        if (generateSize+countByMemberId>=bankFullLimit){
            return true;
        }else{
            return false;
        }
    }

    /**
     * bank size for now
     * @param memberId
     * @return
     */
    public Long bankSizeForNow(String memberId){
       return mathQuestionRepository.countByMemberId(memberId);
    }
}
