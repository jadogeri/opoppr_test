package com.opao.pp_api.services.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoaPpLat5 {
    private Integer id;
    private String jurisdiction;       // Cleaned up 'jur'
    private String parcelId;           // Cleaned up 'parid'
    private String alternateId;        // Cleaned up 'altid'
    private int taxYear;               // Cleaned up 'taxyr'
    private String ownerName;          // Cleaned up 'ownername'
    private String address1;           // Cleaned up 'addr1'
    private String address2;           // Cleaned up 'addr2'
    private String cityName;           // Cleaned up 'cityname'
    private String stateCode;          // Cleaned up 'statecode'
    private String zipCode;            // Cleaned up 'zip1'
    private String pin;
    
    // Contact Info
    private String contactName;
    private String contactPhone;
    private String contactFax;
    private String contactEmail;
    private Boolean contactSendEmails;
    
    // Property & Taxpayer info
    private String propertyAddress;
    private String taxpayerName;
    private LocalDate taxpayerPreparedDate;
    
    // Preparer Info
    private String taxPreparerName;
    private String taxPreparerPhone;
    private String taxPreparerEmail;
    private LocalDate taxPreparerPreparedDate;
    
    // Global Hierarchical Linkages
    private Integer formId;
    private BusinessType businessType; 
    
    // Child Collections (Mapped as clean Domain objects)
    private List<NoaPpLat5Filing> filings;
    private List<NoaPpLat5Inventories> inventories; // Assumes your companion domain model exists
}
