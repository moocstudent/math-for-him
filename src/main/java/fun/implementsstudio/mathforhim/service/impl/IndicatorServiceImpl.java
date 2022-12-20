package fun.implementsstudio.mathforhim.service.impl;

import fun.implementsstudio.mathforhim.constants.MathConstants;
import fun.implementsstudio.mathforhim.manager.MathQuestionManager;
import fun.implementsstudio.mathforhim.service.IMathQuestionBank;
import fun.implementsstudio.mathforhim.service.IndicatorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class IndicatorServiceImpl implements IndicatorService {
    private final KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    public IndicatorServiceImpl(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    @Autowired
    private IMathQuestionBank mathQuestionBank;
    @Autowired
    private MathQuestionManager mathQuestionManager;

    @KafkaListener(topics = "#{kafkaTopicName}", groupId = "#{topicGroupId}")
    @Override
    public void processMessage(ConsumerRecord<Integer, String> record) {
        log.info("kafka processMessage start");
        String topic = record.topic();
        String value = record.value();
        log.info("processMessage, topic={},msg={}", topic, value);
        if (topic.startsWith(MathConstants.MATH_GEN_PREFIX)) {
            mathQuestionManager.saveOneByOneUsingKafka(value);
        }
//        mathQuestionBank.generateNewQuestions()

        log.info("kafka processMessage end");
    }

    @Override
    public void sendMessage(String topic, String data) {
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic, data);
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("kafka sendMessage error, ex={},topic={},data={}", ex, topic, data);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                log.info("kafka sendMessage success topic={},data={}", topic, data);
            }
        });
        log.info("kafka sendMessage end");
    }
}
