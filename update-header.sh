#!/bin/bash

# Find all README.md files in subdirectories one level deep
# and replace "### " with "## " at the beginning of lines
find . -maxdepth 2 -type f -name "README.md" -exec sed -i '' 's/^### /## /' {} \;

echo "Headers updated in README.md files."
