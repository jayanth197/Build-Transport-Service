package com.cintap.transport.ediparser.dto.global;

public class Segment{
    
    public String Id;
    public Object element;
    
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public Object getElement() {
		return element;
	}
	public void setElement(Object element) {
		this.element = element;
	}
    
}
