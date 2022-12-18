package fun.implementsstudio.mathforhim.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * 买的雀巢咖啡即使还买了伴侣，但是味道总比公司公用咖啡机里的咖啡苦一点。
 */
@Slf4j
@Component
public class SecurityUtilz {

    /**
     * 可千万别使用
     * 整理用户登陆在security中配置了sessionManagement比什么都好用些～
     * @return
     */
    //获取用户详情
    public static Object getUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        log.info("securityUtilz authentication:{}",authentication);
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getPrincipal();
         }
        return null;
    }

    //判定用户是否登陆
    public static boolean isUserLogin(HttpSession session){
        return getUserDetails()!=null;
//        return session.getAttribute("userId") !=null;
    }

    //清除SecurityContex(真正使得用户退出)
    public static void cleanUser(){
        SecurityContextHolder.clearContext();
    }

}
