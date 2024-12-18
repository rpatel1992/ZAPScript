# Base image: Use the official OWASP ZAP image
FROM owasp/zap2docker-stable:latest

# Set environment variables for ZAP (optional)
ENV ZAP_PORT=8080
ENV ZAP_HOST=0.0.0.0

# Install additional tools (if required)
RUN apt-get update && apt-get install -y \
    curl \
    jq \
    && apt-get clean

# Copy custom scripts or context files into the container (optional)
COPY ./zap-scripts /zap-scripts

# Expose the ZAP port
EXPOSE 8080

# Set the default command to start ZAP
CMD ["zap.sh", "-daemon", "-host", "0.0.0.0", "-port", "8080"]
