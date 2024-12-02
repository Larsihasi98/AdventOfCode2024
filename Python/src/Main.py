import os.path
import re
import Util
#varialbles
debug = False #set to true for debug messages

#File Directories
root = os.path.join(os.path.abspath(os.getcwd()),"Python")
resc = os.path.join(root,"Ressources")

print("Advent of Code day 1:")
inputFile="input1.txt"

content = Util.importText(os.path.join(resc,inputFile))
content = Util.splitLines(content)
columns = Util.transpose(content)
for line in columns:
    print(line)

print(len(columns))

# for line in content:
#     print(line)
