## Context

There are multiple microservices at Hotstar that log all incoming requests. To keep a check on the health of these services, we have to monitor the number of successful and failed requests in real-time.


## Problem Statement

Your job is to design a system that aggregates these service logs every minute and then calculates the number of logs for a particular HttpMethod in that one minute window.
While designing the system, ensure reliability, accuracy and latency of computation.
As a part of the design, we expect a high-level architecture diagram, low-level diagram that describes each component of the architecture in detail and a detailed documentation of your design choices. You are free to use any open-source framework or library that solves the problem.

Write a demo code to demonstrate your design.

## Solution

### Tools Used
1. kakfa
2. mongodb
3. java programming language

### Architecture Diagram
![Image of Yaktocat](https://github.com/Parteek/LogRealTimeAnalytics/blob/master/design_architecture.jpg)

### Detailed Description

#### why we are processing the same event/logs twice?
First time we are pushing the log as event with no parsing, just dumping the log as it is so that in future, if we want to consume that data in different way we can just replay the same stream again.

Second time we are pushing the log as event with proper topic and paritioning, so that we can aggregate the data in much more efficient and scalable manner.

#### why choose http method as different topic
By choosing different topic for different http methods we can actually configure the no of partitions based on the scale of the different http methods, in normal scenario for most of the systems the get calls are more than the other calls and similarly delete calls being the minimum so we can have more nodes for get calls and less nodes for delete. 

#### explain about partitions
Will use timestamp as the key for partitioning, so that the records with same timestamp goes to one partition for a particular topic, Here timestamp is only till minutes for eg. 16:40:09, 16:40:00, 16:40:59 will be all mapped to the timestamp 16:40 and that will be the key for partition. There consumer can fetch them in batch and aggregate it. 

#### How data is saved in mongo


##### Why kafka
1. Highly distributed system
2. High Availability
3. Act as storage as well
4. Consumer and Producer can enter and exit the system without impacting
5. Reliable as it provides inbuilt replication follows the follower and leader philosphy
 

##### Why mongodb
1. Distributed System with automatic shards
2. High Write Capability - works on eventual consistency

### How to run?
Install go, java and Please follow the below instructions.

#### Download and install kafka
kafka(https://kafka.apache.org/quickstart)

#### Start zookeeper
```
cd kakfa_directory/
bin/zookeeper-server-start.sh config/zookeeper.properties
```

#### Start kafka
```
cd kakfa_directory/
bin/kafka-server-start.sh config/server.properties
```

#### Create Topics for kafka
```
cd kakfa_directory/
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic logs
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic get
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic put
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic post
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic delete
```

#### To add sample logs
```
./producer/log-emitter-ubuntu  > console.log
```

#### To start producer
```
cd kakfa_directory/
tail -n 0 -f ../producer/console.log | bin/kafka-console-producer.sh  --broker-list localhost:9092 --topic logs
```


#### Start Log consumer
```
cd consumer/
javac -cp "/projects/others/kafka/kafka_2.11-0.11.0.0/libs/*" ConsumerGroup.java
java -cp "/projects/others/kafka/kafka_2.11-0.11.0.0/libs/*":. ConsumerGroup logs loggroup
```

#### Start Http consumer
```
cd consumer/
javac -cp "/projects/others/kafka/kafka_2.11-0.11.0.0/libs/*" HttpConsumerGroup.java
java -cp "/projects/others/kafka/kafka_2.11-0.11.0.0/libs/*":. HttpConsumerGroup get getgroup (Run this multiple times to create more consumers)
```








