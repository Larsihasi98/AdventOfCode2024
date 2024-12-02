import os.path
import Util
#varialbles
debug = False #set to true for debug messages

#File Directories
root = os.path.join(os.path.abspath(os.getcwd()),"Python")
resc = os.path.join(root,"Ressources")

#Functions
def isSafe(lineOfCode):
    increasing = True
    safe = True

    if(lineOfCode[0]>lineOfCode[1]):
        increasing = False
    if debug:
        print(f"The Code of {lineOfCode} is {"increasing" if increasing else "decreasing"}")
    for i in range(len(lineOfCode)-1):
        if(safe and increasing):
            if(lineOfCode[i+1]<=lineOfCode[i]):
                safe = False
                if debug:
                    print(f"The Code is unsafe because the {i+1}{"st."if i==0 else "nd." if i==1 else "rd." if i==2 else "th."} element of {lineOfCode[i+1]} is not higher than the {i}{"st."if i==0 else "nd." if i==1 else "rd." if i==2 else "th."} element of {lineOfCode[i]}")
            elif(lineOfCode[i+1]>lineOfCode[i]+3):
                safe = False
                if debug:
                    print(f"The Code is unsafe because the {i+1}{"st."if i==0 else "nd." if i==1 else "rd." if i==2 else "th."} element of {lineOfCode[i+1]} increases by more than 3 upon the {i}{"st."if i==0 else "nd." if i==1 else "rd." if i==2 else "th."} element of {lineOfCode[i]}")

        if(safe and not increasing):
            if(lineOfCode[i+1]>=lineOfCode[i]):
                safe = False
                if debug:
                    print(f"The Code is unsafe because the {i+1}{"st."if i==0 else "nd." if i==1 else "rd." if i==2 else "th."} element of {lineOfCode[i]} is not lower than the {i}{"st."if i==0 else "nd." if i==1 else "rd." if i==2 else "th."} element of {lineOfCode[i-1]}")
            elif(lineOfCode[i+1]<lineOfCode[i]-3):
                safe = False
                if debug:
                    print(f"The Code is unsafe because the {i+1}{"st."if i==0 else "nd." if i==1 else "rd." if i==2 else "th."} element of {lineOfCode[i]} dencreases by more than 3 upon the {i}{"st."if i==0 else "nd." if i==1 else "rd." if i==2 else "th."} element of {lineOfCode[i-1]}")
    if (safe and debug):
        print(f"The code of {lineOfCode} is safe")
    return safe
        
def isSafeWithDampener(lineOfCode):
    safe = False #Assuming it is unsafe. Should we find a safe variant it will be overridden
    for i in range(len(lineOfCode)):
        if(not safe): #Stop when safe variant was found
            adjustedCode = []
            if debug:
                print(f"Checking if the code of {lineOfCode} would be safe without the {i+1}{"st."if i==0 else "nd." if i==1 else "rd." if i==2 else "th."} digit of {lineOfCode[i]}")
            for j in range(len(lineOfCode)):
                #Building new line of Code:
                if(i>j):
                    adjustedCode.append(lineOfCode[j])
                elif(i<j):
                    adjustedCode.append(lineOfCode[j])
            if(isSafe(adjustedCode)):
                safe = True
                if debug:
                    print(f"Safe Variant was found")
    return safe

print("Advent of Code day 2:")
inputFile="input2.txt"

code = Util.parseInt(Util.splitLines(Util.importText(os.path.join(resc,inputFile))))

if(debug):
    for line in code:
        print(line)

safeCount = 0
for line in code:
    if(isSafe(line)):
        safeCount=safeCount+1
        if debug:
            print(f"A safe line was found")
    else:
        if(isSafeWithDampener(line)):
            safeCount = safeCount+1
            if debug:
                print(f"Using the Problem Dampener, a Safe line was found")

print(f"There are {safeCount} safe lines of code to be found.")

