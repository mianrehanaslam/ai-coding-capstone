#!/usr/bin/env bash

set -euo pipefail

PROJECT_DIR="${1:?Project directory is required}"
REPORT_FILE="${2:?Report file is required}"
PROMPT_FILE="$PROJECT_DIR/config/ai-code-review-prompt.md"
BASE_BRANCH="${AI_REVIEW_BASE:-main}"

if ! command -v codex >/dev/null 2>&1; then
    echo "AI review requires the Codex CLI. Install or expose 'codex' on PATH." >&2
    exit 1
fi

if [[ ! -f "$PROMPT_FILE" ]]; then
    echo "AI review prompt was not found: $PROMPT_FILE" >&2
    exit 1
fi

if ! git -C "$PROJECT_DIR" rev-parse --verify "$BASE_BRANCH" >/dev/null 2>&1; then
    echo "AI review base branch does not exist: $BASE_BRANCH" >&2
    exit 1
fi

mkdir -p "$(dirname "$REPORT_FILE")"
REVIEW_PROMPT="$(cat "$PROMPT_FILE")

Configured base branch: $BASE_BRANCH
Use Git to compare the current HEAD with this base branch before reporting findings."

echo "Running AI code review against base branch '$BASE_BRANCH'..."
codex exec \
    --cd "$PROJECT_DIR" \
    --sandbox read-only \
    --ephemeral \
    --output-last-message "$REPORT_FILE" \
    "$REVIEW_PROMPT" </dev/null

if [[ ! -s "$REPORT_FILE" ]]; then
    echo "Codex completed without producing a review report." >&2
    exit 1
fi

"$PROJECT_DIR/scripts/validate-ai-review-report.sh" "$REPORT_FILE"

echo "AI code review report generated: $REPORT_FILE"
