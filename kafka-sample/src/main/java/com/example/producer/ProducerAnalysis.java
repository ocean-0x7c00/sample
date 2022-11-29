package com.example.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.internals.FutureRecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 1.构建生产者客户端参数
 * 2.构建生产者实例
 * 3.创建并发送消息
 * 4.关闭实例
 *
 * @author yancy
 * @version 1.0.0
 * @since 2022/11/29 15:23
 */
public class ProducerAnalysis {
  private static final String BROKER_LIST = "39.103.193.206:9092,49.235.90.221:9092";
  private static final String TOPIC = "test_demo";

  public static Properties initConfig() {
    Properties properties = new Properties();

    //生产者客户端连接Kafka集群所需要的broker地址
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);

    //broker 端接收的消息必须以字节数组（byte[]）的形式存在
    //key.serializer和value.serializer这两个参数分别用来指定key和value序列化操作的序列化器，这两个参数无默认值
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


    //默认为空，Kafka会默认生成一个非空字符串，producer-数字
    properties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "producer-1");
    return properties;
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    Properties properties = initConfig();

    //KafkaProducer是线程安全的
    KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

    //消息的构建，针对不同的消息需要使用不同的ProducerRecord构造函数

    //相同的key会进入同一个partition中
    String key = "";
    String msg = "Hello Kafka!";
    ProducerRecord producerRecord = new ProducerRecord(TOPIC, key, msg);



    //发送消息有三种模式
    //1.发后即忘（无须broker的确认，性能高，可靠性差）
    kafkaProducer.send(producerRecord);

    //2.同步
    kafkaProducer.send(producerRecord).get();

    Future<FutureRecordMetadata> future = kafkaProducer.send(producerRecord);
    //FutureRecordMetadata中存储了当前消息的元数据信息
    FutureRecordMetadata futureRecordMetadata = future.get();


    //3.异步
    kafkaProducer.send(producerRecord);

  }

}
