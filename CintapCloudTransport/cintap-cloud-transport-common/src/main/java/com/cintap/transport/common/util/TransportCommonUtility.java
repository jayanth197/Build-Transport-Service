
package com.cintap.transport.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.TimeZone;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.cintap.transport.common.enums.ACKSTATUS;
import com.cintap.transport.common.enums.APERAKSTATUS;
import com.cintap.transport.common.enums.RHENUSSOURCECODE;
import com.cintap.transport.entity.common.ErrorLog;
import com.cintap.transport.repository.common.BpiErrorLogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TransportCommonUtility {
	private static ObjectMapper mapper = new ObjectMapper();

	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

	public static Object convertJSONToObject(String jsonMessage, Class<?> cls) throws Exception {
		log.info("Inside convertJSONToObject");
		return mapper.readValue(jsonMessage, cls);
	}
	
	public static String genrateRandomNumber()
	{
		Random rnd = new Random();
	    int number = rnd.nextInt(999999);

	    // this will convert any number sequence into 6 character.
	    return String.format("%08d", number);
	}
	
	public static String generateControlNumber(String refNumber) {
		return StringUtils.leftPad(refNumber, 8, "0");
	}

	public static String convertObjectToJson(Object object) {
		try {
			ObjectMapper obj = new ObjectMapper();
			return obj.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.info("convertObjectToJson  - Unable to convert into JSON  : " + e);
			return "";
		}
	}

	// Validating minimum required Segments are present in the EDI file or not
	public static boolean validateEditSegments(String inputString, String[] items) {
		boolean found = true;
		for (String item : items) {
			if (!inputString.contains(item)) {
				found = false;
				break;
			}
		}
		return found;
	}

	/**
	 * 
	 * @param sourceDate    --> updatedDate from bpi_user table
	 * @param expiryMinutes --> pulling it from bpi_config table and it is
	 *                      completely configurable
	 * @return --> Validation logic --> Take updatedDate + add expiry minutes then
	 *         compare with current date and time
	 * @throws ParseException
	 */
	public static boolean validateExpiryMinutes(String sourceDate, int expiryMinutes) {
		try {
			Date source = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sourceDate);
			source = DateUtils.addMinutes(source, expiryMinutes);
			Date currentDate = new Date();
			return currentDate.before(source) ? Boolean.TRUE : Boolean.FALSE;
		} catch (ParseException exception) {
			log.info("Validate Expiry Minutes : Exception  " + exception);
			return Boolean.FALSE;
		}
	}
	
	public static int convertStringtoInt(String value) {
		return Double.valueOf(value).intValue();
	}

	public static String getResponseCodeValue(String code) {
		Optional<APERAKSTATUS> status = APERAKSTATUS.getStatusByCode(code);
		if (status.isPresent()) {
			return status.get().getValue();
		}
		return "";
	}

	public static String getResponseCode(String code) {
		Optional<APERAKSTATUS> status = APERAKSTATUS.getStatusByValue(code);
		if (status.isPresent()) {
			return status.get().getCode();
		}
		return "";
	}

	public static String getSourceCode(String source) {
		Optional<RHENUSSOURCECODE> sourceCode = RHENUSSOURCECODE.getSourceByValue(source);
		if (sourceCode.isPresent()) {
			return sourceCode.get().getCode();
		}
		return "";
	}
	
	public static String getSourceValue(String source) {
		Optional<RHENUSSOURCECODE> sourceCode = RHENUSSOURCECODE.getSourceByCode(source);
		if (sourceCode.isPresent()) {
			return sourceCode.get().getValue();
		}
		return "";
	}
	
	public static Integer getAckStatus(String ack) {
		Integer ackStatus = 0;
		if (APERAKSTATUS.AP.getCode().equals(ack) || APERAKSTATUS.AP.getValue().equals(ack)) {
			ackStatus = ACKSTATUS.ACCEPTED.getCode();
		} else if (APERAKSTATUS.RE.getCode().equals(ack) || APERAKSTATUS.RE.getValue().equals(ack)) {
			ackStatus = ACKSTATUS.RECEIVED.getCode();
		} else if (APERAKSTATUS.RJ.getCode().equals(ack) || APERAKSTATUS.RJ.getValue().equals(ack)) {
			ackStatus = ACKSTATUS.REJECTED.getCode();
		} else if (APERAKSTATUS.ER.getCode().equals(ack) || APERAKSTATUS.ER.getValue().equals(ack)) {
			ackStatus = ACKSTATUS.ERROR.getCode();
		}
		return ackStatus;
	}
	
	public static Integer getSentAckStatus(String ack) {
		Integer ackStatus = 0;
		if (APERAKSTATUS.AP.getCode().equals(ack) || APERAKSTATUS.AP.getValue().equals(ack)) {
			ackStatus = ACKSTATUS.ACCEPTED_SENT.getCode();
		} else if (APERAKSTATUS.RE.getCode().equals(ack) || APERAKSTATUS.RE.getValue().equals(ack)) {
			ackStatus = ACKSTATUS.RECEIVED_SENT.getCode();
		} else if (APERAKSTATUS.RJ.getCode().equals(ack) || APERAKSTATUS.RJ.getValue().equals(ack)) {
			ackStatus = ACKSTATUS.REJECTED_SENT.getCode();
		} else if (APERAKSTATUS.ER.getCode().equals(ack) || APERAKSTATUS.ER.getValue().equals(ack)) {
			ackStatus = ACKSTATUS.ERROR_SENT.getCode();
		}
		return ackStatus;
	}
	
	public static String getDocumentCreation() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
	}

	public static String getDocumentCreationDateAndTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd:HHmm");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
	}

	public static String getDocumentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
	}

	public static String getReportDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
	}

	public static String getDocumentChangeDateAndTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
	}

	public static String convertToDateFormat(String date, String currentDateFormat, String reqDateFormat) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(reqDateFormat);
			Date date1 = new SimpleDateFormat(currentDateFormat).parse(date);
			return sdf.format(date1);
		} catch (Exception e) {
			return date;
		}
	}

	@Autowired
	BpiErrorLogRepository bpiErrorLogRepository;

	public void saveErrorLog(Exception exception) {
		StackTraceElement[] stackTrace = exception.getStackTrace();
		StringWriter sw = new StringWriter();
		exception.printStackTrace(new PrintWriter(sw));
		ErrorLog bpiErrorLog = ErrorLog.builder().className(stackTrace[0].getClassName())
				.funName(stackTrace[0].getMethodName()).fileName(stackTrace[0].getFileName())
				.errorDescription(sw.toString()).exception(exception.toString())
				.createdDate(TransportCommonUtility.getCurrentDateTime()).createdBy("System").build();
		log.info("Exception : {}", sw.toString());
		bpiErrorLogRepository.save(bpiErrorLog);
	}

	public static String getFileDateFormatInCS() {
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy-HHMMSS");
		format.setTimeZone(TimeZone.getTimeZone("CST"));
		return format.format(new Date());
	}

	public static String convertObjectToXML(Object object) {
		JAXBContext jaxbContext;
		StringWriter stringWriter = new StringWriter();
		try {
			jaxbContext = JAXBContext.newInstance(object.getClass());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.marshal(object, stringWriter);
			return stringWriter.toString();
		} catch (JAXBException e) {
			log.info("XML parsing exception : " + e);
			return "";
		}
	}

	/*
	 * public static Object convertXMLToObject(String xmlSource) { JAXBContext
	 * jaxbContext; try { jaxbContext =
	 * JAXBContext.newInstance(xmlSource.getClass()); Unmarshaller jaxbUnmarshaller
	 * = jaxbContext.createUnmarshaller(); return jaxbUnmarshaller.unmarshal(new
	 * StringReader(xmlSource)); } catch (JAXBException e) { //throw new
	 * CintapBpiException(e.getMessage()); } }
	 * 
	 * @Autowired BpiErrorLogRepository bpiErrorLogRepository;
	 * 
	 * public void saveErrorLog(Exception exception) { StackTraceElement[]
	 * stackTrace = exception.getStackTrace(); StringWriter sw = new StringWriter();
	 * exception.printStackTrace(new PrintWriter(sw)); ErrorLog bpiErrorLog =
	 * ErrorLog.builder() .className(stackTrace[0].getClassName())
	 * .funName(stackTrace[0].getMethodName())
	 * .fileName(stackTrace[0].getFileName()) .errorDescription(sw.toString())
	 * .exception(exception.toString())
	 * .createdDate(TransportUtility.getCurrentDateTime()) .createdBy("System")
	 * .build(); log.info("Exception : {}",sw.toString());
	 * bpiErrorLogRepository.save(bpiErrorLog); }
	 * 
	 * public void saveErrorLog(Exception exception,String partnerId,String
	 * bpiLogId) { StackTraceElement[] stackTrace = exception.getStackTrace();
	 * StringWriter sw = new StringWriter(); exception.printStackTrace(new
	 * PrintWriter(sw));
	 * 
	 * ErrorLog bpiErrorLog = ErrorLog.builder()
	 * .className(stackTrace[0].getClassName())
	 * .funName(stackTrace[0].getMethodName())
	 * .fileName(stackTrace[0].getFileName()) .errorDescription(sw.toString())
	 * .exception(exception.toString()) .partnerId(partnerId) .bpiLogId(bpiLogId)
	 * .build(); bpiErrorLogRepository.save(bpiErrorLog); }
	 */

	/**
	 * UTC format returning
	 * 
	 * @return
	 */
	public static String getCurrentDateTime() {
		return Instant.now().toString();
	}

	/*
	 * public static String mmDDYYYY() { DateTimeFormatter oldPattern =
	 * DateTimeFormatter.ofPattern(CintapBpiConstants.DATE_MM_DD_YYYY_HH_MM_SS);
	 * LocalDateTime datetime = LocalDateTime.now(); return
	 * datetime.format(oldPattern); }
	 * 
	 * public static String YYmmDD() { SimpleDateFormat format = new
	 * SimpleDateFormat(CintapBpiConstants.DATE_YY_MM_DD);
	 * format.setTimeZone(TimeZone.getTimeZone("CST")); return format.format(new
	 * Date()); }
	 * 
	 * public static String ccyymmDD() { SimpleDateFormat format = new
	 * SimpleDateFormat(CintapBpiConstants.DATE_CCYY_MM_DD);
	 * format.setTimeZone(TimeZone.getTimeZone("CST")); return format.format(new
	 * Date()); }
	 * 
	 * public static String getCurrentDateTimeInCSZone() { SimpleDateFormat format =
	 * new SimpleDateFormat(CintapBpiConstants.DATE_MM_DD_YYYY_HH_MM_SS);
	 * format.setTimeZone(TimeZone.getTimeZone("CST")); return format.format(new
	 * Date()); }
	 * 
	 * public static String getCurrentTimeInCS() { SimpleDateFormat format = new
	 * SimpleDateFormat(CintapBpiConstants.HH_MM);
	 * format.setTimeZone(TimeZone.getTimeZone("CST")); return format.format(new
	 * Date()); }
	 * 
	 * public static String TIME_HHMM() { SimpleDateFormat format = new
	 * SimpleDateFormat(CintapBpiConstants.TIME_HHMM);
	 * format.setTimeZone(TimeZone.getTimeZone("CST")); return format.format(new
	 * Date()); }
	 * 
	 * public static String TIME_HHMMSSSSS() { SimpleDateFormat format = new
	 * SimpleDateFormat(CintapBpiConstants.TIME_HHMMSSSSS);
	 * format.setTimeZone(TimeZone.getTimeZone("CST")); return format.format(new
	 * Date()); }
	 * 
	 * public static String getFileDateFormatInCS() { SimpleDateFormat format = new
	 * SimpleDateFormat(CintapBpiConstants.DATE_DDMMYYYYHHMMSS);
	 * format.setTimeZone(TimeZone.getTimeZone("CST")); return format.format(new
	 * Date()); }
	 * 
	 * public static String getDayOfWeek() { SimpleDateFormat simpleDateformat = new
	 * SimpleDateFormat(CintapBpiConstants.DAY_OF_WEEK); return
	 * simpleDateformat.format(new Date()); }
	 * 
	 * public static String formatDateString_CCYYMMDD(String date) { date =
	 * date.replace("-",""); if(date.length()==8) { String year = date.substring(0,
	 * 4); String month = date.substring(4, 6); String day = date.substring(6, 8);
	 * return year+""+month+""+day; }else { return date; } }
	 */

	public static String formatDateString(String date) {
		if (date.length() == 8) {
			String month = date.substring(0, 2);
			String day = date.substring(2, 4);
			String year = date.substring(4, 8);
			return year + "" + month + "" + day;
		} else {
			return date;
		}
	}

	public static String convertXmlOrderDate(String date) {
		if (StringUtils.isNoneBlank(date) && date.length() == 8) {
			String year = date.substring(0, 4);
			String month = date.substring(4, 6);
			String day = date.substring(6, 8);
			return year + "-" + month + "-" + day;
		} else {
			return date;
		}
	}

	public static String formatTimeString(String time) {
		if (time.length() == 8) {
			String hr = time.substring(0, 2);
			String minutes = time.substring(3, 5);
			return hr + "" + minutes;
		} else {
			return time;
		}
	}
	
	public static String formatXmlCharacters(String data) {
		if (StringUtils.isNoneEmpty(data)) {
			data = data.replace("&", "&amp;").replace("\'", "&apos;").replace(">", "&gt;").replace("<", "&lt;")
					.replace("\"", "&quot;");
		}
		return data;
	}
}
