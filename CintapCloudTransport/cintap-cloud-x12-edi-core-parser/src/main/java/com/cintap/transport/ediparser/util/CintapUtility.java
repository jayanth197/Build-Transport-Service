
package com.cintap.transport.ediparser.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CintapUtility{
	// Generate random password
	public static String convertObjectToJson(Object object) //throws JsonProcessingException 
	{
		try {
			ObjectMapper obj = new ObjectMapper(); 
			return obj.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return e.toString();
		} 
	}

	
}
