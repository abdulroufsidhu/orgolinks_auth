#!/bin/bash

# Script to generate self-signed SSL certificates for development
# This script creates SSL certificates for the Orgolink Auth service

set -e

# Configuration
CERT_DIR="./nginx/ssl"
CERT_NAME="cert"
KEY_NAME="key"
DAYS=365
COUNTRY="US"
STATE="California"
CITY="San Francisco"
ORGANIZATION="Orgolink"
ORGANIZATIONAL_UNIT="Development"
COMMON_NAME="localhost"
EMAIL="admin@orgolink.local"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}🔒 Generating SSL certificates for Orgolink Auth Service${NC}"
echo "=================================================="

# Create SSL directory if it doesn't exist
if [ ! -d "$CERT_DIR" ]; then
    echo -e "${YELLOW}📁 Creating SSL directory: $CERT_DIR${NC}"
    mkdir -p "$CERT_DIR"
fi

# Check if certificates already exist
if [ -f "$CERT_DIR/$CERT_NAME.pem" ] && [ -f "$CERT_DIR/$KEY_NAME.pem" ]; then
    echo -e "${YELLOW}⚠️  SSL certificates already exist${NC}"
    read -p "Do you want to regenerate them? (y/N): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        echo -e "${GREEN}✅ Using existing certificates${NC}"
        exit 0
    fi
fi

# Generate private key
echo -e "${YELLOW}🔑 Generating private key...${NC}"
openssl genrsa -out "$CERT_DIR/$KEY_NAME.pem" 2048

# Generate certificate signing request
echo -e "${YELLOW}📝 Generating certificate signing request...${NC}"
openssl req -new -key "$CERT_DIR/$KEY_NAME.pem" -out "$CERT_DIR/$CERT_NAME.csr" \
    -subj "/C=$COUNTRY/ST=$STATE/L=$CITY/O=$ORGANIZATION/OU=$ORGANIZATIONAL_UNIT/CN=$COMMON_NAME/emailAddress=$EMAIL"

# Generate self-signed certificate
echo -e "${YELLOW}📜 Generating self-signed certificate...${NC}"
openssl req -x509 -key "$CERT_DIR/$KEY_NAME.pem" -in "$CERT_DIR/$CERT_NAME.csr" -out "$CERT_DIR/$CERT_NAME.pem" \
    -days $DAYS \
    -extensions v3_req \
    -config <(cat <<EOF
[req]
distinguished_name = req_distinguished_name
req_extensions = v3_req
prompt = no

[req_distinguished_name]
C = $COUNTRY
ST = $STATE
L = $CITY
O = $ORGANIZATION
OU = $ORGANIZATIONAL_UNIT
CN = $COMMON_NAME
emailAddress = $EMAIL

[v3_req]
keyUsage = keyEncipherment, dataEncipherment
extendedKeyUsage = serverAuth
subjectAltName = @alt_names

[alt_names]
DNS.1 = localhost
DNS.2 = *.localhost
DNS.3 = orgolink-auth
DNS.4 = *.orgolink-auth
IP.1 = 127.0.0.1
IP.2 = ::1
EOF
)

# Clean up CSR file
rm "$CERT_DIR/$CERT_NAME.csr"

# Set proper permissions
chmod 600 "$CERT_DIR/$KEY_NAME.pem"
chmod 644 "$CERT_DIR/$CERT_NAME.pem"

echo -e "${GREEN}✅ SSL certificates generated successfully!${NC}"
echo "=================================================="
echo -e "${GREEN}Certificate:${NC} $CERT_DIR/$CERT_NAME.pem"
echo -e "${GREEN}Private Key:${NC} $CERT_DIR/$KEY_NAME.pem"
echo -e "${GREEN}Valid for:${NC} $DAYS days"
echo -e "${GREEN}Common Name:${NC} $COMMON_NAME"
echo ""
echo -e "${YELLOW}📋 Certificate Information:${NC}"
openssl x509 -in "$CERT_DIR/$CERT_NAME.pem" -text -noout | grep -E "(Subject:|Issuer:|Not Before:|Not After:|DNS:|IP Address:)"
echo ""
echo -e "${GREEN}🚀 You can now start the services with: docker-compose up${NC}"
echo -e "${YELLOW}⚠️  Note: This is a self-signed certificate for development only!${NC}"
echo -e "${YELLOW}⚠️  Browsers will show a security warning. You can safely ignore it for development.${NC}"
