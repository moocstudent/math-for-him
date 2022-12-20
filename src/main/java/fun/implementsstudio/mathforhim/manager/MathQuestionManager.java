package fun.implementsstudio.mathforhim.manager;

import com.alibaba.fastjson2.JSON;
import fun.implementsstudio.mathforhim.dao.MathQuestionRepository;
import fun.implementsstudio.mathforhim.entity.MathQuestion;
import fun.implementsstudio.mathforhim.kafka.Payload;
import fun.implementsstudio.mathforhim.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

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

    @Autowired
    private Producer producer;

    public Future<Integer> saveQuestionListByKafka(List<MathQuestion> questionList) {
        int sendCount = 0;
        for (MathQuestion mathQuestion : questionList) {
            try {
                producer.send(Payload.builder()
                        .type("question")
                        .dataJson(JSON.toJSONString(mathQuestion))
                        .build());
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendCount++;
        }
        return CompletableFuture.completedFuture(sendCount);
    }

    /**
     * justify generate size to generate
     *
     * @param memberId
     * @param generateSize
     * @return
     */
    public Long justifyGeneSize(String memberId, Long generateSize) {
        Long countByMemberId = mathQuestionRepository.countByMemberId(memberId);
        if (generateSize + countByMemberId >= bankFullLimit) {
            return bankFullLimit - countByMemberId;
        } else {
            return generateSize;
        }
    }

    /**
     * is bank full ?
     *
     * @param memberId
     * @param generateSize
     * @return
     */
    public boolean isBankFull(String memberId, Long generateSize) {
        Long countByMemberId = mathQuestionRepository.countByMemberId(memberId);
        if (generateSize + countByMemberId >= bankFullLimit) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * bank size for now
     *
     * @param memberId
     * @return
     */
    public Long bankSizeForNow(String memberId) {
        return mathQuestionRepository.countByMemberId(memberId);
    }
}
