/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.DefaultStreamedContent;

import com.svlogic.opoppr.app.AppListener;
import com.svlogic.opoppr.model.Form;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author david
 */
@Named(value = "lat5StatusReport")
@SessionScoped
public class LAT5StatusReport implements Serializable
{
    private String year;
    private StreamedContent file;
    private EntityManager entityManager;
    
    /**
     * Creates a new instance of LAT5StatusReport
     */
    public LAT5StatusReport() {
        this.entityManager = AppListener.getEntityManagerFactory().createEntityManager();
    }

    public String generateReport()
    {
        String ret;
        Connection connection = null;
        try {
            String tempDir = System.getProperty("java.io.tmpdir");
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String destPath = tempDir + "/LAT5_STATUS_REPORT_" + formatter.format(currentTime) + ".pdf";
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("reports/Bill_Number_Status.jasper");
            connection = getDBConnection();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("YEAR", Integer.parseInt(year));
            JasperPrint p = JasperFillManager.fillReport(in, params, connection);
            JasperExportManager.exportReportToPdfFile(p, destPath);
            String filename = new File(destPath).getName();
            setFile(DefaultStreamedContent.builder()
                    .contentType("application/pdf")
                    .name(filename)
                    .stream(() -> {
                        try {
                            return new FileInputStream(destPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .build());
            ret = "success";
        }
        catch (Exception e) {
            e.printStackTrace();
            ret = "failure";
        }
        finally {
            closeDBConnection(connection);
        }

        return ret;
    }

    public StreamedContent getFile()
    {
        return file;
    }

    public void setFile(StreamedContent file)
    {
        this.file = file;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public Collection<String> getFilingYears() {
        EntityManager em = getEntityManager();
        CriteriaQuery<String> cq = em.getCriteriaBuilder().createQuery(String.class);
        Root<Form> rt = cq.from(Form.class);
        cq.select(rt.get("filingYear")).distinct(true);
        Query q = em.createQuery(cq);
        return q.getResultList();
    }

    private Connection getDBConnection() throws NamingException, SQLException
    {
        InitialContext context = new InitialContext();
        DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/OPOPPR");
        return ds.getConnection();
    }
    
    private void closeDBConnection(Connection connection)
    {  
        try {
            if (connection != null) {
                connection.close();
            }
        }
        catch (SQLException se) {
        }
    }


}
