package fun.implementsstudio.mathforhim;

import fun.implementsstudio.mathforhim.bo.GenerateQuestionBo;
import fun.implementsstudio.mathforhim.bo.GetQuestionsBo;
import fun.implementsstudio.mathforhim.entity.MathQuestion;
import fun.implementsstudio.mathforhim.enums.QuestionEnums;
import fun.implementsstudio.mathforhim.manager.MathQuestionManager;
import fun.implementsstudio.mathforhim.result.BaseResult;
import fun.implementsstudio.mathforhim.service.IMathQuestionBank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Why I need this @SpringBootApplication
 * 多活动头就不会痛
 */
@Slf4j
@SpringBootApplication
@ComponentScan("fun.implementsstudio.mathforhim.*")
@RequestMapping("")
@Controller
@EnableJpaRepositories
@Validated
@EnableRedisHttpSession
public class MathForHimApplication {

    public static void main(String[] args) {
        SpringApplication.run(MathForHimApplication.class, args);
    }

    @Autowired
    private IMathQuestionBank mathQuestionBank;
    @Autowired
    private MathQuestionManager mathQuestionManager;

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
        Long geneSizeJustice = mathQuestionManager.justifyGeneSize(String.valueOf(memberId), generateQuestionBo.getSize());
        if (geneSizeJustice==0){
            //todo vip with size table relation and notification
            Long sizeForNow = mathQuestionManager.bankSizeForNow(String.valueOf(memberId));
            return BaseResult.builder().code(0).data(false)
                    .msg("生成题目失败了,因为你现在的题库数量:"+
                            sizeForNow+"已不能再创建这么多题了!请升级vip或者删除部分题目～")
                    .build();
            //todo 页面 location = 对应题目数量展示页面以及升级vip
        }
        generateQuestionBo.setSize(geneSizeJustice);
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
    public BaseResult getQuestions(GetQuestionsBo getQuestionsBo,HttpSession session) {
        //整来整去 还是session好使
        Object memberId = session.getAttribute("memberId");
        if (Objects.isNull(memberId)){
            //用户未登陆则不能生成题目
            return BaseResult.builder().code(0).build();
        }
        return BaseResult.builder()
                .data(mathQuestionBank.getQuestions(getQuestionsBo,String.valueOf(memberId)))
                .code(1).build();
    }

    /**
     * 有目标 才能达成 更好地达成
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
