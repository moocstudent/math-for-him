package fun.implementsstudio.mathforhim.service;

import fun.implementsstudio.mathforhim.dao.MathMembersRepository;
import fun.implementsstudio.mathforhim.entity.MemberEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("dbUserLoginService")
public class DbUserLoginService implements UserDetailsService {
    @Autowired
    private MathMembersRepository mathMembersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loaduserByusername invoke");
        MemberEntity byLoginName = mathMembersRepository.findByLoginName(username);
        if (byLoginName==null){
            return null;
//            throw new UsernameNotFoundException("用户不存在");
        }
        //设置用户全新
        List<GrantedAuthority> dbUser = AuthorityUtils.commaSeparatedStringToAuthorityList("dbUser");
        //返回的依旧是security user
        return new User(username,new BCryptPasswordEncoder().encode(byLoginName.getLoginPassword())
        ,dbUser);
    }
}
