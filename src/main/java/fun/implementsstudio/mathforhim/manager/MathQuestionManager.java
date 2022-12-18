package fun.implementsstudio.mathforhim.manager;

import fun.implementsstudio.mathforhim.dao.MathQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 冬日，踏着晶莹的雪。
 * 那是我眼里最美的样子。
 */
@Component
public class MathQuestionManager {
    @Autowired
    private MathQuestionRepository mathQuestionRepository;
    /**
     * bank full limit
     * todo limit by vip level
     */
    @Value("${math.bank_full_limit}")
    private Long bankFullLimit;

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
