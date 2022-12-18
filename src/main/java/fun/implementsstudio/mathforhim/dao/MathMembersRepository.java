package fun.implementsstudio.mathforhim.dao;

import fun.implementsstudio.mathforhim.entity.MemberEntity;

public interface MathMembersRepository extends BaseRepository<MemberEntity,Long>{
    /**
     * 根据登陆用户名密码查找member
     * @param loginName
     * @param loginPassword
     * @return
     */
    MemberEntity findByLoginNameAndLoginPassword(String loginName,String loginPassword);

    /**
     * find by loginName
     * @param loginName
     * @return
     */
    MemberEntity findByLoginName(String loginName);

}
