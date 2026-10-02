package com.opao.pp_api.services.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyAsset {
    private Integer id;
    private int sectionNumber;
    private String category;
    private String propertyType; 
    private String assetDescription;
    private int effectiveLife;
}
