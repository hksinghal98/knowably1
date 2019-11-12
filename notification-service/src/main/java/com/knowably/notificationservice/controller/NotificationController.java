package com.knowably.notificationservice.controller;

import com.google.gson.Gson;
import com.knowably.notificationservice.domain.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;



/**
 * RestController annotation is used to create Restful web services using Spring MVC
 */
@RestController
public class NotificationController {

    /**
     * Constructor based Dependency injection to inject SimpMessagingTemplate into controller
     */
    @Autowired
    private SimpMessagingTemplate template;
    private String destination = "/topic/notification";

/**
    *Kafka listener which can be used to call consume method when something is published in the finalresult Topic
*/
    @KafkaListener(topics ="FinalResult",groupId = "service",containerFactory = "kafkaListenerContainerFactory")
    public void consume(String receivedResponse){

        if (receivedResponse.equals("wait")) {
            template.convertAndSend(destination,"Query is getting processed");

        }
        else if (receivedResponse.equals("notFound")){
            template.convertAndSend(destination,"Sorry!!the result for your query is not found,Search for another query");
        }

        Gson gson=new Gson();
        QueryResponse queryResponse=gson.fromJson(receivedResponse,QueryResponse.class);
        queryResponse.setLocalDateTime(LocalDateTime.now());

/**
        *The return value is broadcast to users who subscribes to "/topic/notification"
*/

        template.convertAndSend("/topic/notification", queryResponse.getResult());
    }





}
