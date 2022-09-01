package test.api.email.email.services

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component
import java.util.*

@Component
class KafkaConfig(
    var emailService: EmailService, @Value("\${spring.kfuser}") username: String,
    @Value("\${spring.kfpass}") password: String?
) {

    private val topic: String
    private val props: Properties
    fun consume() {
        val consumer = KafkaConsumer<String, String>(props)
        consumer.subscribe(Arrays.asList(topic))
        while (true) {
            val records = consumer.poll(1000)
            for (record in records) {
                emailService.sendValidateAccountEmail(record.key(), record.value())
            }
        }
    }

    private val t1 = Runnable {
        try {
            Thread.sleep(100)
            consume() //
        } catch (e: Exception) {
        }
    }

    init {
        val brokers = ("moped-01.srvs.cloudkafka.com:9094,moped-02.srvs.cloudkafka.com:9094,"
                + "moped-03.srvs.cloudkafka.com:9094")
        topic = "$username-default"
        val jaasTemplate =
            "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";"
        val jaasCfg = String.format(jaasTemplate, username, password)
        val serializer = StringSerializer::class.java.name
        val deserializer = StringDeserializer::class.java.name
        props = Properties()
        props["bootstrap.servers"] = brokers
        props["group.id"] = "$username-consumer"
        props["enable.auto.commit"] = "true"
        props["auto.commit.interval.ms"] = "1000"
        props["auto.offset.reset"] = "earliest"
        props["session.timeout.ms"] = "30000"
        props["key.deserializer"] = deserializer
        props["value.deserializer"] = deserializer
        props["key.serializer"] = serializer
        props["value.serializer"] = serializer
        props["security.protocol"] = "SASL_SSL"
        props["sasl.mechanism"] = "SCRAM-SHA-256"
        props["sasl.jaas.config"] = jaasCfg
    }

    @Bean
    @DependsOn("emailService")
    fun threadKafka() {
        Thread(t1).start()
    }

    fun produce(email: String, conteudo: String) {
        val producer: Producer<String, String> = KafkaProducer(props)
        producer.send(ProducerRecord(topic, email, conteudo))
    }
}