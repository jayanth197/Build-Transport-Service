package com.cintap.transport.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cintap.transport.entity.trans.TransactionTrail;

@Repository
public interface TransactionTrailRepository extends JpaRepository<TransactionTrail,Integer>{

	Optional<List<TransactionTrail>> findBySenderPartner(String partnerId);
	
	/**
	 * Return transaction trail based on BpiLogId
	 * @param bpiLogId
	 * @return
	 */
	Optional<TransactionTrail> findByBpiLogId(Integer bpiLogId);
	
	Optional<List<TransactionTrail>> findByMasterBpiLogId(Integer bpiLogId);
	
	/**
	 * Calling this method to identify if there is any master record(204) existed for the current transaction
	 * @param masterTransId
	 * @param senderPartner
	 * @param receiverPartner
	 * @return
	 */
	@Query(value="SELECT * FROM transaction_trail t WHERE t.master_trans_id=:masterTransId AND (t.sender_partner =:senderPartner OR t.receiver_partner =:senderPartner) AND (t.sender_partner =:receiverPartner OR t.receiver_partner =:receiverPartner) and is_master=1 ",nativeQuery = true)
	Optional<TransactionTrail> findByTrail(String masterTransId,String senderPartner,String receiverPartner);
	
}
