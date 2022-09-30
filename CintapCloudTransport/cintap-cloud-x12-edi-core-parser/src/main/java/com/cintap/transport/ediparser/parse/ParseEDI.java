package com.cintap.transport.ediparser.parse;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;
import org.json.XML;
import org.xml.sax.InputSource;

import com.berryworks.edireader.EDIReader;
import com.cintap.transport.ediparser.constant.STATUS;
import com.cintap.transport.ediparser.dto.global.EdiBase;
import com.cintap.transport.ediparser.dto.global.EdiResponse;
import com.cintap.transport.ediparser.dto.global.Transaction;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class ParseEDI {

	EDIReader parser;
	EdiResponse ediResponse;
	public ParseEDI() {
	}


	public EdiResponse convertToDto(String inputEdiString) {
		try {
			ediResponse=new EdiResponse();


			InputSource inputSource=new InputSource( new StringReader( inputEdiString ) );

			//parser= EDIReaderFactory.createEDIReader(inputSource);
			StringWriter ackwriter = new StringWriter();
			parser=new EDIReader();

			parser.setAcknowledgment(ackwriter);
			SAXSource source = new SAXSource(parser, inputSource);   


			Transformer transformer = TransformerFactory.newInstance().newTransformer(); 

			//transformer.transform(source, result);

			StringWriter writer = new StringWriter();

			//transform document to string 
			transformer.transform(source, new StreamResult(writer));

			String xmlString = writer.getBuffer().toString();  
			String ackEdiString=ackwriter.getBuffer().toString();
			//System.out.println(xmlString);                      //Print to console or logs
			String strResult ="";


			JSONObject json = XML.toJSONObject(xmlString);   
			String jsonString = json.toString(4);  
			System.out.println(jsonString);  

			Gson gson = new Gson();
			
			EdiBase ediBase= gson.fromJson(jsonString, EdiBase.class);
		
			
			//JsonReader reader = new JsonReader(new StringReader(jsonString));
			//reader.setLenient(true);
			//EdiBase ediBase = gson.fromJson(reader, EdiBase.class);
			Object transaction=ediBase.getEdiroot().getInterchange().getGroup().getTransaction();
		
			List<Transaction> transactionLst=new ArrayList<Transaction>();
			if(transaction.getClass().getTypeName().equals("com.google.gson.internal.LinkedTreeMap")) {
				JsonElement jsonElement = gson.toJsonTree(transaction);
				Transaction pojo = gson.fromJson(jsonElement, Transaction.class);
				transactionLst.add(pojo);				
			}else {
				for(Map trans : ((ArrayList<Map>) transaction) ) {
					JsonElement jsonElement = gson.toJsonTree(trans);
					Transaction pojo = gson.fromJson(jsonElement, Transaction.class);
					transactionLst.add(pojo);								
				}
			}
			ediBase.getEdiroot().getInterchange().getGroup().setTransactionLst(transactionLst);	
			ediResponse.setEdibase(ediBase);
			ediResponse.setEdiAck(ackEdiString);
			ediResponse.setStatusMessage("SUCCESS :transactions: "+transactionLst.size());
			ediResponse.setStatus(STATUS.SUCCESS);
			return ediResponse;

		} catch (Exception e) {
			ediResponse=new EdiResponse();
			ediResponse.setStatusMessage(e.toString());
			ediResponse.setStatus(STATUS.FAILED_TO_PARSE);
			return ediResponse;
		}
	}

	public EdiBase convertEdiToJson(EDIReader parser,InputSource inputSource ) {
		try {

			SAXSource source = new SAXSource(parser, inputSource);   


			Transformer transformer = TransformerFactory.newInstance().newTransformer(); 

			//transformer.transform(source, result);

			StringWriter writer = new StringWriter();

			//transform document to string 
			transformer.transform(source, new StreamResult(writer));

			String xmlString = writer.getBuffer().toString();   
			System.out.println(xmlString);                      //Print to console or logs
			String strResult ="";


			JSONObject json = XML.toJSONObject(xmlString);   
			String jsonString = json.toString(4);  
			System.out.println(jsonString);  

			Gson gson = new Gson();
			EdiBase ediBase= gson.fromJson(jsonString, EdiBase.class);

			return ediBase;

		} catch (Exception e) {			
			return null;
		}

	}

}
