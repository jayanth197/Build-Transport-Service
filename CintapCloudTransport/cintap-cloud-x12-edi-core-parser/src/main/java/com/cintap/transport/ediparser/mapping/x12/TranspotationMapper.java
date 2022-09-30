package com.cintap.transport.ediparser.mapping.x12;

import java.util.ArrayList;
import java.util.List;

import com.cintap.transport.ediparser.dto.global.EdiBase;
import com.cintap.transport.ediparser.dto.global.EdiResponse;
import com.cintap.transport.ediparser.dto.global.Ediroot;
import com.cintap.transport.ediparser.dto.global.Group;
import com.cintap.transport.ediparser.dto.global.HeaderAddress;
import com.cintap.transport.ediparser.dto.global.Interchange;
import com.cintap.transport.ediparser.dto.global.Segment;
import com.cintap.transport.ediparser.dto.global.Transaction;
import com.cintap.transport.ediparser.dto.segment.x12.AT7;
import com.cintap.transport.ediparser.dto.segment.x12.AT8;
import com.cintap.transport.ediparser.dto.segment.x12.K1;
import com.cintap.transport.ediparser.dto.segment.x12.L11;
import com.cintap.transport.ediparser.dto.segment.x12.LX;
import com.cintap.transport.ediparser.dto.segment.x12.MS3;
import com.cintap.transport.ediparser.dto.segment.x12.N3;
import com.cintap.transport.ediparser.dto.segment.x12.Q7;
import com.cintap.transport.ediparser.dto.transaction.x12.TransportationCarrierShipmentStatusMessage214;

public class TranspotationMapper {

	public TranspotationMapper() {
		// TODO Auto-generated constructor stub
	}

	public List<TransportationCarrierShipmentStatusMessage214> Transpotation214Mapper(EdiResponse ediResponse) {

		return map(ediResponse.getEdibase());

	}

	public boolean isMultiple(EdiResponse ediResponse) {		
		return ediResponse.getEdibase().getEdiroot().getInterchange().getGroup().getTransaction()!=null;
	}

	public List<TransportationCarrierShipmentStatusMessage214> map(EdiBase ediBase) {
		List<TransportationCarrierShipmentStatusMessage214> lst214=new ArrayList<TransportationCarrierShipmentStatusMessage214>();
		Ediroot ediroot=ediBase.getEdiroot();
		Interchange interchange=ediroot.getInterchange();
		Group group=interchange.getGroup();
		//ArrayList<Map> transactionLst=(ArrayList<Map>)group.getTransaction();		
		List<Transaction> transactionLst=group.getTransactionLst();		

		if(transactionLst!=null) {
			for(Transaction transaction:transactionLst ) { 	
				TransportationCarrierShipmentStatusMessage214 trans214=new TransportationCarrierShipmentStatusMessage214();
				trans214.setIsaSegment(SegmentMapper.mapISA(interchange));
				trans214.setGsSegment(SegmentMapper.mapGS(interchange));
				trans214.setStSegment(SegmentMapper.mapST(transaction));	

				trans214=mapSegmentsFromBase(transaction,trans214);		

				trans214.setSe(SegmentMapper.mapSE(transaction));	
				trans214.setGeSegment(SegmentMapper.mapGE(group));
				trans214.setIeaSegment(SegmentMapper.mapIEA(interchange));
				lst214.add(trans214);
			}
		}
		return lst214;
	}


	public TransportationCarrierShipmentStatusMessage214 mapSegmentsFromBase(Transaction transaction, TransportationCarrierShipmentStatusMessage214 trans214) {
		int lxLength=0;
		List<LX> lstLXSegment = new ArrayList<>();
		List<L11> lstL11Segment = new ArrayList<>();
		List<K1> lstK1Segment = new ArrayList<>();
		List<Q7> lstQ7Segment = new ArrayList<>();
		List<AT8> lstAT8Segment = new ArrayList<>();
		AT7 at7 = new AT7();
		List<L11> headerL11 = new ArrayList<>();
		List<HeaderAddress> lstHeaderAddress=new ArrayList<>();
		HeaderAddress headerAddress=new HeaderAddress();
		List<N3> lsN3Segment=new ArrayList<>();
		List<MS3> lstMS3Segment=new ArrayList<>();
		int at8segmentCount=getSegmentCount(transaction,"AT8");
			
		for (Segment seg : transaction.getSegment()) {	
			switch(seg.getId()) {
			case "B10":
				trans214.setB10(SegmentMapper.mapB10(seg.getElement()));
				break;
			case "L11":
				if(lstLXSegment.size()==0) {
					headerL11.add(SegmentMapper.mapL11(seg.getElement()));
					//trans214.getLstL11Segment().add(SegmentMapper.mapL11(seg.getElement()));
				}else {					
					lstL11Segment.add(SegmentMapper.mapL11(seg.getElement()));
				}
				break;
			case "K1":
				lstK1Segment.add(SegmentMapper.mapK1(seg.getElement()));
				break;		
			case "N1":
				headerAddress.setN1Segment(SegmentMapper.mapN1(seg.getElement()));		
				break;	
			case "N3":
				//headerAddress.getLstN3Segment().add(SegmentMapper.mapN3(seg.getElement()));
				lsN3Segment.add(SegmentMapper.mapN3(seg.getElement()));
				break;	
			case "N4":
				headerAddress.setN4Segment(SegmentMapper.mapN4(seg.getElement()));	
				headerAddress.setLstN3Segment(lsN3Segment);
				lstHeaderAddress.add(headerAddress);
				headerAddress=new HeaderAddress(); 
				lsN3Segment=new ArrayList<>();

				break;	
			case "MS3":
				//headerAddress.getLstMS3Segment().add(SegmentMapper.mapMS3(seg.getElement()));
				lstMS3Segment.add(SegmentMapper.mapMS3(seg.getElement()));
				break;
			case "LX":				
				lstLXSegment.add(SegmentMapper.mapLX(seg.getElement()));

				break;
			case "AT7":				
				lstLXSegment.get(lxLength).setAt7Segment(SegmentMapper.mapAT7(seg.getElement()));
				break;
			case "MS1":				
				lstLXSegment.get(lxLength).setMs1Segment(SegmentMapper.mapMS1(seg.getElement()));
				break;
			case "MS2":				
				lstLXSegment.get(lxLength).setMs2Segment(SegmentMapper.mapMS2(seg.getElement()));
				break;

			case "Q7":				
				lstQ7Segment.add(SegmentMapper.mapQ7(seg.getElement()));
				break;
			case "AT8":						
				lstAT8Segment.add(SegmentMapper.mapAT8(seg.getElement()));
				if(lstLXSegment.size()>0 && at8segmentCount==lstAT8Segment.size()) {
					lstLXSegment.get(lxLength).setLstL11Segment(lstL11Segment);
					lstLXSegment.get(lxLength).setLstQ7Segment(lstQ7Segment);
					lstLXSegment.get(lxLength).setLstAT8Segment(lstAT8Segment);
					lstL11Segment= new ArrayList<L11>();
					lstQ7Segment= new ArrayList<Q7>();
					lstAT8Segment= new ArrayList<AT8>();
					++lxLength;
				}
				break;
			default:
				// code block
			}

		}
		trans214.setLstMS3Segment(lstMS3Segment);
		trans214.setLstLXSegment(lstLXSegment);
		trans214.setLstHeaderAddress(lstHeaderAddress);
		trans214.setLstL11Segment(headerL11);
		return trans214;
	}
	public int getSegmentCount(Transaction transaction,String segment) {
		int count=0;
		for (Segment seg : transaction.getSegment()) {	
			if(seg.getId().equals(segment)) {
			++count;
			}
		}
			
		return count;
		
	}


}
