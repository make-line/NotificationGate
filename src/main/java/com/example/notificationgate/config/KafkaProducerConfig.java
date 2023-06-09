package com.example.notificationgate.config;

import com.example.notificationgate.model.dto.Message;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
class KafkaProducerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

//  @Bean
//  public Map<String, Object> producerConfigs() {
//    Map<String, Object> props = new HashMap<>();
//    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
//      bootstrapServers);
//    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
//      StringSerializer.class);
//    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//      StringSerializer.class);
//    props.put("key.serializer", Class.forName("org.apache.kafka.common.serialization.StringSerializer")); props.put("value.serializer", Class.forName("org.apache.kafka.common.serialization.StringSerializer"));
//    return props;
//  }
  @Bean
  public KafkaTemplate<String, Message> messageKafkaTemplate() {
    return new KafkaTemplate<>(messageProducerFactory());
  }

  @Bean
  public ProducerFactory<String, Message> messageProducerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
      bootstrapServers);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            JsonSerializer.class);
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            JsonSerializer.class);
    return new DefaultKafkaProducerFactory<>(configProps);
  }

}