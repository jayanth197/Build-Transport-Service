package com.cintap.transport.aperak.xml.parser;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cintap.transport.aperak.model.Interface;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AperakXmlParser {

	public Interface convertXmlTo(String fileData) {
		Interface inter = null;
		try {
//			File xmlFile = new File(fileData);
//
//			Reader fileReader = new FileReader(xmlFile);
//			BufferedReader bufReader = new BufferedReader(fileReader);
//			StringBuilder sb = new StringBuilder();
//			String line = bufReader.readLine();
//			while (line != null) {
//				sb.append(line).append("\n");
//				line = bufReader.readLine();
//			}
//			String xml2String = sb.toString();
//			System.out.println(xml2String);

			inter = convertXMLToObj(fileData);
			System.out.println(inter);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return inter;
	}

	public static Interface convertXMLToObj(String rhenusXml) {
		JAXBContext jaxbContext;
		Interface rhenusObj = null;
		try {
			rhenusXml = rhenusXml.replaceAll("<!DOCTYPE((.|\n|\r)*?)\">", "");

			rhenusXml = rhenusXml.replaceAll("xmlns=\"http://www.corelogicllc.com\"", "");
			rhenusXml = rhenusXml.replaceAll("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
			rhenusXml = rhenusXml.replaceAll("xsi:schemaLocation=\"http://www.corelogicllc.com https://b2b.corelogicllc.com/schemas/v2/GenericAcknowledgement.xsd\"", "");
			jaxbContext = JAXBContext.newInstance(Interface.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			rhenusObj = (Interface) jaxbUnmarshaller.unmarshal(new StringReader(rhenusXml));
		} catch (JAXBException exception) {
			exception.printStackTrace();
		}
		return rhenusObj;
	}
}
