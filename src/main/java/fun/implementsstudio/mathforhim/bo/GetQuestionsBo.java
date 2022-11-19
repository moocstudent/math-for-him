package fun.implementsstudio.mathforhim.bo;

import fun.implementsstudio.mathforhim.enums.QuestionEnums;
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
}
