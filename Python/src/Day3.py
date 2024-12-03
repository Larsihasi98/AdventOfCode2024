import os.path
import Util
import re

#varialbles
debug = False #set to true for debug messages
part = 2

#File Directories
root = os.path.join(os.path.abspath(os.getcwd()),"Python")
resc = os.path.join(root,"Ressources")

code = Util.importText(os.path.join(resc,"input3.txt"))


if part == 1:
    content =[]

    #Search every line
    for line in code:
        content.append(re.findall(r"mul\(\d{1,3},\d{1,3}\)", line))

    #Add all instructions in a singular array:
    instructions = []
    for line in content:
        for instruction in line:
            instructions.append(instruction)

    if debug:
        for line in instructions:
            print(line)

    numbers = []

    for instruction in instructions:
        numbers.append(re.findall(r"\d{1,3}", instruction))
    numbers = Util.parseInt(numbers)

    result = 0
    for line in numbers:
        if(debug):
            print(line)
            print(f"Multiplying {line[0]} and {line[1]}")
        result += line[0]*line[1]

    print(f"The result is {result}.")


elif part == 2:
    content =[]

    #Search every line
    for line in code:
        content.append(re.findall(r"mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\)", line))

    #Add all instructions in a singular array:
    instructions = []
    for line in content:
        for instruction in line:
            instructions.append(instruction)

    if debug:
        for line in instructions:
            print(line)

    numbers = []
    do = True
    pattern = re.compile("mul\(\d{1,3},\d{1,3}\)")
    for instruction in instructions:
        if(instruction == "do()"):
            do = True
            if(debug):
                print(instruction)
                print(f"Permitting mult()")
        elif(instruction == "don't()"):
            do = False
            if(debug):
                print(instruction)
                print(f"preventing mult()")
        elif(do == True and pattern.match(instruction)):
            numbers.append(re.findall(r"\d{1,3}", instruction))
            if(debug):
                print(instruction)
                print(f"Adding Instruction")
        elif(do == False and pattern.match(instruction)):
            if(debug):
                print(instruction)
                print(f"Skipping instruction")
        else:
            print("What?")
    numbers = Util.parseInt(numbers)

    result = 0
    for line in numbers:
        if(debug):
            print(f"Multiplying {line[0]} and {line[1]}")
        result += line[0]*line[1]

    print(f"The result is {result}.")