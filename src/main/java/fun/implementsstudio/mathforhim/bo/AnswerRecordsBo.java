package fun.implementsstudio.mathforhim.bo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AnswerRecordsBo {
    //问题id
    private String questionId;
    //是否答对
    private boolean isRight;

}
