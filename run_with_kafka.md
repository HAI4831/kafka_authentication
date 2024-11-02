1. [guide on youtube](https://www.youtube.com/watch?v=lFox22RJE7s&t=20s)
2. download folder zookeeper to manage kafka server:
   [pageDownload](https://zookeeper.apache.org/releases.html#download)
    download binary zip,tar file to don't need run build jar file to target to run
    [binaryfolderdownload](https://zookeeper.apache.org/releases.html#download)
3. unzip binaryfolderdownload to nagative to:
   D:\download\kafka\bin\windows 
4. we need 5 notes to start:
    + run zookeeper server:
      - access D:\download\kafka>config/zookeeper.properties (to config for zookeeper server include: port default 9092,hostname...)
      - D:\download\kafka\bin\windows>
      zookeeper-server-start.bat ../../config/zookeeper.properties
    + run kafka server:
      - access D:\download\kafka>config/server.properties (to config for zookeeper server include: port default 9092,hostname...)
      - D:\download\kafka\bin\windows>
      kafka-server-start.bat ../../config/server.properties
    + console log kafka message
      - access D:\download\kafka>config/server.properties (to config for zookeeper server include: port default 9092,hostname...)
      - D:\download\kafka\bin\windows>
      kafka-console-consumer.bat --topic my-topic --bootstrap-server localhost:9092 --from-beginning ../../config/server.properties
    + register topic:
      - D:\download\kafka\bin\windows>
      kafka-topics.bat --describe --topic my-topic --bootstrap-server localhost:9092
    + display list topic register:
      - D:\download\kafka\bin\windows>kafka-topics.bat --list --bootstrap-server localhost:9092
    + run display all message on topic 
      - D:\download\kafka\bin\windows>
      kafka-console-consumer.bat --topic my-topic --bootstrap-server localhost:9092 --from-beginning
    + delete topic:
      - kafka-topics --bootstrap-server localhost:9092 --delete --topic topic_order
   + create topic:
      - kafka-topics --bootstrap-server localhost:9092 --create --topic topic_order --partitions 3 --replication-factor 1
2. kafka run on kubernetes
   kubectl exec -it kafka-deployment-5647b8549-h68kn -- bash
   kafka-topics --bootstrap-server localhost:9092 --create --topic topic_order --replication-factor 1 --partitions 3
   kafka-console-producer --broker-list localhost:9092 --topic topic_order
   kubectl exec -it kafka-deployment-5647b8549-h68kn -- kafka-console-consumer --bootstrap-server localhost:9092 --topic topic_order --from-beginning
   kafka-topics --list --bootstrap-server localhost:9092
3. run on docker
   docker pull postgres
   docker run --name custom-postgres -e POSTGRES_USER=postgres_docker -e POSTGRES_PASSWORD=abCD@1234 -e POSTGRES_DB=kafka_auth -p 5432:5432 -d postgres
   docker exec -it custom-postgres psql -U postgres_docker -d kafka_auth
   docker inspect custom-postgres
4. run postgres on kubernetes
[guide](https://phoenixnap.com/kb/postgresql-kubernetes)
kubectl exec -it postgres-666465d86b-77twz -- psql -h localhost -U postgreskube -p 5432 -d kafka_auth