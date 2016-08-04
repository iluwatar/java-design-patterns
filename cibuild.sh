#!/usr/bin/env bash
set -e # halt script on error

# need to init(download) our git submodule dependency first of all
git submodule update --init

# search and replace the baseurl and url parameters of _config.yml to work on s3
sed -i -e 's/baseurl: /# baseurl: /g' _config.yml
sed -i -e 's/url: "https:\/\/iluwatar.github.io"/url: "http:\/\/java-design-patterns.com"/g' _config.yml

# add the currently used commit hash to the _config.yml so it can be displayed on the website
printf "\ngithub:\n  build_revision: " >> _config.yml
git log --pretty=format:'%H' -n 1 >> _config.yml

bundle exec jekyll build

# - ignore everything below every webapp directory, so we dont mess with the source code
# - ignore the fork link of our project, because it somehow is not valid (https://validator.w3.org/)
# - ignore the folder principles of the external dependency (git submodule) webpro/programming-principles
bundle exec htmlproofer ./_site/ --file-ignore "/.+\/(webapp|principles)\/.*/" --url-ignore "https://github.com/iluwatar/java-design-patterns/fork"
