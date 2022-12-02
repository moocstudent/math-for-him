package fun.implementsstudio.mathforhim.service.impl;

import fun.implementsstudio.mathforhim.bo.GenerateQuestionBo;
import fun.implementsstudio.mathforhim.bo.GetQuestionsBo;
import fun.implementsstudio.mathforhim.dao.MathQuestionRepository;
import fun.implementsstudio.mathforhim.entity.MathQuestion;
import fun.implementsstudio.mathforhim.enums.QuestionEnums;
import fun.implementsstudio.mathforhim.service.IMathQuestionBank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (Objects.isNull(save)) {
            log.error("save error:{}", mathQuestion.getQuestion());
            return null;
        } else {
            return save.getId();
        }
    }

    @Override
    public Boolean generateNewQuestions(GenerateQuestionBo generateQuestionBo) {
        Long maxNumber = generateQuestionBo.getMaxNumber() + 1;
        Long minNumber = generateQuestionBo.getMinNumber();
        String type = generateQuestionBo.getType();
        boolean containsNegative = generateQuestionBo.isContainsNegative();
        boolean distinct = generateQuestionBo.isDistinct();
        long size = generateQuestionBo.getSize();
        log.info("size:{}", size);
        log.info("distinct:{}", distinct);
        if (minNumber.equals(maxNumber)) {
            maxNumber++;
        }
        Random random = new Random();
        //一般生成题目用到的数字量会是题目数量的3-4倍,除法可能遇到除不尽的结果所需数字更多些
        LongStream longs = null;
        LongStream backupLongs = null;
        if (type.equals(QuestionEnums.DIV.getCode()) || type.equals(QuestionEnums.MUL_DIV.getCode())) {
            longs = random.longs(size * 10, minNumber.longValue(), maxNumber.longValue());
            backupLongs = random.longs(size * 10, minNumber.longValue(), maxNumber.longValue());
        } else {
            longs = random.longs(size * 4, minNumber.longValue(), maxNumber.longValue());
            backupLongs = random.longs(size * 4, minNumber.longValue(), maxNumber.longValue());
        }
        List<MathQuestion> questions = new ArrayList<>();
        long[] backupLongsArr = backupLongs.toArray();
        BinaryOperator<Long> longBinaryOperator = (x, y) -> {
            if (x == null || y == null) {
                return y;
            }
            if (type.equals(QuestionEnums.ADD.getCode())) {
                return add(x, y, questions,containsNegative);
            } else if (type.equals(QuestionEnums.SUB.getCode())) {
                return sub(x, y, questions,containsNegative);
            } else if (type.equals(QuestionEnums.MUL.getCode())) {
                return mul(x, y, questions,containsNegative);
            } else if (type.equals(QuestionEnums.DIV.getCode())) {
                return div(x, y, questions,containsNegative);
            } else if (type.equals(QuestionEnums.ADD_SUB.getCode())) {
                return add_sub(x, y, questions, backupLongsArr,containsNegative);
            } else if (type.equals(QuestionEnums.MUL_DIV.getCode())) {
                return mul_div(x, y, questions, backupLongsArr,containsNegative);
            } else if (type.equals(QuestionEnums.ASMD_THREE.getCode())) {
                return asmd_three(x, y, questions, backupLongsArr,containsNegative);
            }
            return y;
        };
        if (distinct) {
            longs.distinct().boxed().reduce(longBinaryOperator).orElse(null);
        } else {
            longs.boxed().reduce(longBinaryOperator).orElse(null);
        }
        List<MathQuestion> matchSizeQs = questions.stream().limit(size).collect(Collectors.toList());
        Iterable<MathQuestion> mathQuestions = mathQuestionRepository.saveAll(matchSizeQs);
        return true;
    }

    @Override
    public List<MathQuestion> getQuestions(GetQuestionsBo getQuestionsBo) {
        List<MathQuestion> randQuestions = mathQuestionRepository.findByConditions(getQuestionsBo.getType(),
                getQuestionsBo.getMaxLimit(), getQuestionsBo.getSize());
        return randQuestions;
    }

    private Long add(Long x, Long y, List<MathQuestion> questions,boolean containsNegative) {
        log.info("x:{},y:{}", x, y);
        long l = x + y;
        if (!containsNegative && l<0){
            return y;
        }
        questions.add(MathQuestion.builder().question(x + "+" + y + "=")
                .maxLimit(x > y ? x : y)
                .answer(l + "").type(QuestionEnums.ADD.getCode()).build());
        return y;
    }

    private Long sub(Long x, Long y, List<MathQuestion> questions,boolean containsNegative) {
        if (x > y || x == y) {
            long l = x - y;
            if (!containsNegative && l<0){
                return y;
            }
            questions.add(MathQuestion.builder().question(x + "-" + y + "=")
                    .maxLimit(x)
                    .answer(l + "").type(QuestionEnums.SUB.getCode()).build());
        } else {
            long l = y - x;
            if (!containsNegative && l<0){
                return y;
            }
            questions.add(MathQuestion.builder().question(y + "-" + x + "=")
                    .maxLimit(y)
                    .answer(l + "").type(QuestionEnums.SUB.getCode()).build());
        }
        return y;
    }

    private Long add_sub(Long x, Long y, List<MathQuestion> questions, long[] backupLongsArr,boolean containsNegative) {
        long z = backupLongsArr[new Random().nextInt(backupLongsArr.length)];
        int rand = new Random().nextInt(10);
        if (rand % 2 == 0 && rand > 5) {
            long l = x + y - z;
            if (!containsNegative && l<0){
                return y;
            }
            questions.add(MathQuestion.builder().question(x + "+" + y + "-" + z + "=")
                    .maxLimit(x)
                    .answer(l + "").type(QuestionEnums.ADD_SUB.getCode()).build());
        } else if (rand % 2 == 0 && rand <= 5) {
            long l = x - y + z;
            if (!containsNegative && l<0){
                return y;
            }
            questions.add(MathQuestion.builder().question(x + "-" + y + "+" + z + "=")
                    .maxLimit(x)
                    .answer(l + "").type(QuestionEnums.ADD_SUB.getCode()).build());
        } else if (rand % 2 == 1 && rand > 5) {
            long l = x + y + z;
            if (!containsNegative && l<0){
                return y;
            }
            questions.add(MathQuestion.builder().question(x + "+" + y + "+" + z + "=")
                    .maxLimit(x)
                    .answer(l + "").type(QuestionEnums.ADD_SUB.getCode()).build());
        } else if (rand % 2 == 1 && rand <= 5) {
            long l = x - y - z;
            if (!containsNegative && l<0){
                return y;
            }
            questions.add(MathQuestion.builder().question(x + "-" + y + "-" + z + "=")
                    .maxLimit(x)
                    .answer(l + "").type(QuestionEnums.ADD_SUB.getCode()).build());
        }
        return y;
    }

    private Long mul(Long x, Long y, List<MathQuestion> questions,boolean containsNegative) {
        long l = x * y;
        if (!containsNegative && l<0){
            return y;
        }
        questions.add(MathQuestion.builder().question(x + "×" + y + "=")
                .maxLimit(x > y ? x : y)
                .answer(l + "").type(QuestionEnums.MUL.getCode()).build());
        return y;
    }

    private Long div(Long x, Long y, List<MathQuestion> questions,boolean containsNegative) {
        if (x > y || x == y) {
            if (y == 0) {
                return y;
            }
            Double l = x / y.doubleValue();
            if (!containsNegative && l<0){
                return y;
            }
            log.info("{}", l.toString());
            if (!l.isInfinite()) {
                if (l.toString().split("[.]")[1].length() < 4) {
                    Long trans = ifIntegerTransDouble(l);
                    MathQuestion question = MathQuestion.builder().question(x + "÷" + y + "=")
                            .maxLimit(x)
                            .answer(l + "").type(QuestionEnums.DIV.getCode()).build();
                    if (trans == null) {
                        question.setAnswer(l.toString());
                        questions.add(question);
                    } else {
                        question.setAnswer(trans.toString());
                        questions.add(question);
                    }
                }
            }
        } else {
            if (x == 0) {
                return y;
            }
            Double l = y / x.doubleValue();
            if (!containsNegative && l<0){
                return y;
            }
            if (!l.isInfinite()) {
                if (l.toString().split("[.]")[1].length() < 4) {
                    Long trans = ifIntegerTransDouble(l);
                    MathQuestion question = MathQuestion.builder().question(y + "÷" + x + "=")
                            .maxLimit(y)
                            .answer(l + "").type(QuestionEnums.DIV.getCode()).build();
                    if (trans == null) {
                        question.setAnswer(l.toString());
                        questions.add(question);
                    } else {
                        question.setAnswer(trans.toString());
                        questions.add(question);
                    }
                }
            }
        }
        return y;
    }

    private Long mul_div(Long x, Long y, List<MathQuestion> questions, long[] backupLongsArr,boolean containsNegative) {
        Long z = backupLongsArr[new Random().nextInt(backupLongsArr.length)];
        int rand = new Random().nextInt(10);
        log.info("mul_div rand:{}", rand);
        if (rand % 2 == 0 && rand > 5) {
            Double l = x * y / z.doubleValue();
            if (!containsNegative && l<0){
                return y;
            }
            if (!l.isInfinite()) {
                if (l.toString().split("[.]")[1].length() < 4) {
                    Long trans = ifIntegerTransDouble(l);
                    MathQuestion question = MathQuestion.builder().question(x + "×" + y + "÷" + z + "=")
                            .maxLimit(x)
                            .answer(l + "").type(QuestionEnums.MUL_DIV.getCode()).build();
                    if (trans == null) {
                        question.setAnswer(l.toString());
                        questions.add(question);
                    } else {
                        question.setAnswer(trans.toString());
                        questions.add(question);
                    }
                }
            }
        } else if (rand % 2 == 0 && rand <= 5) {
            Double l = x / y.doubleValue() * z;
            if (!containsNegative && l<0){
                return y;
            }
            if (!l.isInfinite()) {
                if (l.toString().split("[.]")[1].length() < 4) {
                    Long trans = ifIntegerTransDouble(l);
                    MathQuestion question = MathQuestion.builder().question(x + "÷" + y + "×" + z + "=")
                            .maxLimit(x)
                            .type(QuestionEnums.MUL_DIV.getCode()).build();
                    if (trans == null) {
                        question.setAnswer(l.toString());
                        questions.add(question);
                    } else {
                        question.setAnswer(trans.toString());
                        questions.add(question);
                    }
                }
            }
        } else if (rand % 2 == 1 && rand > 5) {
            long l = x * y * z;
            if (!containsNegative && l<0){
                return y;
            }
            questions.add(MathQuestion.builder().question(x + "×" + y + "×" + z + "=")
                    .maxLimit(x)
                    .answer(l + "").type(QuestionEnums.MUL_DIV.getCode()).build());
        } else if (rand % 2 == 1 && rand <= 5) {
            Double l = x / y.doubleValue() / z.doubleValue();
            if (!containsNegative && l<0){
                return y;
            }
            if (!l.isInfinite()) {
                if (l.toString().split("[.]")[1].length() < 4) {
                    Long trans = ifIntegerTransDouble(l);
                    MathQuestion question = MathQuestion.builder().question(x + "÷" + y + "÷" + z + "=")
                            .maxLimit(x)
                            .type(QuestionEnums.MUL_DIV.getCode()).build();
                    if (trans == null) {
                        question.setAnswer(l.toString());
                        questions.add(question);
                    } else {
                        question.setAnswer(trans.toString());
                        questions.add(question);
                    }
                }
            }
        }
        return y;
    }

    private Long asmd_three(Long x, Long y, List<MathQuestion> questions, long[] backupLongsArr,boolean containsNegative) {
        Long z = backupLongsArr[new Random().nextInt(backupLongsArr.length)];
        Long k = backupLongsArr[new Random().nextInt(backupLongsArr.length)];
        int rand = new Random().nextInt(20);
        if (rand % 2 == 0 && rand > 5 && rand < 10) {
            Double l = x * y / z.doubleValue() + k;
            if (!containsNegative && l<0){
                return y;
            }
            if (!l.isInfinite()) {
                if (l.toString().split("[.]")[1].length() < 4) {
                    Long trans = ifIntegerTransDouble(l);
                    MathQuestion question = MathQuestion.builder()
                            .question(x + "×" + y + "÷" + z + "+" + k + "=")
                            .maxLimit(x)
                            .answer(l + "").type(QuestionEnums.ASMD_THREE.getCode()).build();
                    if (trans == null) {
                        question.setAnswer(l.toString());
                        questions.add(question);
                    } else {
                        question.setAnswer(trans.toString());
                        questions.add(question);
                    }
                }
            }
        } else if (rand % 2 == 0 && rand <= 5) {
            Double l = x / y.doubleValue() - k * z;
            if (!containsNegative && l<0){
                return y;
            }
            if (!l.isInfinite()) {
                if (l.toString().split("[.]")[1].length() < 4) {
                    Long trans = ifIntegerTransDouble(l);
                    MathQuestion question = MathQuestion.builder()
                            .question(x + "÷" + y + "-" + k + "×" + z + "=")
                            .maxLimit(x)
                            .type(QuestionEnums.ASMD_THREE.getCode()).build();
                    if (trans == null) {
                        question.setAnswer(l.toString());
                        questions.add(question);
                    } else {
                        question.setAnswer(trans.toString());
                        questions.add(question);
                    }
                }
            }
        } else if (rand % 2 == 1 && rand > 5 && rand < 10) {
            Double l = x + y / z.doubleValue() - k;
            if (!containsNegative && l<0){
                return y;
            }
            if (!l.isInfinite()) {
                if (l.toString().split("[.]")[1].length() < 4) {
                    Long trans = ifIntegerTransDouble(l);
                    MathQuestion question = MathQuestion.builder()
                            .question(x + "+" + y + "÷" + z + "-" + k + "=")
                            .maxLimit(x)
                            .type(QuestionEnums.ASMD_THREE.getCode()).build();
                    if (trans == null) {
                        question.setAnswer(l.toString());
                        questions.add(question);
                    } else {
                        question.setAnswer(trans.toString());
                        questions.add(question);
                    }
                }
            }
        } else if (rand % 2 == 1 && rand <= 5) {
            Double l = x / y.doubleValue() * z + k;
            if (!containsNegative && l<0){
                return y;
            }
            if (!l.isInfinite()) {
                if (l.toString().split("[.]")[1].length() < 4) {
                    Long trans = ifIntegerTransDouble(l);
                    MathQuestion question = MathQuestion.builder()
                            .question(x + "÷" + y + "×" + z + "+" + k + "=")
                            .maxLimit(x)
                            .type(QuestionEnums.ASMD_THREE.getCode()).build();
                    if (trans == null) {
                        question.setAnswer(l.toString());
                        questions.add(question);
                    } else {
                        question.setAnswer(trans.toString());
                        questions.add(question);
                    }
                }
            }
        } else if (rand % 2 == 0 && rand >= 10 && rand < 15) {
            long l = x - y + z * k;
            if (!containsNegative && l<0){
                return y;
            }
            questions.add(MathQuestion.builder().question(x + "-" + y + "+" + z + "×" + k + "=")
                    .maxLimit(x)
                    .answer(l + "").type(QuestionEnums.ASMD_THREE.getCode()).build());
        }else if(rand%2==1 && rand>=10 && rand <15){
            Double l = x - y * z / k.doubleValue();
            if (!containsNegative && l<0){
                return y;
            }
            if (!l.isInfinite()) {
                if (l.toString().split("[.]")[1].length() < 4) {
                    Long trans = ifIntegerTransDouble(l);
                    MathQuestion question = MathQuestion.builder()
                            .question(x + "-" + y + "×" + z + "÷" + k + "=")
                            .maxLimit(x)
                            .type(QuestionEnums.ASMD_THREE.getCode()).build();
                    if (trans == null) {
                        question.setAnswer(l.toString());
                        questions.add(question);
                    } else {
                        question.setAnswer(trans.toString());
                        questions.add(question);
                    }
                }
            }
        }else if(rand%2==0 && rand>=15 && rand <20){
            long l = x + y - k * z;
            if (!containsNegative && l<0){
                return y;
            }
            questions.add(MathQuestion.builder().question(x + "+" + y + "-" + k + "×" + z + "=")
                    .maxLimit(x)
                    .answer(l + "").type(QuestionEnums.ASMD_THREE.getCode()).build());
        }else {
            long l = x * y - k + z;
            if (!containsNegative && l<0){
                return y;
            }
            questions.add(MathQuestion.builder().question(x + "×" + y + "-" + k + "+" + z + "=")
                    .maxLimit(x)
                    .answer(l + "").type(QuestionEnums.ASMD_THREE.getCode()).build());
        }
        return y;
    }

    private Long ifIntegerTransDouble(Double v) {
        if (v.toString().split("[.]")[1].length() == 1 && v.toString().split("[.]")[1].equals("0")) {
            return Long.parseLong(v.toString().split("[.]")[0]);
        }
        return null;
    }
//
//    public static void main(String[] args) {
//        System.out.println("0.1".split("[.]").length);
//    }
}
