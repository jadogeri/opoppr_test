package com.svlogic.opoppr.model;

import java.io.*;
import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 *
 * @author David
 */
@Entity
@Table(name = "noa_pp_lat5")
@NamedQueries({
        @NamedQuery(name = "NoaPpLat5.findAll", query = "SELECT n FROM NoaPpLat5 n"),
        @NamedQuery(name = "NoaPpLat5.findByNoaPpLat5Id", query = "SELECT n FROM NoaPpLat5 n WHERE n.noaPpLat5Id = :noaPpLat5Id"),
        @NamedQuery(name = "NoaPpLat5.findByJur", query = "SELECT n FROM NoaPpLat5 n WHERE n.jur = :jur"),
        @NamedQuery(name = "NoaPpLat5.findByParid", query = "SELECT n FROM NoaPpLat5 n WHERE n.parid = :parid"),
        @NamedQuery(name = "NoaPpLat5.findByAltid", query = "SELECT n FROM NoaPpLat5 n WHERE n.altid = :altid"),
        @NamedQuery(name = "NoaPpLat5.findByTaxyr", query = "SELECT n FROM NoaPpLat5 n WHERE n.taxyr = :taxyr"),
        @NamedQuery(name = "NoaPpLat5.findByOwnername", query = "SELECT n FROM NoaPpLat5 n WHERE n.ownername = :ownername"),
        @NamedQuery(name = "NoaPpLat5.findByBusinessTypeCode", query = "SELECT n FROM NoaPpLat5 n WHERE n.businessType.businessCode = :businessCode"),
        @NamedQuery(name = "NoaPpLat5.findByAddr1", query = "SELECT n FROM NoaPpLat5 n WHERE n.addr1 = :addr1"),
        @NamedQuery(name = "NoaPpLat5.findByAddr2", query = "SELECT n FROM NoaPpLat5 n WHERE n.addr2 = :addr2"),
        @NamedQuery(name = "NoaPpLat5.findByCityname", query = "SELECT n FROM NoaPpLat5 n WHERE n.cityname = :cityname"),
        @NamedQuery(name = "NoaPpLat5.findByStatecode", query = "SELECT n FROM NoaPpLat5 n WHERE n.statecode = :statecode"),
        @NamedQuery(name = "NoaPpLat5.findByZip1", query = "SELECT n FROM NoaPpLat5 n WHERE n.zip1 = :zip1"),
        @NamedQuery(name = "NoaPpLat5.findByPin", query = "SELECT n FROM NoaPpLat5 n WHERE n.pin = :pin"),
        @NamedQuery(name = "NoaPpLat5.findByContactName", query = "SELECT n FROM NoaPpLat5 n WHERE n.contactName = :contactName"),
        @NamedQuery(name = "NoaPpLat5.findByContactPhone", query = "SELECT n FROM NoaPpLat5 n WHERE n.contactPhone = :contactPhone"),
        @NamedQuery(name = "NoaPpLat5.findByContactFax", query = "SELECT n FROM NoaPpLat5 n WHERE n.contactFax = :contactFax"),
        @NamedQuery(name = "NoaPpLat5.findByContactEmail", query = "SELECT n FROM NoaPpLat5 n WHERE n.contactEmail = :contactEmail"),
        @NamedQuery(name = "NoaPpLat5.findByContactSendEmails", query = "SELECT n FROM NoaPpLat5 n WHERE n.contactSendEmails = :contactSendEmails"),
        @NamedQuery(name = "NoaPpLat5.findByPropertyAddress", query = "SELECT n FROM NoaPpLat5 n WHERE n.propertyAddress = :propertyAddress"),
        @NamedQuery(name = "NoaPpLat5.findByTaxpayerName", query = "SELECT n FROM NoaPpLat5 n WHERE n.taxpayerName = :taxpayerName"),
        @NamedQuery(name = "NoaPpLat5.findByTaxpayerPreparedDate", query = "SELECT n FROM NoaPpLat5 n WHERE n.taxpayerPreparedDate = :taxpayerPreparedDate"),
        @NamedQuery(name = "NoaPpLat5.findByTaxPreparerName", query = "SELECT n FROM NoaPpLat5 n WHERE n.taxPreparerName = :taxPreparerName"),
        @NamedQuery(name = "NoaPpLat5.findByTaxPreparerPreparedDate", query = "SELECT n FROM NoaPpLat5 n WHERE n.taxPreparerPreparedDate = :taxPreparerPreparedDate") })
public class NoaPpLat5 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "NOA_PP_LAT_5_ID")
    private Integer noaPpLat5Id;
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
    @Size(max = 30)
    @Column(name = "ALTID")
    private String altid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TAXYR")
    private int taxyr;
    @Size(max = 40)
    @Column(name = "OWNERNAME")
    private String ownername;
    @JoinColumn(name = "BUSINESS_TYPE_ID", referencedColumnName = "BUSINESS_TYPE_ID")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private BusinessType businessType;
    @Size(max = 80)
    @Column(name = "ADDR1")
    private String addr1;
    @Size(max = 80)
    @Column(name = "ADDR2")
    private String addr2;
    @Size(max = 40)
    @Column(name = "CITYNAME")
    private String cityname;
    @Size(max = 2)
    @Column(name = "STATECODE")
    private String statecode;
    @Size(max = 5)
    @Column(name = "ZIP1")
    private String zip1;
    @Size(max = 6)
    @Column(name = "PIN")
    private String pin;
    @Size(max = 40)
    @Column(name = "CONTACT_NAME")
    private String contactName;
    @Size(max = 10)
    @Column(name = "CONTACT_PHONE")
    private String contactPhone;
    @Size(max = 10)
    @Column(name = "CONTACT_FAX")
    private String contactFax;
    @Size(max = 75)
    @Column(name = "CONTACT_EMAIL")
    private String contactEmail;
    @Column(name = "CONTACT_SEND_EMAILS")
    private Boolean contactSendEmails;
    @Size(max = 50)
    @Column(name = "PROPERTY_ADDRESS")
    private String propertyAddress;
    @Size(max = 50)
    @Column(name = "TAXPAYER_NAME")
    private String taxpayerName;
    @Column(name = "TAXPAYER_PREPARED_DATE")
    @Temporal(TemporalType.DATE)
    private Date taxpayerPreparedDate;
    @Size(max = 50)
    @Column(name = "TAX_PREPARER_NAME")
    private String taxPreparerName;
    @Size(max = 10)
    @Column(name = "TAX_PREPARER_PHONE")
    private String taxPreparerPhone;
    @Size(max = 75)
    @Column(name = "TAX_PREPARER_EMAIL")
    private String taxPreparerEmail;
    @Column(name = "TAX_PREPARER_PREPARED_DATE")
    @Temporal(TemporalType.DATE)
    private Date taxPreparerPreparedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "noaPpLat5", fetch = FetchType.LAZY)
    private Collection<NoaPpLat5Inventories> noaPpLat5InventoriesCollection;
    @JoinColumn(name = "FORM_ID", referencedColumnName = "FORM_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Form form;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "noaPpLat5", fetch = FetchType.LAZY)
    private Collection<NoaPpLat5Filing> noaPpLat5FilingCollection;

    public NoaPpLat5() {
    }

    public NoaPpLat5(Integer noaPpLat5Id) {
        this.noaPpLat5Id = noaPpLat5Id;
    }

    public NoaPpLat5(Integer noaPpLat5Id, String jur, String parid, int taxyr) {
        this.noaPpLat5Id = noaPpLat5Id;
        this.jur = jur;
        this.parid = parid;
        this.taxyr = taxyr;
    }

    public Integer getNoaPpLat5Id() {
        return noaPpLat5Id;
    }

    public void setNoaPpLat5Id(Integer noaPpLat5Id) {
        this.noaPpLat5Id = noaPpLat5Id;
    }

    public String getJur() {
        return jur;
    }

    public void setJur(String jur) {
        this.jur = jur;
    }

    public String getParid() {
        return parid;
    }

    public void setParid(String parid) {
        this.parid = parid;
    }

    public String getAltid() {
        return altid;
    }

    public void setAltid(String altid) {
        this.altid = altid;
    }

    public int getTaxyr() {
        return taxyr;
    }

    public void setTaxyr(int taxyr) {
        this.taxyr = taxyr;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getZip1() {
        return zip1;
    }

    public void setZip1(String zip1) {
        this.zip1 = zip1;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactFax() {
        return contactFax;
    }

    public void setContactFax(String contactFax) {
        this.contactFax = contactFax;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Boolean getContactSendEmails() {

        return contactSendEmails;
    }

    public void setContactSendEmails(Boolean contactSendEmails) {
        this.contactSendEmails = contactSendEmails;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public String getTaxpayerName() {
        return taxpayerName;
    }

    public void setTaxpayerName(String taxpayerName) {
        this.taxpayerName = taxpayerName;
    }

    public Date getTaxpayerPreparedDate() {
        return taxpayerPreparedDate;
    }

    public void setTaxpayerPreparedDate(Date taxpayerPreparedDate) {
        this.taxpayerPreparedDate = taxpayerPreparedDate;
    }

    public String getTaxPreparerName() {
        return taxPreparerName;
    }

    public void setTaxPreparerName(String taxPreparerName) {
        this.taxPreparerName = taxPreparerName;
    }

    public String getTaxPreparerPhone() {
        return taxPreparerPhone;
    }

    public void setTaxPreparerPhone(String taxPreparerPhone) {
        this.taxPreparerPhone = taxPreparerPhone;
    }

    public String getTaxPreparerEmail() {
        return taxPreparerEmail;
    }

    public void setTaxPreparerEmail(String taxPreparerEmail) {
        this.taxPreparerEmail = taxPreparerEmail;
    }

    public Date getTaxPreparerPreparedDate() {
        return taxPreparerPreparedDate;
    }

    public void setTaxPreparerPreparedDate(Date taxPreparerPreparedDate) {
        this.taxPreparerPreparedDate = taxPreparerPreparedDate;
    }

    public Collection<NoaPpLat5Inventories> getNoaPpLat5InventoriesCollection() {
        return noaPpLat5InventoriesCollection;
    }

    public void setNoaPpLat5InventoriesCollection(Collection<NoaPpLat5Inventories> noaPpLat5InventoriesCollection) {
        this.noaPpLat5InventoriesCollection = noaPpLat5InventoriesCollection;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Collection<NoaPpLat5Filing> getNoaPpLat5FilingCollection() {
        return noaPpLat5FilingCollection;
    }

    public void setNoaPpLat5FilingCollection(Collection<NoaPpLat5Filing> noaPpLat5FilingCollection) {
        this.noaPpLat5FilingCollection = noaPpLat5FilingCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noaPpLat5Id != null ? noaPpLat5Id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NoaPpLat5)) {
            return false;
        }
        NoaPpLat5 other = (NoaPpLat5) object;
        if ((this.noaPpLat5Id == null && other.noaPpLat5Id != null)
                || (this.noaPpLat5Id != null && !this.noaPpLat5Id.equals(other.noaPpLat5Id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.svlogic.opoppr.model.NoaPpLat5[ noaPpLat5Id=" + noaPpLat5Id + " ]";
    }

}
