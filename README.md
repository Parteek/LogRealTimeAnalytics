Run zookeeper - open source, high performance coordintation service for distributed applications
bin/zookeeper-server-start.sh config/zookeeper.properties

Run kafka
bin/kafka-server-start.sh config/server.properties

Create Topics for kafka
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic logs
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic get
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic put
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic post
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic delete

To add logs
./log-emitter-ubuntu  > console.log


For Producer
tail -n 0 -f ../producer/console.log | bin/kafka-console-producer.sh  --broker-list localhost:9092 --topic logs

For consumer
# Log consumer
javac -cp "/projects/others/kafka/kafka_2.11-0.11.0.0/libs/*" ConsumerGroup.java
java -cp "/projects/others/kafka/kafka_2.11-0.11.0.0/libs/*":. ConsumerGroup logs loggroup

# Http consumer
javac -cp "/projects/others/kafka/kafka_2.11-0.11.0.0/libs/*" HttpConsumerGroup.java
java -cp "/projects/others/kafka/kafka_2.11-0.11.0.0/libs/*":. HttpConsumerGroup get getgroup


Detailed Description


Why kafka
1. Highly distributed system
2. High Availability
3. Act as storage as well
4. Consumer and Producer can enter and exit the system without impacting
5. Reliable as it provides inbuilt replication follows the follower and leader philosphy
 

Why mongodb
1. Distributed System with automatic shards
2. High Write Capability - works on eventual consistency

Mongodb




