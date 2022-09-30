package com.cintap.transport.ediparser.dto.global; 
import java.util.List; 
public class Group{

    public String ApplReceiver;
    public String ApplSender;
    public String Control;
    public String Date;
    public String GroupType;
    public String StandardCode;
    public String StandardVersion;
    public String Time;
    public Object transaction;
    public List<Transaction> transactionLst;
    
    
	public String getApplReceiver() {
		return ApplReceiver;
	}
	public void setApplReceiver(String applReceiver) {
		ApplReceiver = applReceiver;
	}
	public String getApplSender() {
		return ApplSender;
	}
	public void setApplSender(String applSender) {
		ApplSender = applSender;
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
	public String getGroupType() {
		return GroupType;
	}
	public void setGroupType(String groupType) {
		GroupType = groupType;
	}
	public String getStandardCode() {
		return StandardCode;
	}
	public void setStandardCode(String standardCode) {
		StandardCode = standardCode;
	}
	public String getStandardVersion() {
		return StandardVersion;
	}
	public void setStandardVersion(String standardVersion) {
		StandardVersion = standardVersion;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public Object getTransaction() {
		return transaction;
	}
	public void setTransaction(Object transaction) {
		this.transaction = transaction;			
	}
	public List<Transaction> getTransactionLst() {
		return transactionLst;
	}
	public void setTransactionLst(List<Transaction> transactionLst) {
		this.transactionLst = transactionLst;
	}
	
    
}
