package com.manica.productscatalogue.subscriptions.user;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.messaging.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserConsumer {



    private final UserService userService;


    @KafkaListener(topics = "user_updates", groupId = "products")
    public void receiveUserUpdate(ConsumerRecord<String, Object> consumerRecord) throws JsonProcessingException {
        log.info("Receiving new user record  :: "+ consumerRecord.value());
        UserDto userDto = (UserDto)consumerRecord.value();

        log.info("Cast user object :"+ userDto);
        userService.add(userDto);
    }

}
