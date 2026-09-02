#!/bin/bash
# OPOPPR database backup script
while getopts u:p: option
do
        case "${option}"
        in
                u) USER=${OPTARG};;
                p) PASS=$OPTARG;;
        esac
done

# Parent backup directory
backup_parent_dir="/Applications/mampstack-5.4.23-0/mysql/backups"

# Database
backup_db="/Applications/mampstack-5.4.23-0/mysql/backups"

# Create backup directory and set permissions
backup_date=`date +%Y_%m_%d_%H_%M`
backup_dir="${backup_parent_dir}/${backup_date}"
echo "Backup directory: ${backup_dir}"
mkdir -p "${backup_dir}"
chmod 700 "${backup_dir}"
  
echo "Creating backup of OPOPPR database"
mysqldump --user=${USER} --password=${PASS} ${backup_db} | gzip > "${backup_dir}/${backup_db}.gz"
chmod 600 "${backup_dir}/${backup_db}.gz"
