package com.svlogic.opoppr.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import jakarta.persistence.EntityManagerFactory;

import org.primefaces.model.file.UploadedFile;

import com.svlogic.opoppr.app.AppListener;

public abstract class InitializeCommon implements Serializable {
    private UploadedFile file;
    private String output;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String uploadFile() {
        try {
            String tempDir = System.getProperty("java.io.tmpdir");
            String destPath = tempDir + "/" + this.getFile().getFileName();
            copyFile(destPath, this.getFile().getInputStream());
            runCommand(destPath);

            EntityManagerFactory emFactory = AppListener.getEntityManagerFactory();
            emFactory.getCache().evictAll();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return "success";
    }

    private void copyFile(String destPath, InputStream in)
            throws IOException {
        File f = new File(destPath);
        try (FileOutputStream out = new FileOutputStream(f)) {
            byte[] buffer = new byte[16384];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    abstract protected void runCommand(String destPath) throws IOException;
}
