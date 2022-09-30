package com.cintap.transport.message.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileProcessResponse {
	Integer batchId;
	String functionalAck997Response;
}
