/**
 * 
 */
package com.cintap.transport.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author SurenderMogiloju
 *
 */
@Component
public class KafkaSender {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public void send(String queue, String message) {
	    kafkaTemplate.send(queue, message);
	}
}
