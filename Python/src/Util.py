import os.path
import numpy as np
import re

DEBUG = False #Set True if debug messages are required


#Try to read the input File
def importText(path):
    if DEBUG: 
        print(f"Looking for file: {path}")

    if not os.path.isfile(path):
        print("Input File couldn't be found")
    else:
        print("Reading File:")
        with open(path) as file:
            content = file.read().splitlines()
        if DEBUG:
            for line in content:
                print(line)
        return np.array(content)

#Splits the input properly into a 2dimensional array
def splitLines(lines):
    out = np.array
    for line in lines:
        split = re.split(r"\s+",line)
        np.append(out, split, 2)
        
        if DEBUG:
            print(f"Splitting {line} into {split}")
    return out

#Parses a code made up of strings into ints
def parseInt(content):
    out = [[]]  
    if(not isinstance(content, list)):
        out = int(content)
    else:
        for i in range(len(content)):
            if(not isinstance(i, list)):
                out.append(int(i))
            else:
                out.append([int(item) for item in content])

    return out

#Transposes the matrix using numpy
def transpose(lines):
    return np.transpose(lines)