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
        return content

#Splits the input properly into a 2dimensional array
def splitLines(lines):
    out = []
    for line in lines:
        split = re.split(r"\s+",line)
        out.append(split)
        
        if DEBUG:
            print(f"Splitting {line} into {split}")
    return out

#Parses a code made up of strings into ints
def parseInt(content):
    
    if(not isinstance(content, list)):
        content = int(content) # Check if input is not a list
    else:
        for i in range(len(content)):
            if(not isinstance(content[i], list)):
                content[i]=int(content[i]) # Check if input is 1 dimensional
            else:
                for j in range(len(content[i])):
                    content[i][j]=int(content[i][j]) # Check if input is 2 dimensional
    return content

#Transposes the matrix using numpy
def transpose(lines):

    array = np.array(lines)
    array = np.transpose(array)

    return array.tolist()