package fun.implementsstudio.mathforhim.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AnswerRecordsSearchBo {
    //如果questionId不为空，则查一个题目的答题记录，否则查该会员的答题记录集体
    //整体转换为图表统计
    private String questionId;

    private String type;
}
