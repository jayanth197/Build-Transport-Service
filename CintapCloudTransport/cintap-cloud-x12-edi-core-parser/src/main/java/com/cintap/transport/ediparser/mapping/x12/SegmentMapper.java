package com.cintap.transport.ediparser.mapping.x12;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cintap.transport.ediparser.dto.global.EdiBase;
import com.cintap.transport.ediparser.dto.global.Group;
import com.cintap.transport.ediparser.dto.global.Interchange;
import com.cintap.transport.ediparser.dto.global.Transaction;
import com.cintap.transport.ediparser.dto.segment.x12.AT7;
import com.cintap.transport.ediparser.dto.segment.x12.AT8;
import com.cintap.transport.ediparser.dto.segment.x12.B10;
import com.cintap.transport.ediparser.dto.segment.x12.GE;
import com.cintap.transport.ediparser.dto.segment.x12.GS;
import com.cintap.transport.ediparser.dto.segment.x12.IEA;
import com.cintap.transport.ediparser.dto.segment.x12.ISA;
import com.cintap.transport.ediparser.dto.segment.x12.K1;
import com.cintap.transport.ediparser.dto.segment.x12.L11;
import com.cintap.transport.ediparser.dto.segment.x12.LX;
import com.cintap.transport.ediparser.dto.segment.x12.MS1;
import com.cintap.transport.ediparser.dto.segment.x12.MS2;
import com.cintap.transport.ediparser.dto.segment.x12.MS3;
import com.cintap.transport.ediparser.dto.segment.x12.N1;
import com.cintap.transport.ediparser.dto.segment.x12.N3;
import com.cintap.transport.ediparser.dto.segment.x12.N4;
import com.cintap.transport.ediparser.dto.segment.x12.Q7;
import com.cintap.transport.ediparser.dto.segment.x12.SE;
import com.cintap.transport.ediparser.dto.segment.x12.ST;

public class SegmentMapper {

	public SegmentMapper() {
	}
	
	public static ST mapST(EdiBase ediBase) {
		return null;
	}
	
	public static B10 mapB10(Object b10Elements) {
		ArrayList<Map> lst=(ArrayList<Map>)b10Elements;		
		B10 b10 = new B10(lst);		
		return b10;
	}
	
	public static LX mapLX(Object b10Elements) {
		Map lst=(Map)b10Elements;		
		LX lx = new LX(lst);		
		return lx;
	}
	
	public static AT7 mapAT7(Object b10Elements) {
		ArrayList<Map> lst=(ArrayList<Map>)b10Elements;		
		AT7 at7 = new AT7(lst);		
		return at7;
	}
	
	public static L11 mapL11(Object b10Elements) {
		ArrayList<Map> lst=(ArrayList<Map>)b10Elements;		
		L11 l11 = new L11(lst);		
		return l11;
	}	
	
	public static ISA mapISA(Interchange interchange) {		
		ISA isa = new ISA(interchange);		
		return isa;
	}

	public static GS mapGS(Interchange interchange) {
		GS gs = new GS(interchange);		
		return gs;
	}

	public static ST mapST(Transaction transaction) {
		ST st = new ST(transaction);		
		return st;
	}

	public static SE mapSE(Transaction transaction) {
		SE se=new SE(transaction);
		return se;
	}

	public static GE mapGE(Group group) {
		GE ge=new GE(group);
		return ge;
	}

	public static IEA mapIEA(Interchange interchange) {
		IEA iea=new IEA(interchange);
		return iea;
	}

	public static K1 mapK1(Object element) {
		ArrayList<Map> lst=getListFromObject(element);				
		K1 k1=new K1(lst);
		return k1;
	}

	public static N1 mapN1(Object element) {
		// TODO Auto-generated method stub
		ArrayList<Map> lst=getListFromObject(element);				
		N1 n1=new N1(lst);
		return n1;
	}

	public static N3 mapN3(Object element) {
		ArrayList<Map> lst=getListFromObject(element);				
		N3 n3=new N3(lst);
		return n3;

	}

	public static N4 mapN4(Object element) {
		ArrayList<Map> lst=getListFromObject(element);				
		N4 n4=new N4(lst);
		return n4;
	}
	public static MS1 mapMS1(Object element) {
		ArrayList<Map> lst=getListFromObject(element);				
		MS1 ms1=new MS1(lst);
		return ms1;
	}	
	public static MS2 mapMS2(Object element) {
		ArrayList<Map> lst=getListFromObject(element);				
		MS2 ms2=new MS2(lst);
		return ms2;
	}	
	public static MS3 mapMS3(Object element) {
		ArrayList<Map> lst=getListFromObject(element);				
		MS3 ms3=new MS3(lst);
		return ms3;
	}

	public static Q7 mapQ7(Object element) {
		ArrayList<Map> lst=getListFromObject(element);				
		Q7 q7=new Q7(lst);
		return q7;
	}

	public static AT8 mapAT8(Object element) {
		ArrayList<Map> lst=getListFromObject(element);				
		AT8 at8=new AT8(lst);
		return at8;
	}	
	
	public static ArrayList<Map> getListFromObject(Object element){		
		ArrayList<Map> lst=new ArrayList<>();
		
		 
		    if (element instanceof List<?>) {
		    	lst=(ArrayList<Map>)element;	
		       
		    }else {
		    	lst.add((Map) element);
		    }
			return lst;
		
	}

}
