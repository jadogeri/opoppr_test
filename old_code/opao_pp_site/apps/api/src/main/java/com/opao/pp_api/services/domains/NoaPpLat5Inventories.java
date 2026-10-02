package com.opao.pp_api.services.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoaPpLat5Inventories {
    private Integer id;
    private String jurisdiction;    // Cleaned up 'jur'
    private String parcelId;        // Cleaned up 'parid'
    private int taxYear;            // Cleaned up 'taxyr'
    private int filingYear;         // Cleaned up 'fileyr'
    private String inventoryType;
    private Integer inventoryMonth;
    private Long inventoryAmount;   // Cleaned up 'inventoryAmt'
    
    // Core Domain Relational Linkage
    private Integer noaPpLat5Id;    // Parent link decoupled as an ID primitive
}
