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
public class UserChange {
    private Integer id;
    private String verificationCode;
    private LocalDateTime initiatedTime;
    
    // Core Domain structural linkages (Flat IDs/Types instead of heavy JPA mapping trees)
    private Integer userChangeTypeId;
    private Integer userId;
}
