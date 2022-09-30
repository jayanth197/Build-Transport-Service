package com.cintap.transport.ediparser.dto.global; 
import java.util.ArrayList; 
public class Transaction{

    public int Control;
    public int DocType;
    
    public ArrayList<Segment> segment;
    
	public int getControl() {
		return Control;
	}
	public void setControl(int control) {
		Control = control;
	}
	public int getDocType() {
		return DocType;
	}
	public void setDocType(int docType) {
		DocType = docType;
	}
	public ArrayList<Segment> getSegment() {
		return segment;
	}
	public void setSegment(ArrayList<Segment> segment) {
		this.segment = segment;
	}
    
}
