#!/usr/bin/env bash

set -euo pipefail

REPORT_FILE="${1:?AI review report file is required}"

if [[ ! -s "$REPORT_FILE" ]]; then
    echo "AI review report is missing or empty: $REPORT_FILE" >&2
    exit 1
fi

GATE_COUNT="$(awk '/^AI_REVIEW_GATE: (PASS|FAIL)$/ { count++ } END { print count + 0 }' "$REPORT_FILE")"
FINAL_NON_EMPTY_LINE="$(awk 'NF { line = $0 } END { print line }' "$REPORT_FILE")"

if [[ "$GATE_COUNT" -ne 1 ]]; then
    echo "AI review report must contain exactly one gate decision." >&2
    exit 1
fi

if [[ "$FINAL_NON_EMPTY_LINE" == "AI_REVIEW_GATE: FAIL" ]]; then
    echo "AI review gate failed because Critical or High findings are open." >&2
    exit 1
fi

if [[ "$FINAL_NON_EMPTY_LINE" != "AI_REVIEW_GATE: PASS" ]]; then
    echo "AI review gate decision must be the final non-empty report line." >&2
    exit 1
fi
