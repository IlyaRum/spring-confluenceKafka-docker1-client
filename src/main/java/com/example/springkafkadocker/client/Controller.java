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

    private final Producer producer;

    public Controller(Producer producer) {
        this.producer = producer;
    }

    @PostMapping("/test")
    public void sendAvroMessage(@RequestParam("message") String message) {
        this.producer.sendAvroMessage("my_topic", "IN_KEY", message);

    }
}
