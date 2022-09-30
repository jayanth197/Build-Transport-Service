/**
 * 
 */
package com.cintap.transport.common.ftl.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SurenderMogiloju
 *
 */
@Service
@Slf4j
public class FTLTemplateUtil {

	private Map<String, Template> ftlTemplates = new ConcurrentHashMap<>();

	public Template getFTLTemplateByName(String templateName) throws Exception {
		if (null != ftlTemplates.get(templateName)) {
			return ftlTemplates.get(templateName);
		} else {
			log.info(templateName + " not exist, so creating new ");
			Configuration cfg = new Configuration(new Version("2.3.23"));
			cfg.setClassForTemplateLoading(FTLTemplateUtil.class, "/templates");
			cfg.setDefaultEncoding("UTF-8");
			Template template = cfg.getTemplate(templateName);
			ftlTemplates.put(templateName, template);
			return template;
		}
	}

}
