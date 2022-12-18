package fun.implementsstudio.mathforhim.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MemberAddBo {
    @NotNull(message = "登陆名不能为空")
    private String loginName;//登陆名
    @NotNull(message = "登陆密码不能为空")
    private String loginPassword;//登陆密码
    @NotNull(message = "儿童名字不能为空")
    private String childName;//儿童名字
    @NotNull(message = "儿童年龄不能为空")
    @Min(value = 1,message = "最小为1岁哦")
    @Max(value = 12,message = "最大为12岁哦")
    private Integer childAge;//儿童年龄
    @NotNull(message = "儿童性别不能为空")
    private String childGender;//儿童性别
    @NotNull(message = "父母姓名不能为空")
    private String parentName;//父母名称
    @NotNull(message = "父母邮箱不能为空哦，用于接收做题报告")
    @Email
    private String parentEmail;//用于发送做题报告
    //邀请码
    private String invitationCode;

}
