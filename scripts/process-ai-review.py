#!/usr/bin/env python3

import json
import sys
from pathlib import Path


def escape(value):
    return str(value).replace("|", "\\|").replace("\n", " ")


def main():
    if len(sys.argv) != 3:
        raise SystemExit("Usage: process-ai-review.py <structured-json> <markdown-report>")

    source = Path(sys.argv[1])
    destination = Path(sys.argv[2])
    data = json.loads(source.read_text(encoding="utf-8"))
    findings = data["findings"]
    blockers = [
        finding for finding in findings
        if finding["status"] == "OPEN" and finding["severity"] in {"Critical", "High"}
    ]
    gate = "FAIL" if blockers else "PASS"

    lines = [
        "# Automated AI Code Review Report", "", "## Review scope and base branch", "", data["scope"], "",
        "## Severity-ranked findings", "",
        "| Severity | Status | Finding | Location | Recommended remediation |",
        "|---|---|---|---|---|",
    ]
    if findings:
        for finding in findings:
            values = {key: escape(value) for key, value in finding.items()}
            lines.append("| {severity} | {status} | {title} | {location} | {remediation} |".format(**values))
    else:
        lines.append("| — | — | No actionable findings identified. | — | — |")

    lines.extend(["", "## Gate decision", "", "**{}**".format(gate), "", "## Limitations", "",
                  data["limitations"], "", "AI_REVIEW_GATE: {}".format(gate), ""])
    destination.write_text("\n".join(lines), encoding="utf-8")
    if blockers:
        raise SystemExit("AI review gate failed because open Critical or High findings exist.")


if __name__ == "__main__":
    main()
