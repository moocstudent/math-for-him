package fun.implementsstudio.mathforhim.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MemberAddBo {

    private String loginName;//登陆名
    private String loginPassword;//登陆密码

    private String childName;//儿童名字
    private Integer childAge;//儿童年龄
    private String childGender;//儿童性别

    private String parentName;//父母名称
    private String parentEmail;//用于发送做题报告

}
