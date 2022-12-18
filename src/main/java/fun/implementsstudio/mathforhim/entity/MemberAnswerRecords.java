package fun.implementsstudio.mathforhim.entity;

import lombok.*;

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

    //用户答案
    private String memberAnswer;

    //原问题id
    private String questionId;

    //结果是否匹配
    private Integer isMatch;

    /**
     * 虽然出题人是在题目中绑定了 但说不准之后可以乱序哦
     */
    private String memberId;

}
