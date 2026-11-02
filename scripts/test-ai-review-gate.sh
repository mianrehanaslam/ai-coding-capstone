#!/usr/bin/env bash

set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
VALIDATOR="$PROJECT_DIR/scripts/validate-ai-review-report.sh"
TEST_DIR="$(mktemp -d)"
trap 'rm -rf "$TEST_DIR"' EXIT

printf '# Automated AI Code Review Report\n## Review scope\nmain...HEAD\n## Severity-ranked findings\nNo actionable findings.\n## Gate decision\nPASS\n## Limitations\nHuman review required.\nAI_REVIEW_GATE: PASS\n' > "$TEST_DIR/pass.md"
printf '# Automated AI Code Review Report\n## Review scope\nmain...HEAD\n## Severity-ranked findings\n| High | Open issue |\n## Gate decision\nFAIL\n## Limitations\nHuman review required.\nAI_REVIEW_GATE: FAIL\n' > "$TEST_DIR/fail.md"
printf '# Review without a decision\n' > "$TEST_DIR/malformed.md"
printf '# Review\nAI_REVIEW_GATE: PASS\nAI_REVIEW_GATE: PASS\n' > "$TEST_DIR/duplicate.md"
printf '# Review\nAI_REVIEW_GATE: PASS\nTrailing content\n' > "$TEST_DIR/trailing.md"
printf '# Review\nAI_REVIEW_GATE: PASS\nAI_REVIEW_GATE: FAIL\n' > "$TEST_DIR/conflicting.md"
printf '# Automated AI Code Review Report\n## Review scope\nmain...HEAD\n## Severity-ranked findings\n| High | Contradictory finding |\n## Gate decision\nPASS\n## Limitations\nHuman review required.\nAI_REVIEW_GATE: PASS\n' > "$TEST_DIR/high-with-pass.md"
printf '# Automated AI Code Review Report\n## Review scope\nmain...HEAD\n## Severity-ranked findings\n### Critical - Contradictory finding\n## Gate decision\nPASS\n## Limitations\nHuman review required.\nAI_REVIEW_GATE: PASS\n' > "$TEST_DIR/critical-with-pass.md"

"$VALIDATOR" "$TEST_DIR/pass.md"

if "$VALIDATOR" "$TEST_DIR/fail.md" >/dev/null 2>&1; then
    echo "Expected FAIL report to be rejected." >&2
    exit 1
fi

if "$VALIDATOR" "$TEST_DIR/malformed.md" >/dev/null 2>&1; then
    echo "Expected malformed report to be rejected." >&2
    exit 1
fi

if "$VALIDATOR" "$TEST_DIR/missing.md" >/dev/null 2>&1; then
    echo "Expected missing report to be rejected." >&2
    exit 1
fi

if "$VALIDATOR" "$TEST_DIR/duplicate.md" >/dev/null 2>&1; then
    echo "Expected duplicate gate markers to be rejected." >&2
    exit 1
fi

if "$VALIDATOR" "$TEST_DIR/trailing.md" >/dev/null 2>&1; then
    echo "Expected content after the gate marker to be rejected." >&2
    exit 1
fi

if "$VALIDATOR" "$TEST_DIR/conflicting.md" >/dev/null 2>&1; then
    echo "Expected conflicting gate markers to be rejected." >&2
    exit 1
fi

if "$VALIDATOR" "$TEST_DIR/high-with-pass.md" >/dev/null 2>&1; then
    echo "Expected a High finding with PASS to be rejected." >&2
    exit 1
fi

if "$VALIDATOR" "$TEST_DIR/critical-with-pass.md" >/dev/null 2>&1; then
    echo "Expected a Critical finding with PASS to be rejected." >&2
    exit 1
fi

echo "AI review gate validator tests passed."
