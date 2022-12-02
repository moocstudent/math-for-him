package fun.implementsstudio.mathforhim.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetQuestionsBo {

    private String type;
    //the max of the question number （君洋说的不可说不可说可能这么大)
    private long maxLimit = 99999999999999L;
    //get question's size
    private long size = 10;
    //contains negative answer 0:positive and negative，1:positive，2:negative
    private Integer answerNegative = 0;

}
