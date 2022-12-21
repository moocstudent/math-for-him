package fun.implementsstudio.mathforhim.controller;


import fun.implementsstudio.mathforhim.bo.AnswerRecordsBo;
import fun.implementsstudio.mathforhim.bo.AnswerRecordsSearchBo;
import fun.implementsstudio.mathforhim.result.BaseResult;
import fun.implementsstudio.mathforhim.service.IMemberAnswerRecordsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * 也许总有感觉简历合适的
 */
@Slf4j
@RestController
@RequestMapping("/records")
public class MemberRecordsController {
    @Autowired
    private IMemberAnswerRecordsService memberAnswerRecordsService;

    @GetMapping("/makeRecords")
    public BaseResult makeRecords(AnswerRecordsBo answerRecordsBo, HttpSession session){
        Object memberId = session.getAttribute("memberId");
        if (Objects.isNull(memberId)){
            //用户未登陆则不能生成题目
            return BaseResult.builder().code(0).build();
        }
        boolean records = false;
        try {
            records = memberAnswerRecordsService.answerRecords(answerRecordsBo, String.valueOf(memberId));
            if (records){
                return BaseResult.builder().code(1).msg("recorded").build();
            }else{
                return BaseResult.builder().code(0).msg("failed").build();
            }
        } catch (Exception e) {
            return BaseResult.builder().code(0).msg("exception").build();
        }
    }

    @GetMapping("/searchRecords")
    public BaseResult searchRecords(AnswerRecordsSearchBo searchBo,HttpSession session){
        Object memberId = session.getAttribute("memberId");
        if (Objects.isNull(memberId)){
            //用户未登陆则不能生成题目
            return BaseResult.builder().code(0).build();
        }
        Object records = null;
        try {
            records = memberAnswerRecordsService.searchRecords(searchBo, String.valueOf(memberId));
            if (Objects.isNull(records)){
                return BaseResult.builder().code(0).data(null).msg("found null").build();
            }
            return BaseResult.builder().code(1).data(records).msg("get records").build();
        } catch (Exception e) {
            return BaseResult.builder().code(0).msg("exception").build();
        }
    }
}
