#!/bin/bash

# Function to extract the title from YAML frontmatter
extract_title() {
    local file="$1"
    grep '^title: ' "$file" | sed 's/^title: //'
}

# Function to replace headings in a file
replace_headings() {
    local file="$1"
    local title="$2"

    awk -v title="$title" '
    BEGIN {
        intent = "## Intent of " title " Design Pattern"
        explanation = "## Detailed Explanation of " title " Pattern with Real-World Examples"
        applicability = "## When to Use the " title " Pattern in Java"
        tutorials = "## " title " Pattern Java Tutorials"
        known_uses = "## Real-World Applications of " title " Pattern in Java"
        consequences = "## Benefits and Trade-offs of " title " Pattern"
        related_patterns = "## Related Java Design Patterns"
        credits = "## References and Credits"
    }
    {
        if ($0 ~ /^## Intent$/) { print intent }
        else if ($0 ~ /^## Explanation$/) { print explanation }
        else if ($0 ~ /^## Class diagram$/) { print explanation }
        else if ($0 ~ /^## Applicability$/) { print applicability }
        else if ($0 ~ /^## Tutorials$/) { print tutorials }
        else if ($0 ~ /^## Known uses$/) { print known_uses }
        else if ($0 ~ /^## Consequences$/) { print consequences }
        else if ($0 ~ /^## Related patterns$/) { print related_patterns }
        else if ($0 ~ /^## Credits$/) { print credits }
        else { print }
    }' "$file" > tmpfile && mv tmpfile "$file"
}

# Iterate through 1st level subdirectories
for dir in */ ; do
    readme_file="${dir}README.md"

    if [ -f "$readme_file" ]; then
        title=$(extract_title "$readme_file")

        if [ -n "$title" ]; then
            echo "Editing $readme_file with title: $title"
            replace_headings "$readme_file" "$title"
        else
            echo "No title found in $readme_file"
        fi
    else
        echo "No README.md found in $dir"
    fi
done
