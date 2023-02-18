package life.majd.challenge.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

  @KafkaListener(topics = "employee.created", groupId = "employee-group")
  public void consumeEmployeeCreated(String employee) {
    log.info("Kafka message received: {} was created", employee);
  }

  @KafkaListener(topics = "employee.updated", groupId = "employee-group")
  public void consumeEmployeeUpdated(String employee) {
    // Your employee updated logic goes here
    log.info("Kafka message received: {} was updated", employee);
  }

  @KafkaListener(topics = "employee.deleted", groupId = "employee-group")
  public void consumeEmployeeDeleted(String uuid) {
    // Your employee deleted logic goes here
    log.info("Kafka message received: Employee with following uuid {} was deleted", uuid);
  }
}