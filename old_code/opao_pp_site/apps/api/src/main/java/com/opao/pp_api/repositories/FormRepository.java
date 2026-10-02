package com.opao.pp_api.repositories;

import com.opao.pp_api.repositories.entities.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<FormEntity, Integer> {

    // Note: Standard findAll() is already provided by JpaRepository

    // SELECT f FROM Form f WHERE f.formId = :formId
    Optional<FormEntity> findByFormId(Integer formId);

    // SELECT f FROM Form f WHERE f.title = :title
    List<FormEntity> findByTitle(String title);

    // SELECT f FROM Form f WHERE f.filingYear = :filingYear
    List<FormEntity> findByFilingYear(Integer filingYear);

    // SELECT f FROM Form f WHERE f.billNumber = :billNumber
    List<FormEntity> findByBillNumber(String billNumber);

    // SELECT f FROM Form f WHERE f.billNumber = :billNumber AND f.pin = :pin
    List<FormEntity> findByBillNumberAndPin(String billNumber, String pin);

    // SELECT f FROM Form f WHERE f.billNumber = :billNumber AND f.filingYear = :filingYear
    List<FormEntity> findByBillNumberAndFilingYear(String billNumber, Integer filingYear);

    // SELECT f FROM Form f WHERE f.filingYear = :filingYear AND f.billNumber = :billNumber AND f.pin = :pin
    List<FormEntity> findByFilingYearAndBillNumberAndPin(Integer filingYear, String billNumber, String pin);

    // SELECT f FROM Form f WHERE f.billNumber = :billNumber AND f.pin = :pin AND f.status.name = :statusName
    // (Spring Data handles nested object properties automatically via f.status.name -> StatusName)
    List<FormEntity> findByBillNumberAndPinAndStatusName(String billNumber, String pin, String statusName);
}
