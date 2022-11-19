package fun.implementsstudio.mathforhim.service.impl;

import fun.implementsstudio.mathforhim.bo.GenerateQuestionBo;
import fun.implementsstudio.mathforhim.bo.GetQuestionsBo;
import fun.implementsstudio.mathforhim.dao.MathQuestionRepository;
import fun.implementsstudio.mathforhim.entity.MathQuestion;
import fun.implementsstudio.mathforhim.enums.QuestionEnums;
import fun.implementsstudio.mathforhim.service.IMathQuestionBank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * 一小步提升
 * 一瞬间快乐
 */
@Service
@Slf4j
public class MathQuestionBankImpl implements IMathQuestionBank {
    @Autowired
    private MathQuestionRepository mathQuestionRepository;
    @Override
    public Long addQuestion(MathQuestion mathQuestion) {
        MathQuestion save = mathQuestionRepository.save(mathQuestion);
        if (Objects.isNull(save)){
            log.error("save error:{}",mathQuestion.getQuestion());
            return null;
        }else{
            return save.getId();
        }
    }

    @Override
    public Boolean generateNewQuestions(GenerateQuestionBo generateQuestionBo) {

        Long maxNumber = generateQuestionBo.getMaxNumber();
        Long minNumber = generateQuestionBo.getMinNumber();
        String type = generateQuestionBo.getType();
        boolean distinct = generateQuestionBo.isDistinct();
        long size = generateQuestionBo.getSize();
        log.info("size:{}",size);
        log.info("distinct:{}",distinct);
        if (minNumber.equals(maxNumber)){
            maxNumber++;
        }
        Random random = new Random();
        LongStream longs = random.longs(size, minNumber.longValue(), maxNumber.longValue());
        List<MathQuestion> questions = new ArrayList<>();
        BinaryOperator<Long> longBinaryOperator = (x, y) -> {
            if (x==null||y==null){
                return y;
            }
            if (type.equals(QuestionEnums.ADD.getCode())) {
                log.info("x:{},y:{}",x,y);
                long l = x + y;
                questions.add(MathQuestion.builder().question(x + "+" + y + "=")
                                .maxLimit(x>y?x: y)
                        .answer(l + "").type(QuestionEnums.ADD.getCode()).build());
            } else if (type.equals(QuestionEnums.SUB.getCode())) {
                if (x > y || x == y) {
                    long l = x - y;
                    questions.add(MathQuestion.builder().question(x + "-" + y + "=")
                                    .maxLimit(x)
                            .answer(l + "").type(QuestionEnums.SUB.getCode()).build());
                } else {
                    long l = y - x;
                    questions.add(MathQuestion.builder().question(y + "-" + x + "=")
                                    .maxLimit(y)
                            .answer(l + "").type(QuestionEnums.SUB.getCode()).build());
                }
            } else if (type.equals(QuestionEnums.MUL.getCode())) {
                long l = x * y;
                questions.add(MathQuestion.builder().question(x + "×" + y + "=")
                                .maxLimit(x>y?x:y)
                        .answer(l + "").type(QuestionEnums.MUL.getCode()).build());
            } else if (type.equals(QuestionEnums.DIV.getCode())) {
                if (x > y || x == y) {
                    if (y == 0) {
                        return y;
                    }
                    double l = x / y.doubleValue();
                    questions.add(MathQuestion.builder().question(x + "÷" + y + "=")
                                    .maxLimit(x)
                            .answer(l + "").type(QuestionEnums.DIV.getCode()).build());
                } else {
                    if (x == 0) {
                        return y;
                    }
                    double l = y / x.doubleValue();
                    questions.add(MathQuestion.builder().question(y + "÷" + x + "=")
                                    .maxLimit(y)
                            .answer(l + "").type(QuestionEnums.DIV.getCode()).build());
                }
            }
            return y;
        };

        if (distinct){
             longs.distinct().boxed().reduce(longBinaryOperator).orElse(null);
        }else {
             longs.boxed().reduce(longBinaryOperator).orElse(null);
        }

        Iterable<MathQuestion> mathQuestions = mathQuestionRepository.saveAll(questions);
        return true;
    }

    @Override
    public List<MathQuestion> getQuestions(GetQuestionsBo getQuestionsBo) {
        List<MathQuestion> randQuestions = mathQuestionRepository.findByConditions(getQuestionsBo.getType(),
                getQuestionsBo.getMaxLimit(), getQuestionsBo.getSize());
        return randQuestions;
    }
}
