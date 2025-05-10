#!/bin/bash

# Generate HMAC-SHA Key Script
# Usage: ./generate-hmac-key.sh [algorithm] [key-length]
# Example: ./generate-hmac-key.sh sha256 64

# Default values
ALGORITHM=${1:-sha256}  # sha256, sha384, or sha512
KEY_LENGTH=${2:-64}      # Key length in bytes (32, 48, 64 recommended)

# Validate algorithm
valid_algorithms=("sha256" "sha384" "sha512")
if [[ ! " ${valid_algorithms[@]} " =~ " ${ALGORITHM} " ]]; then
    echo "Error: Invalid algorithm. Choose from: ${valid_algorithms[@]}"
    exit 1
fi

# Validate key length
if ! [[ "$KEY_LENGTH" =~ ^[0-9]+$ ]] || [ "$KEY_LENGTH" -lt 16 ]; then
    echo "Error: Key length must be a number and at least 16 bytes"
    exit 1
fi

# Recommended minimum lengths for algorithms
case $ALGORITHM in
    sha256) min_length=32 ;;
    sha384) min_length=48 ;;
    sha512) min_length=64 ;;
esac

if [ "$KEY_LENGTH" -lt "$min_length" ]; then
    echo "Warning: For $ALGORITHM, recommended minimum key length is $min_length bytes"
    read -p "Continue with $KEY_LENGTH bytes? (y/N) " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
fi

# Generate random bytes
echo "Generating $ALGORITHM HMAC key with $KEY_LENGTH bytes..."
KEY=$(openssl rand -base64 $KEY_LENGTH | head -c $KEY_LENGTH | base64)

# Output the result
echo ""
echo "HMAC-$ALGORITHM Key (Base64 encoded):"
echo "-------------------------------------"
echo "$KEY"
echo "-------------------------------------"
echo ""
echo "For JWT applications, you might use this in your properties:"
echo "jwt.secret-key=$KEY"