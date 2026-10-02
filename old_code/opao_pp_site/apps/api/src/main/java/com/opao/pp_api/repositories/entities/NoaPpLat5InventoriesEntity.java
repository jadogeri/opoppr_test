package com.opao.pp_api.repositories.entities;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

import com.opao.pp_api.common.constants.models.NoaPpLat5InventoriesConstants;

@Entity
@Data // Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a public no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
@Table(name = "noa_pp_lat5_inventories")
public class NoaPpLat5InventoriesEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "NOA_PP_LAT_5_INVENTORIES_ID")
    private Integer noaPpLat5InventoriesId;
    @Basic(optional = false)
    @NotNull
    @Size(min = NoaPpLat5InventoriesConstants.JUR_MIN_LENGTH, max = NoaPpLat5InventoriesConstants.JUR_MAX_LENGTH)
    @Column(name = "JUR")
    private String jur;
    @Basic(optional = false)
    @NotNull
    @Size(min = NoaPpLat5InventoriesConstants.PARID_MIN_LENGTH, max = NoaPpLat5InventoriesConstants.PARID_MAX_LENGTH)
    @Column(name = "PARID")
    private String parid;
    @Basic(optional = false)
    @NotNull
    @Size(min = NoaPpLat5InventoriesConstants.TAXYR_MIN_LENGTH, max = NoaPpLat5InventoriesConstants.TAXYR_MAX_LENGTH)
    @Column(name = "TAXYR")
    private int taxyr;
    @Basic(optional = false)
    @NotNull
    @Size(min = NoaPpLat5InventoriesConstants.FILEYR_MIN_LENGTH, max = NoaPpLat5InventoriesConstants.FILEYR_MAX_LENGTH)
    @Column(name = "FILEYR")
    private int fileyr;
    @Size(max = NoaPpLat5InventoriesConstants.INVENTORY_TYPE_MAX_LENGTH)
    @Column(name = "INVENTORY_TYPE")
    private String inventoryType;
    @Column(name = "INVENTORY_MONTH")
    private Integer inventoryMonth;
    @Column(name = "INVENTORY_AMT")
    private Long inventoryAmt;
    @JoinColumn(name = "NOA_PP_LAT_5_ID", referencedColumnName = "NOA_PP_LAT_5_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NoaPpLat5Entity noaPpLat5;

    public NoaPpLat5InventoriesEntity(Integer noaPpLat5InventoriesId)
    {
        this.noaPpLat5InventoriesId = noaPpLat5InventoriesId;
    }

    public NoaPpLat5InventoriesEntity(Integer noaPpLat5InventoriesId, String jur, String parid, int taxyr, int fileyr)
    {
        this.noaPpLat5InventoriesId = noaPpLat5InventoriesId;
        this.jur = jur;
        this.parid = parid;
        this.taxyr = taxyr;
        this.fileyr = fileyr;
    }
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (noaPpLat5InventoriesId != null ? noaPpLat5InventoriesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NoaPpLat5InventoriesEntity)) {
            return false;
        }
        NoaPpLat5InventoriesEntity other = (NoaPpLat5InventoriesEntity) object;
        if ((this.noaPpLat5InventoriesId == null && other.noaPpLat5InventoriesId != null) || (this.noaPpLat5InventoriesId != null && !this.noaPpLat5InventoriesId.equals(other.noaPpLat5InventoriesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.svlogic.opoppr.model.NoaPpLat5InventoriesEntity[ noaPpLat5InventoriesId=" + noaPpLat5InventoriesId + " ]";
    }
    
}
