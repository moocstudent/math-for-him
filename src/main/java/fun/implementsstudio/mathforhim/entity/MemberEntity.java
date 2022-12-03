package fun.implementsstudio.mathforhim.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
//@Table(name = "members")
public class MemberEntity extends BaseEntity implements Serializable {

    private String loginName;//登陆名
    private String loginPassword;//登陆密码

    private String childName;//儿童名字
    private Integer childAge;//儿童年龄
    private String childGender;//儿童性别

    private String parentName;//父母名称
    private String parentEmail;//用于发送做题报告

    private Long vipLevel;//vip等级 初始NULL 即不是会员

}
