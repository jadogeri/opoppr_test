package com.opao.pp_api.services.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoaPpLat5Filing {
    private Integer id;
    private String jurisdiction;       
    private String parcelId;           
    private int taxYear;               
    private String category;
    private String propertyType;       
    private int filingYear;            
    private Integer yearAcquired;      
    private Integer noOfUnits;         
    private Long acquisitionCost;
    private Integer effectiveLife;
    
    // Consigner / Owner Details
    private String consignerOwnerName;
    private String consignerMailingAddr;
    private Long consignerRentalAmt;
    private String itemDescription;
    private String consignerTelNo;
    
    // Core Domain Relational Linkages
    private Integer noaPpLat5Id;        
    private PropertyAsset propertyAsset; 
}
