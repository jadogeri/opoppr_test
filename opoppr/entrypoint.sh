#! /bin/bash

#host=$(hostname)
#line=$(cat /etc/hosts | grep [1]27.0.0.1)
#echo $line localhost.localdomain $host >>/etc/hosts
#
#service sendmail start

export JAVA_OPTS="-DdbHost=\"$DB_HOST\" -DdbPort=\"$DB_PORT\" -DdbUsername=\"$DB_USERNAME\" -DdbPassword=\"$DB_PASSWORD\" -DsendConfirmationEmail=\"$SEND_CONFIRMATION_EMAIL\" -DsendFromAddress=\"$SEND_FROM_ADDRESS\" -DsendFromPerson=\"$SEND_FROM_PERSON\" -DsmtpHost=\"$SMTP_HOST\" -DsmtpPort=\"$SMTP_PORT\" -DsmtpStartTLSEnable=\"$SMTP_START_TLS_ENABLE\" -DsmtpAuth=\"$SMTP_AUTH\" -DsmtpUsername=\"$SMTP_USERNAME\" -DsmtpPassword=\"$SMTP_PASSWORD\" -DsiteHostname=\"$SITE_HOSTNAME\" -DsiteRoot=\"$SITE_ROOT\""

catalina.sh run