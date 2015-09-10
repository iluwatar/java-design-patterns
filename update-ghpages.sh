#!/bin/bash

# Clone gh-pages
git clone -b gh-pages "https://${GH_REF}" ghpagesclone
cd ghpagesclone

# Init and update submodule to latest
git submodule update --init --recursive
git submodule update --remote

# Setup Git
git config user.name "Travis-CI"
git config user.email "travis@no.reply"

# If there is a new version of the master branch
if git status | grep patterns > /dev/null 2>&1
then
  # it should be committed
  git add .
  git commit -m ":sparkles: :up: Automagic Update via Travis-CI"
  git push --quiet "https://${GH_TOKEN}:x-oauth-basic@${GH_REF}" gh-pages > /dev/null 2>&1
fi
