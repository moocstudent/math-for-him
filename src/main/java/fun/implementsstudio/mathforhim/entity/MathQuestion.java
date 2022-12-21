package fun.implementsstudio.mathforhim.entity;

import fun.implementsstudio.mathforhim.enums.QuestionEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "math_question_bank")
public class MathQuestion extends BaseEntity implements Serializable {

    /**
     * 在一些题型中，应当设定只是如1+1= 2+2=几的题目，而字符串也仅是这样的题目的返回，也就是说题目是数字生成后返回
     */
    private String question;
    /**
     * 答案是字符串，因为经过出提器出完题后，就应该同时计算了结果，并将结果写入数据库，题目也一样
     */
    private String answer;

    /**
     * 该题目最大限制，用于搜题
     */
    private long maxLimit;

    /**
     * {@link QuestionEnums}
     */
    private String type;

    /**
     * 会员id，即绑定哪些题是哪些用户出的 之后可以乱序 比如会员1做会员2的题目
     */
    private String memberId;


}
