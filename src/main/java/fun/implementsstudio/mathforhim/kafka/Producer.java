package fun.implementsstudio.mathforhim.kafka;

import com.alibaba.fastjson2.JSON;
import fun.implementsstudio.mathforhim.manager.MathQuestionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@EnableBinding(Source.class)
public class Producer {

    @Autowired
    private Source source;

    public void send(Payload payload) {
        source.output().send(MessageBuilder.withPayload(payload)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
                .build());
    }

}
