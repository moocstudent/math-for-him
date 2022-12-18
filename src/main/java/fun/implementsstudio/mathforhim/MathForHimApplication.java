package fun.implementsstudio.mathforhim;

import fun.implementsstudio.mathforhim.bo.GenerateQuestionBo;
import fun.implementsstudio.mathforhim.bo.GetQuestionsBo;
import fun.implementsstudio.mathforhim.entity.MathQuestion;
import fun.implementsstudio.mathforhim.enums.QuestionEnums;
import fun.implementsstudio.mathforhim.result.BaseResult;
import fun.implementsstudio.mathforhim.service.IMathQuestionBank;
import fun.implementsstudio.mathforhim.util.SecurityUtilz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Why I need this @SpringBootApplication
 */
@Slf4j
@SpringBootApplication
@ComponentScan("fun.implementsstudio.mathforhim.*")
@RequestMapping("")
@Controller
@EnableJpaRepositories
public class MathForHimApplication {

    public static void main(String[] args) {
        SpringApplication.run(MathForHimApplication.class, args);
    }

    @Autowired
    private IMathQuestionBank mathQuestionBank;

    @GetMapping("/name")
    @ResponseBody
    public BaseResult test() {
        return BaseResult.builder().msg("zhangjunyang").build();
    }

    /**
     * creation
     * 创建一些题出来
     */
    @PostMapping("/fun/generateQ")
    @ResponseBody
    public BaseResult generateNewQuestions(GenerateQuestionBo generateQuestionBo, HttpSession session) {
        //整来整去 还是session好使
        Object memberId = session.getAttribute("memberId");
        log.info("开始生成数学题:{}", generateQuestionBo);
        if (Objects.isNull(memberId)){
            //用户未登陆则不能生成题目
            return BaseResult.builder().code(0).build();
        }
        Boolean success = mathQuestionBank.generateNewQuestions(generateQuestionBo,String.valueOf(memberId));
        return BaseResult.builder().code(1).data(success)
                .msg(success ? "生成了" +
                        QuestionEnums.getDescByCode(generateQuestionBo.getType())
                        + "类型的、介于【" + generateQuestionBo.getMinNumber() + "与" + generateQuestionBo.getMaxNumber() + "】之间数学题"
                        + generateQuestionBo.getSize() + "道，结果" + (generateQuestionBo.isContainsNegative() ?
                        "允许" : "不允许") + "出现负数。" : "").build();
    }

    @GetMapping("/fun/getQ")
    @ResponseBody
    public BaseResult getQuestions(GetQuestionsBo getQuestionsBo) {
        return BaseResult.builder().data(mathQuestionBank.getQuestions(getQuestionsBo)).code(1).build();
    }

    /**
     * 点外卖吃烧鸡 再来一个选择题
     *
     * @param model
     * @param getQuestionsBo
     * @return
     */
    @GetMapping("/fun/getQList")
    public String getQuestionsView(Model model, GetQuestionsBo getQuestionsBo,
                                   HttpSession session) {
        Object memberId = session.getAttribute("memberId");
        if (Objects.isNull(memberId)){
            return "redirect:/";
        }
        log.info("memberId in getQList:{}",memberId);
        List<MathQuestion> questions = mathQuestionBank.getQuestions(getQuestionsBo,String.valueOf(memberId));
        model.addAttribute("questions", questions);
        return "math";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping(value = {"/","/index",""})
    public ModelAndView index(ModelAndView mv) {
        Map<String, String> map = Arrays.stream(QuestionEnums.values())
                //map 排序，index白加了
                .collect(Collectors.toMap(QuestionEnums::getCode, QuestionEnums::getDesc,
                        (k1, k2) -> k1, LinkedHashMap::new));
        mv.addObject("mathType", map);
        mv.setViewName("theIndex");
        return mv;
    }


}
