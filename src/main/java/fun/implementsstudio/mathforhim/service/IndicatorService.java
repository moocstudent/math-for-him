package fun.implementsstudio.mathforhim.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface IndicatorService {

    void processMessage(ConsumerRecord<Integer,String> record);

    void sendMessage(String topic,String data);

}
