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

