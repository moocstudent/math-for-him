package fun.implementsstudio.mathforhim.controller;

import fun.implementsstudio.mathforhim.bo.DoLoginBo;
import fun.implementsstudio.mathforhim.bo.MemberAddBo;
import fun.implementsstudio.mathforhim.entity.MemberEntity;
import fun.implementsstudio.mathforhim.enums.QuestionEnums;
import fun.implementsstudio.mathforhim.result.BaseResult;
import fun.implementsstudio.mathforhim.service.IMathMembersService;
import fun.implementsstudio.mathforhim.util.SecurityUtilz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IMathMembersService mathMembersService;
    @Resource
    private AuthenticationManager authenticationManager;

    @PostMapping("/doLogin")
    public ModelAndView login(@Validated DoLoginBo loginBo, ModelAndView mv,HttpSession session) {
        log.info("doLogin");
        Map<String, String> map = Arrays.stream(QuestionEnums.values())
                //map 排序，index白加了
                .collect(Collectors.toMap(QuestionEnums::getCode, QuestionEnums::getDesc,
                        (k1, k2) -> k1, LinkedHashMap::new));
        mv.addObject("mathType", map);
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginBo.getLoginName(), loginBo.getLoginPassword());
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//            Object principal = authenticate.getPrincipal();
//            log.info("principal:{}",principal);

        } catch (AuthenticationException e) {
            log.error("authenticationException:{}",e.getMessage());
            mv.setViewName("theIndex");
            mv.addObject("member",null);
            return mv;
        }
        MemberEntity entity = mathMembersService.login(loginBo.getLoginName(), loginBo.getLoginPassword());
        log.info("login ok?:{}",entity!=null);
        if(!Objects.isNull(entity)){
            session.setAttribute("memberId",entity.getId());
            session.setAttribute("memberName",entity.getChildName());
        }
        mv.addObject("member",entity);
        mv.setViewName("redirect:/");
        return mv;
    }

    @ResponseBody
    @PostMapping(value = "/doReg")
    public BaseResult reg(@Validated MemberAddBo addBo, Model model){
        log.info("doReg...");
        MemberEntity register = mathMembersService.register(addBo);
        model.addAttribute("member",register);
        return BaseResult.builder()
                .code(register==null?0:1)
                .data(register).build();
    }

    /**
     * 打代码有时 如坐针毡 但有时 又无所事事
     * @param mv
     * @return
     */

    @GetMapping(value = "/doLogout")
    public ModelAndView reg(ModelAndView mv,HttpSession session){
        log.info("dologout...");
        SecurityUtilz.cleanUser();
        mv.setViewName("theIndex");
        mv.addObject("memberId",null);
        session.removeAttribute("memberId");
        session.removeAttribute("memberName");
        return mv;
    }

}
