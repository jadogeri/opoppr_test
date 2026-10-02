package com.opao.pp_api.repositories;

import com.opao.pp_api.repositories.entities.NoaPpLat5Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoaPpLat5Repository extends JpaRepository<NoaPpLat5Entity, Integer> {

    // Note: Standard findAll() is already provided by JpaRepository

    // SELECT n FROM NoaPpLat5 n WHERE n.noaPpLat5Id = :noaPpLat5Id
    Optional<NoaPpLat5Entity> findByNoaPpLat5Id(Integer noaPpLat5Id);

    // SELECT n FROM NoaPpLat5 n WHERE n.jur = :jur
    List<NoaPpLat5Entity> findByJur(String jur);

    // SELECT n FROM NoaPpLat5 n WHERE n.parid = :parid
    List<NoaPpLat5Entity> findByParid(String parid);

    // SELECT n FROM NoaPpLat5 n WHERE n.altid = :altid
    List<NoaPpLat5Entity> findByAltid(String altid);

    // SELECT n FROM NoaPpLat5 n WHERE n.taxyr = :taxyr
    List<NoaPpLat5Entity> findByTaxyr(Integer taxyr);

    // SELECT n FROM NoaPpLat5 n WHERE n.ownername = :ownername
    List<NoaPpLat5Entity> findByOwnername(String ownername);

    // SELECT n FROM NoaPpLat5 n WHERE n.businessType.businessCode = :businessCode
    // (Spring Data uses underscores or property names to traverse object graphs: n.businessType.businessCode)
    List<NoaPpLat5Entity> findByBusinessTypeBusinessCode(Integer businessCode);

    // SELECT n FROM NoaPpLat5 n WHERE n.addr1 = :addr1
    List<NoaPpLat5Entity> findByAddr1(String addr1);

    // SELECT n FROM NoaPpLat5 n WHERE n.addr2 = :addr2
    List<NoaPpLat5Entity> findByAddr2(String addr2);

    // SELECT n FROM NoaPpLat5 n WHERE n.cityname = :cityname
    List<NoaPpLat5Entity> findByCityname(String cityname);

    // SELECT n FROM NoaPpLat5 n WHERE n.statecode = :statecode
    List<NoaPpLat5Entity> findByStatecode(String statecode);

    // SELECT n FROM NoaPpLat5 n WHERE n.zip1 = :zip1
    List<NoaPpLat5Entity> findByZip1(String zip1);

    // SELECT n FROM NoaPpLat5 n WHERE n.pin = :pin
    List<NoaPpLat5Entity> findByPin(String pin);

    // SELECT n FROM NoaPpLat5 n WHERE n.contactName = :contactName
    List<NoaPpLat5Entity> findByContactName(String contactName);

    // SELECT n FROM NoaPpLat5 n WHERE n.contactPhone = :contactPhone
    List<NoaPpLat5Entity> findByContactPhone(String contactPhone);

    // SELECT n FROM NoaPpLat5 n WHERE n.contactFax = :contactFax
    List<NoaPpLat5Entity> findByContactFax(String contactFax);

    // SELECT n FROM NoaPpLat5 n WHERE n.contactEmail = :contactEmail
    List<NoaPpLat5Entity> findByContactEmail(String contactEmail);

    // SELECT n FROM NoaPpLat5 n WHERE n.contactSendEmails = :contactSendEmails
    List<NoaPpLat5Entity> findByContactSendEmails(Boolean contactSendEmails);

    // SELECT n FROM n NoaPpLat5 n WHERE n.propertyAddress = :propertyAddress
    List<NoaPpLat5Entity> findByPropertyAddress(String propertyAddress);

    // SELECT n FROM NoaPpLat5 n WHERE n.taxpayerName = :taxpayerName
    List<NoaPpLat5Entity> findByTaxpayerName(String taxpayerName);

    // SELECT n FROM NoaPpLat5 n WHERE n.taxpayerPreparedDate = :taxpayerPreparedDate
    List<NoaPpLat5Entity> findByTaxpayerPreparedDate(Date taxpayerPreparedDate);

    // SELECT n FROM NoaPpLat5 n WHERE n.taxPreparerName = :taxPreparerName
    List<NoaPpLat5Entity> findByTaxPreparerName(String taxPreparerName);

    // SELECT n FROM NoaPpLat5 n WHERE n.taxPreparerPreparedDate = :taxPreparerPreparedDate
    List<NoaPpLat5Entity> findByTaxPreparerPreparedDate(Date taxPreparerPreparedDate);
}
