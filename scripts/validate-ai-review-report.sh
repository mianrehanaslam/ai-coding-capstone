#!/usr/bin/env bash

set -euo pipefail

REPORT_FILE="${1:?AI review report file is required}"

if [[ ! -s "$REPORT_FILE" ]]; then
    echo "AI review report is missing or empty: $REPORT_FILE" >&2
    exit 1
fi

if grep -q '^AI_REVIEW_GATE: FAIL$' "$REPORT_FILE"; then
    echo "AI review gate failed because Critical or High findings are open." >&2
    exit 1
fi

if ! grep -q '^AI_REVIEW_GATE: PASS$' "$REPORT_FILE"; then
    echo "AI review report has no valid machine-readable gate decision." >&2
    exit 1
fi
