package fun.implementsstudio.mathforhim.entity;

import lombok.*;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实际上记录用户的答案并记录错题集等～
 * 等弄完注册的验证码就应该做这个啦
 *
 * 男孩容易受视觉上的影响，所以会沉迷手机啊x片啊或者电子游戏啊
 * 但是实际上要培养专注的思想和沉稳的大脑要靠阅读
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "member_answer_records")
public class MemberAnswerRecords extends BaseEntity implements Serializable {

    //原问题id
    private String questionId;
    //统计错误次数
    private Long wrongCount;
    //统计回答正确次数
    private Long rightCount;
    /**
     * 会员id
     */
    private String memberId;

    //题目类型 需要获取
    private String type;

}