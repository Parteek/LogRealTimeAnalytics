import java.util.Properties;
import java.util.Arrays;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class HttpConsumerGroup {
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
      KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
      consumer.subscribe(Arrays.asList(topic));
      int count = 0;
      String lastRecord = "";
      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(20000);
        for (ConsumerRecord<String, String> record : records){
          if(lastRecord.equals(record.key())){
            count++;  
          }else if(lastRecord.equals("")){
            lastRecord = record.key();
          }else{
            System.out.println(lastRecord + " :: " + count);
            // update the count in the database
            count = 0;
            lastRecord = record.key();
          }
          System.out.printf("key = %s, value = %s%n", record.key(), record.value());
        }
        if(count > 0){
          System.out.println(lastRecord + " :: " + count);
          // update the count in the database
        }
      }     
   }  
}