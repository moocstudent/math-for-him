package fun.implementsstudio.mathforhim.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 枚举类真是学习英语的好帮手哈哈
 */
@Getter
@AllArgsConstructor
public enum QuestionEnums {
    ADD(0,"addition","加法"),
    SUB(1,"subtraction","减法"),
    MUL(2,"multiplication","乘法"),
    DIV(3,"division","除法"),
    ADD_SUB(4,"addWithSub","加减法混合"),
    MUL_DIV(5,"mulAndDiv","乘除法混合"),
    ASMD_THREE(6,"asmdThanThree","包含加减乘除中至少三种混合的");

    private Integer index;
    private String code;
    private String desc;

    public static String getDescByCode(String code){
        QuestionEnums[] values = QuestionEnums.values();
        List<QuestionEnums> collect = Arrays.stream(values).filter(v -> v.getCode().equals(code))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)){
            return "";
        }
        return collect.get(0).getDesc();
    }
}
