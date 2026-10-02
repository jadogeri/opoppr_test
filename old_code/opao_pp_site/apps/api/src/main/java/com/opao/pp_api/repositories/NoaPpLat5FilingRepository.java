package com.opao.pp_api.repositories;

import com.opao.pp_api.repositories.entities.NoaPpLat5FilingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoaPpLat5FilingRepository extends JpaRepository<NoaPpLat5FilingEntity, Integer> {

    // Note: Standard findAll() is already provided by JpaRepository

    // SELECT n FROM NoaPpLat5Filing n WHERE n.noaPpLat5FilingId = :noaPpLat5FilingId
    Optional<NoaPpLat5FilingEntity> findByNoaPpLat5FilingId(Integer noaPpLat5FilingId);

    // SELECT n FROM NoaPpLat5Filing n WHERE n.jur = :jur
    List<NoaPpLat5FilingEntity> findByJur(String jur);

    // SELECT n FROM NoaPpLat5Filing n WHERE n.parid = :parid
    List<NoaPpLat5FilingEntity> findByParid(String parid);

    // SELECT n FROM NoaPpLat5Filing n WHERE n.taxyr = :taxyr
    List<NoaPpLat5FilingEntity> findByTaxyr(Integer taxyr);

    // SELECT n FROM NoaPpLat5Filing n WHERE n.category = :category
    List<NoaPpLat5FilingEntity> findByCategory(String category);

    // SELECT n FROM NoaPpLat5Filing n WHERE n.pptype = :pptype
    List<NoaPpLat5FilingEntity> findByPptype(String pptype);

    // SELECT n FROM NoaPpLat5Filing n WHERE n.fileyr = :fileyr
    List<NoaPpLat5FilingEntity> findByFileyr(Integer fileyr);

    // SELECT n FROM NoaPpLat5Filing n WHERE n.yracqd = :yracqd
    List<NoaPpLat5FilingEntity> findByYracqd(Integer yracqd);

    // SELECT n FROM NoaPpLat5Filing n WHERE n.nounits = :nounits
    List<NoaPpLat5FilingEntity> findByNounits(Integer nounits);

    // SELECT n FROM NoaPpLat5Filing n WHERE n.acquisitionCost = :acquisitionCost
    List<NoaPpLat5FilingEntity> findByAcquisitionCost(BigDecimal acquisitionCost);

    // SELECT n FROM NoaPpLat5Filing n WHERE n.effectiveLife = :effectiveLife
    List<NoaPpLat5FilingEntity> findByEffectiveLife(Integer effectiveLife);

    // SELECT n FROM NoaPpLat5Filing n WHERE n.consignerOwnerName = :consignerOwnerName
    List<NoaPpLat5FilingEntity> findByConsignerOwnerName(String consignerOwnerName);

    // SELECT n FROM NoaPpLat5Filing n WHERE n.consignerMailingAddr = :consignerMailingAddr
    List<NoaPpLat5FilingEntity> findByConsignerMailingAddr(String consignerMailingAddr);

    // SELECT n FROM NoaPpLat5Filing n WHERE n.consignerRentalAmt = :consignerRentalAmt
    List<NoaPpLat5FilingEntity> findByConsignerRentalAmt(BigDecimal consignerRentalAmt);

    // SELECT n FROM NoaPpLat5Filing n WHERE n.itemDescription = :itemDescription
    List<NoaPpLat5FilingEntity> findByItemDescription(String itemDescription);

    // SELECT n FROM NoaPpLat5Filing n WHERE n.consignerTelNo = :consignerTelNo
    List<NoaPpLat5FilingEntity> findByConsignerTelNo(String consignerTelNo);
}
