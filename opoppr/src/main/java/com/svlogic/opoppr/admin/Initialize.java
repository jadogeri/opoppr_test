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

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

/**
 *
 * @author david
 */
@Named("initialize")
@SessionScoped
public class Initialize extends InitializeCommon
{
    /**
     * Creates a new instance of Initialize
     */
    public Initialize()
    {
    }

    protected void runCommand(String destPath)
        throws IOException
    {
        String opopprScripts = System.getenv("OPOPPR_SCRIPTS");
        String host = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
        String port = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "3306";
        String[] cmdArray = new String[]{opopprScripts + "/importLoadOPOPPR.sh", "-h", host, "-P", port, "-u", System.getenv("DB_USERNAME"), "-p", System.getenv("DB_PASSWORD"), "-f", destPath};
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
}
