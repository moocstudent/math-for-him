package fun.implementsstudio.mathforhim;

import fun.implementsstudio.mathforhim.bo.GenerateQuestionBo;
import fun.implementsstudio.mathforhim.bo.GetQuestionsBo;
import fun.implementsstudio.mathforhim.entity.MathQuestion;
import fun.implementsstudio.mathforhim.enums.QuestionEnums;
import fun.implementsstudio.mathforhim.result.BaseResult;
import fun.implementsstudio.mathforhim.service.IMathQuestionBank;
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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    @PostMapping("/generateQ")
    @ResponseBody
    public BaseResult generateNewQuestions(GenerateQuestionBo generateQuestionBo) {
        log.info("开始生成数学题:{}", generateQuestionBo);
        Boolean success = mathQuestionBank.generateNewQuestions(generateQuestionBo);
        return BaseResult.builder().code(1).data(mathQuestionBank.generateNewQuestions(generateQuestionBo))
                .msg(success ? "生成了" +
                        QuestionEnums.getDescByCode(generateQuestionBo.getType())
                        + "类型的、介于【" + generateQuestionBo.getMinNumber() + "与" + generateQuestionBo.getMaxNumber() + "】之间数学题"
                        + generateQuestionBo.getSize() + "道，结果" + (generateQuestionBo.isContainsNegative() ?
                        "允许" : "不允许") + "出现负数。" : "").build();
    }

    @GetMapping("/getQ")
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
    @GetMapping("/getQList")
    public String getQuestionsView(Model model, GetQuestionsBo getQuestionsBo) {
        List<MathQuestion> questions = mathQuestionBank.getQuestions(getQuestionsBo);
        model.addAttribute("questions", questions);
        return "math";
    }

    @GetMapping(value = {"/", ""})
    public String index(Model model) {
        Map<String, String> map = Arrays.stream(QuestionEnums.values())
                //map 排序，index白加了
                .collect(Collectors.toMap(QuestionEnums::getCode, QuestionEnums::getDesc,
                        (k1, k2) -> k1, LinkedHashMap::new));
        model.addAttribute("mathType", map);
        return "index";
    }


}
