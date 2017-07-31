import java.util.Properties;
import java.util.Arrays;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;

//import simple producer packages
import org.apache.kafka.clients.producer.Producer;

//import KafkaProducer packages
import org.apache.kafka.clients.producer.KafkaProducer;

//import ProducerRecord packages
import org.apache.kafka.clients.producer.ProducerRecord;

public class ConsumerGroup {
   public static void main(String[] args) throws Exception {
      if(args.length < 2){
         System.out.println("Usage: consumer <topic> <groupname>");
         return;
      }
      
      String topic = args[0].toString();
      String group = args[1].toString();
       Properties props = new Properties();
     props.put("bootstrap.servers", "localhost:9092");
     props.put("group.id", group);
     props.put("enable.auto.commit", "true");
     props.put("auto.commit.interval.ms", "1000");
     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
      props.put("key.serializer", 
         "org.apache.kafka.common.serialization.StringSerializer");
         
      props.put("value.serializer", 
         "org.apache.kafka.common.serialization.StringSerializer");
      Producer<String, String> producer = new KafkaProducer
         <String, String>(props);
      KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
      consumer.subscribe(Arrays.asList(topic));
      while (true) {
         ConsumerRecords<String, String> records = consumer.poll(100);
         for (ConsumerRecord<String, String> record : records){
            System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            String val = record.value();
            String httpMethod = val.split(" - ")[3];
            String date = val.split(" - ")[1];
            int len = date.length();
            String datetimeinminutes = date.substring(0, len-4);
            producer.send(new ProducerRecord<String, String>(httpMethod.toLowerCase(), datetimeinminutes, record.value()));
         }
      }     
   }  
}