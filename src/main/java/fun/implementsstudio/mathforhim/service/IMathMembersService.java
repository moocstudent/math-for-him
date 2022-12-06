package fun.implementsstudio.mathforhim.service;

import fun.implementsstudio.mathforhim.bo.MemberAddBo;
import fun.implementsstudio.mathforhim.entity.MemberEntity;

public interface IMathMembersService {

    /**
     * 注册
     * @param memberAddBo
     * @return
     */
    Long register(MemberAddBo memberAddBo);

    /**
     * 就是登陆
     * @param loginName
     * @param loginPassword
     * @return
     */
    MemberEntity login(String loginName,String loginPassword);

    /**
     * 就像上次看到的，自己注销的用户，如果注册了会员会返还
     * @param loginName
     * @param loginPassword
     * @return
     */
    Boolean cancel(String loginName,String loginPassword);



}
