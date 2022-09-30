package com.cintap.transport.ediparser.dto.global;

public class Interchange{

	public String Authorization;
	public String AuthorizationQual;
	public String Control;
	public String Date;
	public String Security;
	public String SecurityQual;
	public String Standard;
	public String StandardsId;
	public String Time;
	public String Version;
	public Group group;
	public Receiver receiver;
	public Sender sender;
	public String getAuthorization() {
		return Authorization;
	}
	public void setAuthorization(String authorization) {
		Authorization = authorization;
	}
	public String getAuthorizationQual() {
		return AuthorizationQual;
	}
	public void setAuthorizationQual(String authorizationQual) {
		AuthorizationQual = authorizationQual;
	}
	public String getControl() {
		return Control;
	}
	public void setControl(String control) {
		Control = control;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getSecurity() {
		return Security;
	}
	public void setSecurity(String security) {
		Security = security;
	}
	public String getSecurityQual() {
		return SecurityQual;
	}
	public void setSecurityQual(String securityQual) {
		SecurityQual = securityQual;
	}
	public String getStandard() {
		return Standard;
	}
	public void setStandard(String standard) {
		Standard = standard;
	}
	public String getStandardsId() {
		return StandardsId;
	}
	public void setStandardsId(String standardsId) {
		StandardsId = standardsId;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public Receiver getReceiver() {
		return receiver;
	}
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}
	public Sender getSender() {
		return sender;
	}
	public void setSender(Sender sender) {
		this.sender = sender;
	}
	
	
	
}
