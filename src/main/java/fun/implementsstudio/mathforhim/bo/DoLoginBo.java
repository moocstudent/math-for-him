package fun.implementsstudio.mathforhim.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DoLoginBo {
    @NotNull(message = "登陆名不能为空")
    private String loginName;//登陆名
    @NotNull(message = "登陆密码不能为空")
    private String loginPassword;//登陆密码
}
