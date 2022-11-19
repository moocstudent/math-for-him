//package fun.implementsstudio.mathforhim.controller;
//
//import fun.implementsstudio.mathforhim.bo.GenerateQuestionBo;
//import fun.implementsstudio.mathforhim.result.BaseResult;
//import fun.implementsstudio.mathforhim.service.IMathQuestionBank;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//@RestController("/v0/question")
//public class QuestionController {
//    @Autowired
//    private IMathQuestionBank mathQuestionBank;
//    @GetMapping("/name")
//    public BaseResult test(){
//        return BaseResult.builder().msg("zhangjunyang").build();
//    }
//    @GetMapping("/generateQ")
//    public BaseResult generateNewQuestions(GenerateQuestionBo generateQuestionBo){
//        return BaseResult.builder().code(1).data(mathQuestionBank.generateNewQuestions(generateQuestionBo)).build();
//    }
//}
