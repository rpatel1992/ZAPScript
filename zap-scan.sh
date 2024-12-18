#!/bin/bash

# Variables
ZAP_PATH="zap" # Path to the ZAP directory
TARGET_URL="http://localhost:8081" # The target application URL
REPORT_NAME="zap_report.html" # Output report file

# Start ZAP in daemon mode
echo "Starting OWASP ZAP..."
nohup java -jar $ZAP_PATH/zap.jar -daemon -port 8080 &

# Wait for ZAP to initialize
echo "Waiting for ZAP to start..."
sleep 30

# Run ZAP baseline scan
echo "Running ZAP baseline scan on $TARGET_URL..."
java -jar $ZAP_PATH/zap.jar -cmd -quickurl $TARGET_URL -quickout $REPORT_NAME -cmdoptions="-quickprogress"

# Check if vulnerabilities are found
if grep -q "FAIL-NEW" $REPORT_NAME; then
  echo "Security vulnerabilities found! Check $REPORT_NAME for details."
  exit 1
fi

echo "ZAP scan completed successfully. No vulnerabilities found."
