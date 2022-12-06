package fun.implementsstudio.mathforhim.service.impl;

import fun.implementsstudio.mathforhim.bo.MemberAddBo;
import fun.implementsstudio.mathforhim.entity.MemberEntity;
import fun.implementsstudio.mathforhim.service.IMathMembersService;
import org.springframework.stereotype.Service;

@Service
public class MathMembersServiceImpl implements IMathMembersService {
    @Override
    public Long register(MemberAddBo memberAddBo) {

        return null;
    }

    @Override
    public MemberEntity login(String loginName, String loginPassword) {
        return null;
    }

    @Override
    public Boolean cancel(String loginName, String loginPassword) {
        return null;
    }
}
