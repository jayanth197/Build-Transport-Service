package com.cintap.transport.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLogParams implements Serializable{
	private static final long serialVersionUID = -4641541906619656567L;
	private String isaHeader;
	private String gsHeader;
	private String isaTrailer;
	private String gsTrailer;
	private String transactionBuilder;
}
