package test.api.email.email.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import test.api.email.email.services.EmailService;

import java.util.Arrays;
import java.util.Properties;

@Component
public class KafkaConfig {
    private final String topic;
    private final Properties props;
    EmailService emailService;

    public KafkaConfig(EmailService emailService2, @Value("${spring.kfuser}") String username,
                       @Value("${spring.kfpass}") String password) {
        emailService = emailService2;
        String brokers = "moped-01.srvs.cloudkafka.com:9094,moped-02.srvs.cloudkafka.com:9094,"
                 + "moped-03.srvs.cloudkafka.com:9094";


        this.topic = username + "-default";

        String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
        String jaasCfg = String.format(jaasTemplate, username, password);

        String serializer = StringSerializer.class.getName();
        String deserializer = StringDeserializer.class.getName();
        props = new Properties();
        props.put("bootstrap.servers", brokers);
        props.put("group.id", username + "-consumer");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", deserializer);
        props.put("value.deserializer", deserializer);
        props.put("key.serializer", serializer);
        props.put("value.serializer", serializer);
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.mechanism", "SCRAM-SHA-256");
        props.put("sasl.jaas.config", jaasCfg);
    }


    public void consume() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                emailService.sendValidateAccountEmail(record.key());
            }
        }
    }
    private Runnable t1 = () -> {
        try{
            Thread.sleep(100);
            consume();//
        } catch (Exception e){}

    };


    @Bean
    @DependsOn("emailService")
    public void threadKafka(){
        new Thread(t1).start();
    }

    public void produce(String email, String conteudo) {
        Producer<String, String> producer = new KafkaProducer<>(props);
        producer.send(new ProducerRecord<>(topic, email, conteudo));
    }

}
