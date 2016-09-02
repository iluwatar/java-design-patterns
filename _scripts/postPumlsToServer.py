#
# The MIT License
# Copyright (c) 2014 Ilkka Seppälä
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

import requests, glob, re, os

# taken from here: http://stackoverflow.com/a/13641746
def replace(file, pattern, subst):
    # Read contents from file as a single string
    file_handle = open(file, 'r')
    file_string = file_handle.read()
    file_handle.close()

    # Use RE package to allow for replacement (also allowing for (multiline) REGEX)
    file_string = (re.sub(pattern, subst, file_string))

    # Write contents to file.
    # Using mode 'w' truncates the file.
    file_handle = open(file, 'w')
    file_handle.write(file_string)
    file_handle.close()

# list of all puml files
fileList = glob.glob('*/etc/*.puml')
for puml in fileList:
    pathSplit = puml.split("/")
    # parent folder
    parent = pathSplit[0]
    # individual artifact/project name
    artifact = pathSplit[2].replace(".urm.puml", "")
    print "parent: " + parent + "; artifact: " + artifact

    # do a POST to the official plantuml hosting site with a little trick "!includeurl" and raw github content
    data = {
        'text': "!includeurl https://raw.githubusercontent.com/iluwatar/java-design-patterns/master/" + puml
    }
    r = requests.post('http://plantuml.com/plantuml/uml', data=data)
    pumlId = r.url.replace("http://plantuml.com/plantuml/uml/", "")
    
    # the only thing needed to get a png/svg/ascii from the server back
    print "Puml Server ID: " + pumlId
    
    # add the id so jekyll/liquid can use it
    if (parent == artifact):
        replace("./" + parent + "/README.md", "categories:", "pumlid: {}\\ncategories:".format(pumlId))
    else:
        print "I dont want to program this, just add the following lines to the README.md file that corresponds to this puml file '" + puml + "'\npumlid: {}".format(pumlId)

