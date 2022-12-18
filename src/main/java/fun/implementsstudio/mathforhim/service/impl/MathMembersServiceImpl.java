package fun.implementsstudio.mathforhim.service.impl;

import fun.implementsstudio.mathforhim.bo.MemberAddBo;
import fun.implementsstudio.mathforhim.constants.MathConstants;
import fun.implementsstudio.mathforhim.dao.MathMembersRepository;
import fun.implementsstudio.mathforhim.entity.MemberEntity;
import fun.implementsstudio.mathforhim.service.IMathMembersService;
import fun.implementsstudio.mathforhim.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class MathMembersServiceImpl implements IMathMembersService {
    @Autowired
    private MathMembersRepository mathMembersRepository;

    @Value("${math.invitation_mode}")
    private boolean invitationMode;

    @Override
    public MemberEntity register(MemberAddBo memberAddBo) {
        if(invitationMode){
            String invitationCode = memberAddBo.getInvitationCode();
            if (StringUtils.hasText(invitationCode)){
                if(MathConstants.invitationCodeList.contains(invitationCode)){
                    MathConstants.invitationCodeList.remove(invitationCode);
                }else{
                    throw new RuntimeException("不存在的激活码");
                }
            }else{
                throw new RuntimeException("请输入激活码");
            }
        }
        MemberEntity byLoginName = mathMembersRepository.findByLoginName(memberAddBo.getLoginName());
        if (!Objects.isNull(byLoginName)){
            throw new RuntimeException("该注册账号已经存在，请尝试其它登陆名称！");
        }
        MemberEntity memberEntity = BeanUtil.getClass(MemberEntity.class, memberAddBo).get();
        return mathMembersRepository.save(memberEntity);
    }

    @Override
    public MemberEntity login(String loginName, String loginPassword) {
        return mathMembersRepository.findByLoginNameAndLoginPassword(loginName, loginPassword);
    }

    @Override
    public String cancel(String loginName, String loginPassword) {
        //todo 判定会员等级 返回会员点数，用于本网站其它消费服务
        //返回自定义字符串，再用于激活账户 需创建冷冻账户表 对应激活码
        return null;
    }

    @Override
    public String active(String activationCode) {
        return null;
    }
}
