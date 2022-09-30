package com.cintap.transport.repository.common;

import java.util.Optional;

import com.cintap.transport.entity.common.EdiDelimiter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface EdiDelimiterRepository extends JpaRepository<EdiDelimiter, Integer>{
//	Optional<EdiDelimiter> findByPartnerIdAndEdiType(String partnerId,String ediType);
	//Optional<List<EdiDelimiter>> findByPartnerIdOrderByEdiDelimiterIdDesc(String partnerId);
	//@Query(value="select * from edi_delimiter where receiver_partner_id=:receiverPartnerId and edi_type=:ediType and edi_version=:ediVersion and receiver_isa_id:=receiverIsaId",nativeQuery = true)
	
	/*@Query(value="select e from EdiDelimiter e where e.partnerId=:partnerId and e.receiverPartnerId=:receiverPartnerId and e.ediType=:ediType and ediVersion=:ediVersion and receiverIsaId=:receiverIsaId")
	Optional<EdiDelimiter> findEdiDelimiter(@Param("partnerId") String partnerId,@Param("receiverPartnerId") String receiverPartnerId,@Param("ediType") String ediType,
			@Param("ediVersion") String ediVersion,@Param("receiverIsaId") String receiverIsaId);
	
	@Query(value="select e from EdiDelimiter e where e.receiverPartnerId = :receiverPartnerId and e.ediType=:ediType and ediVersion=:ediVersion")
	Optional<EdiDelimiter> findEdiDelimiterWithoutISAId(@Param("receiverPartnerId") String receiverPartnerId,@Param("ediType") String ediType,@Param("ediVersion") String ediVersion);
	
	@Query(value="select e from EdiDelimiter e where e.partnerId=:partnerId and e.receiverPartnerId = :receiverPartnerId and e.ediType=:ediType and e.fileType=:fileType")
	Optional<EdiDelimiter> findFFDelimter(@Param("partnerId") String partnerId,@Param("receiverPartnerId") String receiverPartnerId,@Param("ediType") String ediType,@Param("fileType") String fileType);

	@Query(value="select e from EdiDelimiter e where e.receiverPartnerId = :receiverPartnerId and e.ediType=:ediType and ediVersion=:ediVersion")
	Optional<EdiDelimiter> findEdiDelimiterWithFileType(@Param("receiverPartnerId") String receiverPartnerId,@Param("ediType") String ediType,@Param("ediVersion") String ediVersion); */

//	@Query(value="select e from EdiDelimiter e where e.partnerId=:partnerId and e.fileType=:fileType and e.direction=:direction and e.transactionType=:transactionType "
//			+ " and e.ediType=:ediType and e.ediVersion=:ediVersion")
//	Optional<EdiDelimiter> findEdiDelimiter(@Param("partnerId") String partnerId,@Param("fileType") String fileType,@Param("direction") String direction,
//			@Param("transactionType") String transactionType,@Param("ediType") String ediType,@Param("ediVersion") String ediVersion);
//	
//	@Query(value="select e from EdiDelimiter e where e.partnerId=:partnerId and e.fileType=:fileType and e.direction=:direction and e.transactionType=:transactionType "
//			+ " and e.ediType=:ediType")
//	Optional<EdiDelimiter> findEdiDelimiterWithoutEdiVersion(@Param("partnerId") String partnerId,@Param("fileType") String fileType,@Param("direction") String direction,
//			@Param("transactionType") String transactionType,@Param("ediType") String ediType);
//	
//	@Query(value="select e from EdiDelimiter e where e.partnerId=:partnerId and e.fileType=:fileType and e.direction=:direction and e.transactionType=:transactionType")
//	Optional<EdiDelimiter> findFlatFileDelimiter(@Param("partnerId") String partnerId,@Param("fileType") String fileType,@Param("direction") String direction,
//			@Param("transactionType") String transactionType);
//	
//	@Query(value="select e from EdiDelimiter e where e.senderPartnerId=:senderPartnerId and e.senderIsaId=:senderIsaId and e.receiverPartnerId=:receiverPartnerId "
//			+ " and e.receiverIsaId=:receiverIsaId and e.fileType=:fileType and e.direction=:direction and e.transactionType=:transactionType "
//			+ " and e.ediType=:ediType and e.ediVersion=:ediVersion and e.ediStandard=:ediStandard")
//	Optional<EdiDelimiter> findInboundEdiDelimiter(@Param("senderPartnerId") String senderPartnerId,@Param("senderIsaId") String senderIsaId,@Param("receiverPartnerId") String receiverPartnerId,
//			@Param("receiverIsaId") String receiverIsaId,@Param("fileType") String fileType,@Param("direction") String direction,@Param("transactionType") String transactionType,
//			@Param("ediType") String ediType,@Param("ediVersion") String ediVersion,@Param("ediStandard") String ediStandard);
//
//	@Query(value="select e from EdiDelimiter e where e.senderPartnerId=:senderPartnerId and e.senderIsaId=:senderIsaId and e.receiverPartnerId=:receiverPartnerId "
//			+ " and e.receiverIsaId=:receiverIsaId and e.fileType=:fileType and e.direction=:direction and e.transactionType=:transactionType "
//			+ " and e.ediType=:ediType and e.ediStandard=:ediStandard")
//	Optional<EdiDelimiter> findOutboundEdiDelimiter(@Param("senderPartnerId") String senderPartnerId,@Param("senderIsaId") String senderIsaId,@Param("receiverPartnerId") String receiverPartnerId,
//			@Param("receiverIsaId") String receiverIsaId,@Param("fileType") String fileType,@Param("direction") String direction,@Param("transactionType") String transactionType,
//			@Param("ediType") String ediType,@Param("ediStandard") String ediStandard);
//	
//	@Query(value="select e from EdiDelimiter e where e.senderPartnerId=:senderPartnerId and e.receiverPartnerId=:receiverPartnerId "
//			+ " and e.fileType=:fileType and e.direction=:direction and e.transactionType=:transactionType "
//			+ " and e.ediType=:ediType and e.ediStandard=:ediStandard")
//	Optional<EdiDelimiter> findOutboundEdiDelimiterWithoutIsa(@Param("senderPartnerId") String senderPartnerId,@Param("receiverPartnerId") String receiverPartnerId,
//			@Param("fileType") String fileType,@Param("direction") String direction,@Param("transactionType") String transactionType,
//			@Param("ediType") String ediType,@Param("ediStandard") String ediStandard);
//	
//	
//	@Query(value="select e from EdiDelimiter e where e.senderPartnerId=:senderPartnerId and e.receiverPartnerId=:receiverPartnerId and e.fileType=:fileType "
//			+ "and e.direction=:direction and e.transactionType=:transactionType")
//	Optional<EdiDelimiter> findInboundFlatFileDelimiter(@Param("senderPartnerId") String senderPartnerId,@Param("receiverPartnerId") String receiverPartnerId,
//			@Param("fileType") String fileType,@Param("direction") String direction,
//			@Param("transactionType") String transactionType);
	
	//pagination
	
	//Page<EdiDelimiter> findAll(Pageable page);
	
	Page<EdiDelimiter> findByPartnerIdOrderByEdiDelimiterIdDesc(String partnerId,Pageable page);
	
	@Query(value="select e from EdiDelimiter e where e.senderPartnerId=:senderPartnerId and e.receiverPartnerId=:receiverPartnerId "
			+ " and e.fileType=:fileType and e.ediStandard=:ediStandard")
	EdiDelimiter findDelimiterByPartner(@Param("senderPartnerId") String senderPartnerId, @Param("receiverPartnerId") String receiverPartnerId,
			@Param("fileType") String fileType, @Param("ediStandard") String ediStandard);
}
