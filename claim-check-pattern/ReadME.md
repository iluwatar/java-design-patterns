# Command for Apache Kafka
    I am using git bash for the following commands to execute.
1. First, go to the Kafka root folder

        cd C:\kafka_2.13-2.7.0

2. Run Zookeeper server

        bin/windows/zookeeper-server-start.bat config/zookeeper.properties
3. Then Run Kafka Server.
        
        bin/windows/kafka-server-start.bat config/server.properties
4. 

    bin/windows/kafka-topics.bat --describe --topic quickstart-events --bootstrap-server localhost:9092

5.  

    bin/windows/kafka-topics.bat --create --topic quickstart-events --bootstrap-server localhost:9092

6.  
    
    bin/windows/kafka-console-producer.bat --topic quickstart-events --bootstrap-server localhost:9092
7.  
        
    bin/windows/kafka-console-producer.bat --topic quickstart-events --bootstrap-server localhost:9092
8.  
        
    bin/windows/kafka-console-consumer.bat --topic quickstart-events --from-beginning --bootstrap-server localhost:9092