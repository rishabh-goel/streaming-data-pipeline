#!/bin/bash

# cd to folder where report is generated
zip -r /Users/ameykasbe/Desktop/kafka-spark/documents /Users/ameykasbe/Desktop/kafka-spark/documents/report-*
#( echo Subject: Report ; echo ; echo "PFA your report"; uuencode /home/ec2-user/report.zip report.zip ) | sendmail -vf rgoel20@uic.edu rgoel20@uic.edu
#mkdir -p ~/ReportBackup && mv ~/report.zip ~/ReportBackup/

( echo Subject: Report ; echo ; echo "PFA your report"; uuencode /Users/ameykasbe/Desktop/kafka-spark/documents/report.zip report.zip ) | sendmail -vf akasbe2@uic.edu akasbe2@uic.edu
mkdir -p ~/ReportBackup && mv ~/report.zip ~/ReportBackup/