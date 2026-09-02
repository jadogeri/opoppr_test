# Database
This folder contains files we've built up over the years to setup the OPOPPR databse.  The most important file is opoppr.mwb. It is a MySQL Workbench project that holds the OPOPPR database model. Changes to the database schema and tables are performed in MySQL Workbench and then scripts are generated from the model to rebuild the entire database schema. MySQL Workbench also supports sychronizing an existing database to the model so that we can apply updates without having to rebuild the entire database.

Download MySQL Workbench from https://dev.mysql.com/downloads/workbench/.

After making changes to the database model, use File | Export | Forward Engineer SQL CREATE script to export the script that rebuilds the entire database. Save the file to opoppr/database/1_opoppr.sql so the container is built with your changes.  The script include INSERT statement to preload all lookup tables and creates the record in the ACCOUNT table for the OPAADMIN account.
