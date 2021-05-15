package com.example.springkafkadocker.client;

import com.example.springkafkadocker.avro.SchemaRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
@Service
public class Producer {

    final private KafkaTemplate template;

    public Producer(KafkaTemplate template) {
        this.template = template;
    }

    private List<GenericRecord> records = new ArrayList<>();

    public void sendAvroMessage(String topic, String key, String message) {
        GenericRecord record = new GenericData.Record( SchemaRepository.instance().getSchemaObject());
        record.put("message", message);
        record.put( "key", key );
        record.put( "topic", topic );
        records.add(record);
        template.setDefaultTopic( topic );
        template.sendDefault( key, records );
        log.info("Producing record: {}", records);
        template.flush();
        records.clear();

    }
}

