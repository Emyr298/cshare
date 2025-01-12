package com.cshare.search.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.cshare.search.dto.ImageToTextResult;

import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

@Configuration
public class KafkaConfig {
    private static final String IMAGE_TO_TEXT_TOPIC = "content.image-to-text.done";

    @Value("${cshare.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${cshare.kafka.group-id}")
    private String groupId;

    @Bean
    public ReceiverOptions<String, ImageToTextResult> imageToTextReceiverOptions() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.cshare.search.dto.ImageToTextResult");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.cshare.search.dto.ImageToTextResult");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return ReceiverOptions.<String, ImageToTextResult>create(props);
    }

    @Bean
    public Flux<ReceiverRecord<String, ImageToTextResult>> imageToTextReceiver(
            ReceiverOptions<String, ImageToTextResult> receiverOptions) {
        return KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(IMAGE_TO_TEXT_TOPIC)))
                .receive();
    }
}
