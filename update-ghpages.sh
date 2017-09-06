#!/bin/bash
#
# The MIT License
# Copyright (c) 2014-2016 Ilkka Seppälä
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in
# all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
# THE SOFTWARE.
#


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
