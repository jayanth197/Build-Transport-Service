package com.cintap.transport.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;

import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.common.EmailLog;
import com.cintap.transport.repository.common.EmailLogRepository;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class MailUtility {

	private JavaMailSender javaMailSender;
	private EmailLogRepository emailLogRepository;
	private String result = null;
	public static final String FAIL = "FAIL";
	public static final String SUCCESS = "SUCCESS";

	public MailUtility(JavaMailSender javaMailSender,EmailLogRepository emailLogRepository) {
		this.javaMailSender = javaMailSender;
		this.emailLogRepository = emailLogRepository;
	}

	@Async
	public String sendEmail(String toEmail, String subject, String body,String type) {
		try {
			log.info("Initiated email sending");
			MimeMessagePreparator messagePreparator = mimeMessage -> {
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
				messageHelper.setFrom("support@cintap.com");
				messageHelper.setTo(toEmail);
				messageHelper.setSubject(subject);
				messageHelper.setText(body, true);
			};

			javaMailSender.send(messagePreparator);
			log.info("Email sent successfully");
			//Log email details into database
			EmailLog emailLog = EmailLog.builder()
					.emailAddress(toEmail)
					.emailBody(body)
					.type(type)
					.createdDate(TransportCommonUtility.getCurrentDateTime())
					.createdBy("SYSTEM")
					.updatedDate(TransportCommonUtility.getCurrentDateTime())
					.updatedBy("SYSTEM")
					.status(1)
					.build();
			emailLogRepository.save(emailLog);
			result = SUCCESS;
		} catch (MailException exception) {
			StringWriter sw = new StringWriter();
			exception.printStackTrace(new PrintWriter(sw));
			log.error("Unable to send an email to :" + toEmail + "; Exception is : " + exception);
			log.info("MailUtility :: Exception :: ", sw.toString());
		} catch (Exception exception1) {
			log.error("Unable to send an email to :" + toEmail + "; Exception is : " + exception1);
			log.info("MailUtility :: Exception :: ", exception1.getMessage());
			result = FAIL;
		}
		return result;
	}
}
