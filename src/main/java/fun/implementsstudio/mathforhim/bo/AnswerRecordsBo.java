package fun.implementsstudio.mathforhim.bo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 较量 往往就在刹那之间
 * 以低的姿态 讲最高的话
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AnswerRecordsBo {
    //问题id
    private String questionId;
    //是否答对
    private Boolean isRight;
    //题目类型
    private String type;
}
