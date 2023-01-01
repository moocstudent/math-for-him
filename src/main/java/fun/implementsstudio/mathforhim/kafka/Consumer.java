package fun.implementsstudio.mathforhim.kafka;

import com.alibaba.fastjson2.JSONObject;
import fun.implementsstudio.mathforhim.dao.MathQuestionRepository;
import fun.implementsstudio.mathforhim.entity.MathQuestion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@EnableBinding(Sink.class)
@Component
public class Consumer {

    @Autowired
    private MathQuestionRepository mathQuestionRepository;


    @StreamListener(Sink.INPUT)
    public void consume(Message<Payload> message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("partition [{}], receive [{}]", partition, message);
        Payload payload = message.getPayload();
        if (payload.getType().equals("question")){
            MathQuestion save = mathQuestionRepository.save(JSONObject.parseObject(
                    payload.getDataJson(), MathQuestion.class));
            log.info("kafka save one question id:{}",save.getId());
        }
    }

}
