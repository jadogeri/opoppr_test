package com.opao.pp_api.services.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessType {
    private Integer id;
    private Integer code;
    private String description;      
}
