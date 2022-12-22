package fun.implementsstudio.mathforhim.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GenerateQuestionBo {
    @NotNull(message = "min number must not be null")
    private Long minNumber;
    @NotNull(message = "max number must not be null")
    private Long maxNumber;
    @NotNull(message = "type must not be null")
    private String type;
//    @NotNull(message = "distinct must not be null")
    //is generated number distinct
    private boolean distinct;
    //generate number's size
    @NotNull(message = "size must not be null")
    private long size;
    //是否允许生成结果为负数
    private boolean containsNegative;
}
