#!/bin/bash

echo $currentDate >> /root/assembly_spark/emailLogs
# cd to folder where report is generated
zip -r /root/assembly_spark/reports/report-*
#( echo Subject: Report ; echo ; echo "PFA your report"; uuencode /home/ec2-user/report.zip report.zip ) | sendmail -vf rgoel20@uic.edu rgoel20@uic.edu
#mkdir -p ~/ReportBackup && mv ~/report.zip ~/ReportBackup/

( echo Subject: Report ; echo ; echo "PFA your report"; uuencode /root/assembly_spark/reports/report.zip report.zip ) | sendmail -vf svn2998@gmail.com svn2998@gmail.com
mkdir -p /root/assembly_spark/reportBackup && mv /root/assembly_spark/reports/* /root/assembly_spark/reportBackup/