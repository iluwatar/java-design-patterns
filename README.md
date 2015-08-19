# Design pattern samples in Java.

This is the branch for the pages.

Link for Website: http://iluwatar.github.io/java-design-patterns/

## How to update the website reference

Due to the fact that we use submodules, the website doesn't automatically
update with the changes in the master branch. And for now this update process
is manual, here is how you do it:

1. checkout the latest version of the gh-pages branch
2. open a Git Bash at root level of the repo (ls should show you the index.html file)
3. execute the following command to update the submodule `git submodule update --remote`
4. Check the cmd output line "Submodule path 'pattern': checked out 'COMMIT_HASH'" and validate that the correct COMMIT_HASH is used
5. Check if something changed with `git diff`. It should show the folder 'patterns' changed.
6. Execute `git add .`
7. Commit and push the change
