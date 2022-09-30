package com.cintap.transport.desadv.shipnotice.xml.parser;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cintap.transport.desadv.shipnotice.model.Interface;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DespatchAdviceShipNoticeXmlParser {

	public Interface convertXmlTo(String fileData) {
		Interface inter = null;
		try {
			inter = convertXMLToObj(fileData);
//			System.out.println(inter);
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
			rhenusXml = rhenusXml.replaceAll("xsi:schemaLocation=\"http://www.corelogicllc.com http://b2b.corelogicllc.com/schemas/v2/ShipNoticeManifest.xsd\"", "");
			rhenusXml = rhenusXml.replaceAll("xsi:nil=\"true\"", "");
			jaxbContext = JAXBContext.newInstance(Interface.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			rhenusObj = (Interface) jaxbUnmarshaller.unmarshal(new StringReader(rhenusXml));
		} catch (JAXBException exception) {
			exception.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return rhenusObj;
	}
}
