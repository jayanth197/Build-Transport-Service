package com.cintap.transport.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.springframework.stereotype.Component;

import com.berryworks.edireader.json.fromedi.EdiToJson;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EditUtility {

	public static String convertEditToJosn(String ediData) {
		String ediJson = "";
		final EdiToJson ediToJson = new EdiToJson();
		ediToJson.setFormatting(true);
		ediToJson.setAnnotated(true);
		ediToJson.setSummarize(false);
		try {
			ediJson = ediToJson.asJson(ediData);
			log.info("EdiJson is ::{}", ediJson);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return ediJson;
	}

	public static String convertEdiFileToJson(File file) {
		String ediJson = "";
		final EdiToJson ediToJson = new EdiToJson();
		ediToJson.setFormatting(true);
		ediToJson.setAnnotated(true);
		ediToJson.setSummarize(false);
		try (Reader reader = new BufferedReader(new FileReader(file)); Writer writer = new StringWriter()) {
			ediToJson.asJson(reader, writer);
			ediJson = writer.toString();
			log.info("EdiJson is ::{}", ediJson);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return ediJson;
	}
}
