#!/usr/bin/env bash

set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"

read -r -p "API username [local-user]: " CAPSTONE_USERNAME_INPUT
CAPSTONE_USERNAME="${CAPSTONE_USERNAME_INPUT:-local-user}"

read -r -s -p "API password: " CAPSTONE_PASSWORD
printf '\n'

if [ -z "$CAPSTONE_PASSWORD" ]; then
    echo "Password cannot be empty."
    exit 1
fi

export CAPSTONE_USERNAME
export CAPSTONE_PASSWORD

if [ -x "/usr/libexec/java_home" ]; then
    JAVA_HOME="$(/usr/libexec/java_home -v 11)"
    export JAVA_HOME
fi

cd "$PROJECT_DIR"

if [ -x "$PROJECT_DIR/mvnw" ]; then
    exec "$PROJECT_DIR/mvnw" spring-boot:run
fi

if command -v mvn >/dev/null 2>&1; then
    exec mvn spring-boot:run
fi

echo "Neither Maven Wrapper nor Maven was found."
exit 1
