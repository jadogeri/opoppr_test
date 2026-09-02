/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.model;

import java.io.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 *
 * @author David
 */
@Entity
@Table(name = "noa_pp_lat_5_inventories")
@NamedQueries({
    @NamedQuery(name = "NoaPpLat5Inventories.findAll", query = "SELECT n FROM NoaPpLat5Inventories n"),
    @NamedQuery(name = "NoaPpLat5Inventories.findByNoaPpLat5InventoriesId", query = "SELECT n FROM NoaPpLat5Inventories n WHERE n.noaPpLat5InventoriesId = :noaPpLat5InventoriesId"),
    @NamedQuery(name = "NoaPpLat5Inventories.findByJur", query = "SELECT n FROM NoaPpLat5Inventories n WHERE n.jur = :jur"),
    @NamedQuery(name = "NoaPpLat5Inventories.findByParid", query = "SELECT n FROM NoaPpLat5Inventories n WHERE n.parid = :parid"),
    @NamedQuery(name = "NoaPpLat5Inventories.findByTaxyr", query = "SELECT n FROM NoaPpLat5Inventories n WHERE n.taxyr = :taxyr"),
    @NamedQuery(name = "NoaPpLat5Inventories.findByFileyr", query = "SELECT n FROM NoaPpLat5Inventories n WHERE n.fileyr = :fileyr"),
    @NamedQuery(name = "NoaPpLat5Inventories.findByInventoryType", query = "SELECT n FROM NoaPpLat5Inventories n WHERE n.inventoryType = :inventoryType"),
    @NamedQuery(name = "NoaPpLat5Inventories.findByInventoryMonth", query = "SELECT n FROM NoaPpLat5Inventories n WHERE n.inventoryMonth = :inventoryMonth"),
    @NamedQuery(name = "NoaPpLat5Inventories.findByInventoryAmt", query = "SELECT n FROM NoaPpLat5Inventories n WHERE n.inventoryAmt = :inventoryAmt")})
public class NoaPpLat5Inventories implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "NOA_PP_LAT_5_INVENTORIES_ID")
    private Integer noaPpLat5InventoriesId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "JUR")
    private String jur;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "PARID")
    private String parid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TAXYR")
    private int taxyr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FILEYR")
    private int fileyr;
    @Size(max = 2)
    @Column(name = "INVENTORY_TYPE")
    private String inventoryType;
    @Column(name = "INVENTORY_MONTH")
    private Integer inventoryMonth;
    @Column(name = "INVENTORY_AMT")
    private Long inventoryAmt;
    @JoinColumn(name = "NOA_PP_LAT_5_ID", referencedColumnName = "NOA_PP_LAT_5_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NoaPpLat5 noaPpLat5;

    public NoaPpLat5Inventories()
    {
    }

    public NoaPpLat5Inventories(Integer noaPpLat5InventoriesId)
    {
        this.noaPpLat5InventoriesId = noaPpLat5InventoriesId;
    }

    public NoaPpLat5Inventories(Integer noaPpLat5InventoriesId, String jur, String parid, int taxyr, int fileyr)
    {
        this.noaPpLat5InventoriesId = noaPpLat5InventoriesId;
        this.jur = jur;
        this.parid = parid;
        this.taxyr = taxyr;
        this.fileyr = fileyr;
    }

    public Integer getNoaPpLat5InventoriesId()
    {
        return noaPpLat5InventoriesId;
    }

    public void setNoaPpLat5InventoriesId(Integer noaPpLat5InventoriesId)
    {
        this.noaPpLat5InventoriesId = noaPpLat5InventoriesId;
    }

    public String getJur()
    {
        return jur;
    }

    public void setJur(String jur)
    {
        this.jur = jur;
    }

    public String getParid()
    {
        return parid;
    }

    public void setParid(String parid)
    {
        this.parid = parid;
    }

    public int getTaxyr()
    {
        return taxyr;
    }

    public void setTaxyr(int taxyr)
    {
        this.taxyr = taxyr;
    }

    public int getFileyr()
    {
        return fileyr;
    }

    public void setFileyr(int fileyr)
    {
        this.fileyr = fileyr;
    }

    public String getInventoryType()
    {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType)
    {
        this.inventoryType = inventoryType;
    }

    public Integer getInventoryMonth()
    {
        return inventoryMonth;
    }

    public void setInventoryMonth(Integer inventoryMonth)
    {
        this.inventoryMonth = inventoryMonth;
    }

    public Long getInventoryAmt()
    {
        return inventoryAmt;
    }

    public void setInventoryAmt(Long inventoryAmt)
    {
        this.inventoryAmt = inventoryAmt;
    }

    public NoaPpLat5 getNoaPpLat5()
    {
        return noaPpLat5;
    }

    public void setNoaPpLat5(NoaPpLat5 noaPpLat5)
    {
        this.noaPpLat5 = noaPpLat5;
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
        if (!(object instanceof NoaPpLat5Inventories)) {
            return false;
        }
        NoaPpLat5Inventories other = (NoaPpLat5Inventories) object;
        if ((this.noaPpLat5InventoriesId == null && other.noaPpLat5InventoriesId != null) || (this.noaPpLat5InventoriesId != null && !this.noaPpLat5InventoriesId.equals(other.noaPpLat5InventoriesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.svlogic.opoppr.model.NoaPpLat5Inventories[ noaPpLat5InventoriesId=" + noaPpLat5InventoriesId + " ]";
    }
    
}
