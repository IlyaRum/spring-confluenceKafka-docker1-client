package com.example.springkafkadocker.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ExecutionException;

@RestController
public class Controller {

    private final com.example.springkafkadocker.client.Producer producer;

    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    public Controller(Producer producer) {
        this.producer = producer;
    }

    @PostMapping("/publish")
    public void writeMessageToTopic(@RequestParam("message") String message){
        this.producer.writeMessage(message);

    }

    @PostMapping("/test")
    public void send(@RequestParam("message") String message) throws ExecutionException, InterruptedException {

        ListenableFuture<SendResult<String, String>> listenableFuture = this.producer.sendMessage("my_topic", "IN_KEY", message);

        SendResult<String, String> result = listenableFuture.get();
        logger.info(String.format("Produced message:\nmessage: %s\ntopic: %s\noffset: %d\npartition: %d\nvalue size: %d",
                message,
                result.getRecordMetadata().topic(),
                result.getRecordMetadata().offset(),
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().serializedValueSize()
        ));
    }

}
