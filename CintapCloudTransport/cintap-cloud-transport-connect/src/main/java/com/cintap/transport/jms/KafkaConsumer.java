/**
 * 
 */
package com.cintap.transport.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cintap.transport.message.handler.MessageProcessHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author SurenderMogiloju
 *
 */
@Component
@Slf4j
public class KafkaConsumer {
	
	@Autowired
	private MessageProcessHandler messageProcessHandler;
	
	//@KafkaListener(id = "id-1", topicPartitions = { @TopicPartition (topic = "${cintap.tydenbrooks.queue}", partitions = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}) }, groupId = "group-id")
	@KafkaListener(id = "id-1", topics = "${cintap.transportation.queue}", groupId = "group-id")
	public void consumerId0(String message){
        log.info("Listener Id-1, Thread ID: " + Thread.currentThread().getId()+" - "+String.format("Message recieved -> %s", message));
        messageProcessHandler.processMessage(message);
    }
	
	
	//@KafkaListener(id = "id-2", topicPartitions = { @TopicPartition (topic = "${cintap.tydenbrooks.queue}", partitions = { "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"}) }, groupId = "group-id")
	@KafkaListener(id = "Id-2", topics = "${cintap.transportation.queue}", groupId = "group-id")
	public void consumerId1(String message){
        log.info("Listener Id-2, Thread ID: " + Thread.currentThread().getId()+" - "+String.format("Message recieved -> %s", message));
        messageProcessHandler.processMessage(message);
    }
}