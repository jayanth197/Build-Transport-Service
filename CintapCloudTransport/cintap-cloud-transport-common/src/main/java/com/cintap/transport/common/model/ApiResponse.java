package com.cintap.transport.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ApiResponse implements Serializable{
	private static final long serialVersionUID = 1609269779636510131L;
	private String statusCode;
	private String statusMessage;
	private Integer bpiTransId;
	private String authToken;
	private Object result;
}
