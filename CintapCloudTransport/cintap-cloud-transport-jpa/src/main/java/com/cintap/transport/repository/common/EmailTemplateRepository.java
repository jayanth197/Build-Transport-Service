package com.cintap.transport.repository.common;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cintap.transport.entity.common.EmailTemplate;

public interface EmailTemplateRepository extends CrudRepository<EmailTemplate, Long> {
	Optional<EmailTemplate> findByTemplateName(@Param("templateName") String templateName);
	Optional<List<EmailTemplate>> findByStatus(int status);
}
