package life.majd.challenge.configuration;

import java.util.Arrays;
import java.util.List;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

  public static final String EMPLOYEE_UPDATED_TOPIC = "employee.updated";
  public static final String EMPLOYEE_CREATED_TOPIC = "employee.created";
  public static final String EMPLOYEE_DELETED_TOPIC = "employee.deleted";

  @Bean
  public NewTopic employeeUpdatedTopic() {
    return new NewTopic(EMPLOYEE_UPDATED_TOPIC, 1, (short) 1);
  }

  @Bean
  public NewTopic employeeCreatedTopic() {
    return new NewTopic(EMPLOYEE_CREATED_TOPIC, 1, (short) 1);
  }

  @Bean
  public NewTopic employeeDeletedTopic() {
    return new NewTopic(EMPLOYEE_DELETED_TOPIC, 1, (short) 1);
  }

  @Bean
  public List<NewTopic> topics() {
    return Arrays.asList(employeeUpdatedTopic(), employeeCreatedTopic(), employeeDeletedTopic());
  }

}