package fun.implementsstudio.mathforhim.service;

import fun.implementsstudio.mathforhim.bo.GenerateQuestionBo;
import fun.implementsstudio.mathforhim.bo.GetQuestionsBo;
import fun.implementsstudio.mathforhim.entity.MathQuestion;

import java.util.List;

/**
 * 按照前端传入的数字区间寻找对应的题目，并且根据加减乘除进行分类
 */
public interface IMathQuestionBank {
    /**
     * Add one math question 自动知道了百度翻译app里有语法分析并有纠错能力，就再也不用有道翻译了
     * @param mathQuestion
     * @return
     */
    Long addQuestion(MathQuestion mathQuestion);

    /**
     * 根据数字区间生成新题
     * @param generateQuestionBo
     * @return
     */
    Boolean generateNewQuestions(GenerateQuestionBo generateQuestionBo,String memberId);

    /**
     * 获取试题，默认加法，限制大小数值到100，获取10道题
     * @param getQuestionsBo
     * @return
     */
    List<MathQuestion> getQuestions(GetQuestionsBo getQuestionsBo);
    List<MathQuestion> getQuestions(GetQuestionsBo getQuestionsBo,String memberId);
}
