#!/usr/bin/env bash

set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
PROCESSOR="$PROJECT_DIR/scripts/process-ai-review.py"
TEST_DIR="$(mktemp -d)"
trap 'rm -rf "$TEST_DIR"' EXIT

printf '{"scope":"main...HEAD","findings":[],"limitations":"Human review required."}' > "$TEST_DIR/pass.json"
printf '{"scope":"main...HEAD","findings":[{"severity":"High","status":"OPEN","title":"Open issue","location":"file:1","remediation":"Fix it"}],"limitations":"Human review required."}' > "$TEST_DIR/fail.json"
printf '{invalid json' > "$TEST_DIR/malformed.json"

python3 "$PROCESSOR" "$TEST_DIR/pass.json" "$TEST_DIR/pass.md"
grep -q '^AI_REVIEW_GATE: PASS$' "$TEST_DIR/pass.md"

if python3 "$PROCESSOR" "$TEST_DIR/fail.json" "$TEST_DIR/fail.md" >/dev/null 2>&1; then
    echo "Expected open High finding to fail." >&2
    exit 1
fi
grep -q '^AI_REVIEW_GATE: FAIL$' "$TEST_DIR/fail.md"

if python3 "$PROCESSOR" "$TEST_DIR/malformed.json" "$TEST_DIR/malformed.md" >/dev/null 2>&1; then
    echo "Expected malformed JSON to fail." >&2
    exit 1
fi

echo "Structured AI review gate tests passed."
