package com.opao.pp_api.services.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Form {
    private Integer id;
    private String title;
    private int filingYear;
    private LocalDateTime lastModifiedDate; 
    private String billNumber;
    private String pin;    
    private Integer formTypeId;
    private String statusName;
    private Integer userId;    
    private boolean hasLineItems; 
}
