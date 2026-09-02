/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.admin;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.svlogic.opoppr.app.AppListener;
import com.svlogic.opoppr.model.Form;

/**
 *
 * @author david
 */
@Named("export")
@SessionScoped
public class Export implements Serializable {
    private String output;
    private EntityManager entityManager;
    private String year;
    private StreamedContent file;

    /**
     * Creates a new instance of Initialize
     */
    public Export() {
        this.entityManager = AppListener.getEntityManagerFactory().createEntityManager();
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public StreamedContent getFile() {
        return file;
    }

    public String generateExport() {
        try {
            String tempDir = System.getProperty("java.io.tmpdir");
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String destPath = tempDir + "/" + this.year + "_LAT5_EXTRACT_" + formatter.format(currentTime);
            runCommand(destPath);
            String filename = new File(destPath + ".zip").getName();
            setFile(DefaultStreamedContent.builder()
                    .contentType("application/zip")
                    .name(filename)
                    .stream(() -> {
                        try {
                            return new FileInputStream(destPath + ".zip");
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .build());

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return "success";
    }

    private void runCommand(String destPath)
            throws IOException {
        String opopprScripts = System.getenv("OPOPPR_SCRIPTS");
        String host = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
        String port = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "3306";
        String[] cmdArray = new String[] { opopprScripts + "/exportLoadOPOPPR.sh", "-h", host, "-P", port, "-u",
                System.getenv("DB_USERNAME"), "-p", System.getenv("DB_PASSWORD"), "-f", destPath, "-y", this.year };
        ProcessBuilder pb = new ProcessBuilder(cmdArray);
        pb.redirectErrorStream(true);
        Process p = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        char[] buffer = new char[16384];
        CharArrayWriter writer = new CharArrayWriter();
        int charsRead;
        while ((charsRead = reader.read(buffer)) != -1) {
            writer.write(buffer, 0, charsRead);
        }
        setOutput(writer.toString());
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

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
