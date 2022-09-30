/**
 * 
 */
package com.cintap.transport.global.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author SurenderMogiloju
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GSSegmentInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String gs07ResponsibleAgencyCode;
	private String gs04Date;
	private String gs01FunctionalIdentifierCode;
	private String gs05Time;
	private String gs02ApplicationSenderCode;
	private String gs08Version;
	private String gs06GroupControlNumber;
	private String gs03ApplicationReceiverCode;
	
}
