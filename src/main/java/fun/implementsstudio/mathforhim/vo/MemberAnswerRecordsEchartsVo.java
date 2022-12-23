package fun.implementsstudio.mathforhim.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MemberAnswerRecordsEchartsVo {

    private List<String> questionsData;
    //对应questionsData的对的count统计
    private List<Integer> rightCountArr;
    //对应questionsData的错的count统计
    private List<Integer> wrongCountArr;

    //通过获取rightcountarr中最大值 +该值到下一个0结尾的边界
    private Integer rightCountMax;
    //通过获取wrongcountarr中最大值 +该值到下一个0结尾的边界
    private Integer wrongCountMax;
}
