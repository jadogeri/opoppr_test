/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.admin;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Collection;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import com.svlogic.opoppr.app.AppListener;
import com.svlogic.opoppr.model.Form;

/**
 *
 * @author david
 */
@Named("close")
@SessionScoped
public class Close implements Serializable
{
    private String output;
    private String year;
    private EntityManager entityManager;
    
    /**
     * Creates a new instance of Initialize
     */
    public Close()
    {
        this.entityManager = AppListener.getEntityManagerFactory().createEntityManager();
    }

    public String getOutput()
    {
        return output;
    }

    public void setOutput(String output)
    {
        this.output = output;
    }

    public String closeDatabase()
    {
        try {
            runCommand();
            
            EntityManagerFactory emFactory = AppListener.getEntityManagerFactory();
            emFactory.getCache().evictAll();
       }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        return "success";
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
    
    private void runCommand()
        throws IOException
    {
        String opopprScripts = System.getenv("OPOPPR_SCRIPTS");
        String host = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
        String port = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "3306";
        String[] cmdArray = new String[]{opopprScripts + "/closeOPOPPR.sh", "-h", host, "-P", port, "-u", System.getenv("DB_USERNAME"), "-p", System.getenv("DB_PASSWORD"), "-y", this.year};
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

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
