package com.svlogic.opoppr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author David
 */
@Entity
@Table(name = "form")
@NamedQueries({
        @NamedQuery(name = "Form.findAll", query = "SELECT f FROM Form f"),
        @NamedQuery(name = "Form.findByFormId", query = "SELECT f FROM Form f WHERE f.formId = :formId"),
        @NamedQuery(name = "Form.findByTitle", query = "SELECT f FROM Form f WHERE f.title = :title"),
        @NamedQuery(name = "Form.findByFilingYear", query = "SELECT f FROM Form f WHERE f.filingYear = :filingYear"),
        @NamedQuery(name = "Form.findByBillNumber", query = "SELECT f FROM Form f WHERE f.billNumber = :billNumber"),
        @NamedQuery(name = "Form.findByBillNumberAndPin", query = "SELECT f FROM Form f WHERE f.billNumber = :billNumber AND f.pin = :pin"),
        @NamedQuery(name = "Form.findByBillNumberAndFilingYear", query = "SELECT f FROM Form f WHERE f.billNumber = :billNumber AND f.filingYear = :filingYear"),
        @NamedQuery(name = "Form.findByFilingYear", query = "SELECT f FROM Form f WHERE f.filingYear = :filingYear"),
        @NamedQuery(name = "Form.findByFilingYearAndBillNumberAndPin", query = "SELECT f FROM Form f WHERE f.filingYear = :filingYear AND f.billNumber = :billNumber AND f.pin = :pin"),
        @NamedQuery(name = "Form.findByBillNumberAndPinAndStatus", query = "SELECT f FROM Form f WHERE f.billNumber = :billNumber AND f.pin = :pin AND f.status.name = :statusName")
})
public class Form implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FORM_ID")
    private Integer formId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FILING_YEAR")
    private int filingYear;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFIED_DATE")
    private Date lastModifiedDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "BILL_NUMBER")
    private String billNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "PIN")
    private String pin;
    @JoinColumn(name = "FORM_TYPE_ID", referencedColumnName = "FORM_TYPE_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private FormType formType;
    @JoinColumn(name = "STATUS_ID", referencedColumnName = "STATUS_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Status status;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private User userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "form", fetch = FetchType.LAZY)
    private List<NoaPpLat5> noaPpLat5Collection;

    public Form() {
    }

    public Form(Integer formId) {
        this.formId = formId;
    }

    public Form(Integer formId, String title, int filingYear) {
        this.formId = formId;
        this.title = title;
        this.filingYear = filingYear;
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFilingYear() {
        return filingYear;
    }

    public void setFilingYear(int filingYear) {
        this.filingYear = filingYear;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public FormType getFormType() {
        return formType;
    }

    public void setFormType(FormType formType) {
        this.formType = formType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public List<NoaPpLat5> getNoaPpLat5Collection() {
        return noaPpLat5Collection;
    }

    public void setNoaPpLat5Collection(List<NoaPpLat5> noaPpLat5Collection) {
        this.noaPpLat5Collection = noaPpLat5Collection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (formId != null ? formId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Form)) {
            return false;
        }
        Form other = (Form) object;
        if ((this.formId == null && other.formId != null)
                || (this.formId != null && !this.formId.equals(other.formId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.svlogic.opoppr.model.Form[ formId=" + formId + " ]";
    }

}
