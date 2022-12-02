package fun.implementsstudio.mathforhim.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GenerateQuestionBo {
    private Long minNumber;
    private Long maxNumber;
    private String type;
    //is generated number distinct
    private boolean distinct;
    //generate number's size
    private long size;
    //是否允许生成结果为负数
    private boolean containsNegative;
}
