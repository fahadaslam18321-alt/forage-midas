package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KafkaConsumer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-group")
    public void listen(Transaction transaction) {
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        if (sender != null && recipient != null) {
            if (sender.getBalance() >= transaction.getAmount()) {
                
                // 1. Incentive API call
                String incentiveUrl = "http://localhost:8080/incentive";
                Incentive response = restTemplate.postForObject(incentiveUrl, transaction, Incentive.class);
                float incentiveAmount = (response != null) ? response.getAmount() : 0.0f;

                // 2. Sender ki balance update (sirf transaction amount minus hoga)
                sender.setBalance(sender.getBalance() - transaction.getAmount());

                // 3. Recipient ki balance update (transaction amount + incentive amount add hoga)
                recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentiveAmount);

                // Save Updated Users
                userRepository.save(sender);
                userRepository.save(recipient);

                // Save Transaction Record with Incentive
                TransactionRecord record = new TransactionRecord(sender, recipient, transaction.getAmount(), incentiveAmount);
                transactionRepository.save(record);
            }
        }
    }
}