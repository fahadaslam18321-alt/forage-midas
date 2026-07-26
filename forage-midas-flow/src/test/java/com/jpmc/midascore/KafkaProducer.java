package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    @Value("${general.kafka-topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    public void send(String transactionData) {
        if (transactionData == null) return;

        // Hidden \n, \r aur extra spaces ko completely remove karein
        String cleanData = transactionData.replaceAll("\r", "").replaceAll("\n", "").trim();
        if (cleanData.isEmpty()) return;

        String[] parts = cleanData.split(",");

        if (parts.length >= 3) {
            long senderId = Long.parseLong(parts[0].trim());
            long recipientId = Long.parseLong(parts[1].trim());
            float amount = Float.parseFloat(parts[2].trim());

            Transaction transaction = new Transaction(senderId, recipientId, amount);
            kafkaTemplate.send(topic, transaction);
        }
    }
}