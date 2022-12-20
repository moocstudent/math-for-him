package fun.implementsstudio.mathforhim.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaSendResultHandler implements ProducerListener {

    @Override
    public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
        String topic = recordMetadata.topic();
        int partition = recordMetadata.partition();
        long offset = recordMetadata.offset();
        log.info("消息发送成功:topic is:{},partition is:{},offset is:{}", topic, partition, offset);
    }
 
    @Override
    public void onError(ProducerRecord producerRecord, Exception exception) {
        log.error("消息发送失败 error msg:{}", exception.getMessage());
    }
}