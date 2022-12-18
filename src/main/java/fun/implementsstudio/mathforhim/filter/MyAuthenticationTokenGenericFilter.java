//package fun.implementsstudio.mathforhim.filter;
//
//import fun.implementsstudio.mathforhim.dao.MathMembersRepository;
//import fun.implementsstudio.mathforhim.entity.MemberEntity;
//import fun.implementsstudio.mathforhim.http.MyServletRequestWrapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.Optional;
//
//@Slf4j
//public class MyAuthenticationTokenGenericFilter extends OncePerRequestFilter {
//    /**
//     * TODO 过滤元数据，后续自己实现
//     */
//    /**
//     * 用户认证服务接口
//     */
//    @Autowired
//    private MathMembersRepository mathMembersRepository;
//
//    @Override
//    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        log.info("~~~~~~~~~用户合法性校验~~~~~~~~~");
//        // 白名单直接验证通过
//        HttpSession session = request.getSession(false);
//        Object memberId = null;
//        try {
//            memberId = session.getAttribute("memberId");
//        } catch (Exception e) {
//            chain.doFilter(request, response);
//            return;
//        }
//        log.info("memberId:{}",memberId);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info("auth:{}",authentication);
//        if (memberId!=null && (authentication == null || (authentication instanceof AnonymousAuthenticationToken))) {
//            Optional<MemberEntity> byId = mathMembersRepository.findById((Long) memberId);
//            if (byId.isPresent()){
//                MemberEntity memberEntity = byId.get();
//                log.info("userid not null and auth is null");
//                UsernamePasswordAuthenticationToken authenticationToken
//                        = new UsernamePasswordAuthenticationToken(memberEntity.getLoginName(),memberEntity.getLoginPassword());
//                // 2、重新SecurityContextHolder.getContext().setAuthentication(authentication)存储用户认证信息
//                authenticationToken.setDetails(memberEntity);
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }else if (memberId!=null && (authentication instanceof UsernamePasswordAuthenticationToken)){
//            if (!(request instanceof MyServletRequestWrapper)) {
//                request = new MyServletRequestWrapper(request);
//            }
//            chain.doFilter(request, response);
//            return;
//        }else{
//            if (!(request instanceof MyServletRequestWrapper)) {
//                request = new MyServletRequestWrapper(request);
//            }
//            chain.doFilter(request, response);
//            return;
//        }
//        chain.doFilter(request, response);
//    }
//
//
//}