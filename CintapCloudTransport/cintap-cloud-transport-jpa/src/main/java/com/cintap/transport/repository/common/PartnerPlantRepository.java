package com.cintap.transport.repository.common;

import java.util.Optional;

import com.cintap.transport.entity.common.PartnerPlants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PartnerPlantRepository extends JpaRepository<PartnerPlants, Integer>{

	@Query(value="select p.plantName from PartnerPlants p where p.partnerId = :partnerId and p.plantCode = :plantCode")
	Optional<String> findNameByPartnerIdAndPartnerCode(@Param("partnerId") String partnerId, @Param("plantCode") String partnerCode);

	@Query(value="select p.plantCode from PartnerPlants p where p.partnerId = :partnerId and p.plantName = :plantName")
	Optional<String> findCodeByPartnerIdAndPartnerName(@Param("partnerId") String partnerId, @Param("plantName") String plantName);
	
}
