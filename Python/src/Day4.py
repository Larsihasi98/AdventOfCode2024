import os.path
import Util
import re

#varialbles
debug = True #set to true for debug messages
part = 2

#File Directories
root = os.path.join(os.path.abspath(os.getcwd()),"Python")
resc = os.path.join(root,"Ressources")

content = Util.importText(os.path.join(resc,"input4.txt"))


print(f"Advent of Code day 4:")

def countInstances(code, word):
    pattern = re.compile(word)
    if(debug):
        print(f"seeking {word} in {code}")
    count = 0
    for line in code:
        betweenCount = len(re.findall(pattern, line))
        if debug:
            print(f"Found {betweenCount} instances of {word} in {line}")
        count += betweenCount
    return count

def wordPuzzle(input, word):

    code = input
    countHorizontal1 = countInstances(code, word)
    
    code = Util.transpose(Util.splitLetters(input))
    code = Util.uniteLetters(code)
    countVertical1 = countInstances(Util.transpose(code), word)

    code = Util.turnDiagonal(Util.splitLetters(input))
    code = Util.uniteLetters(code)
    countDiagonal1 = countInstances(Util.transpose(code), word)

    code = Util.turnDiagonal2(Util.splitLetters(input))
    code = Util.uniteLetters(code)
    countDiagonal2 = countInstances(Util.transpose(code), word)

    input2 = Util.uniteLetters(Util.flipArray(Util.splitLetters(input)))

    countHorizontal2 = countInstances(input2, word)
    
    code = Util.transpose(Util.splitLetters(input2))
    code = Util.uniteLetters(code)
    countVertical2 = countInstances(Util.transpose(code), word)

    code = Util.turnDiagonal(Util.splitLetters(input2))
    code = Util.uniteLetters(code)
    countDiagonal3 = countInstances(Util.transpose(code), word)

    code = Util.turnDiagonal2(Util.splitLetters(input2))
    code = Util.uniteLetters(code)
    countDiagonal4 = countInstances(Util.transpose(code), word)

    print(f"There are {countHorizontal1} horizonzal (left to right) instances, {countVertical1} vertical (up to down) instances, {countDiagonal1} diagonal (up left to down right), {countDiagonal2} diagonal (up right to down left), {countHorizontal2} horizonzal (right to left) instances, {countVertical2} vertical (down to up) instances, {countDiagonal3} diagonal (down right to up left) instances and {countDiagonal4} diagonal (down left to up right),  of {word} in input")

    return countHorizontal1+countVertical1+countDiagonal1+countDiagonal2+countHorizontal2+countVertical2+countDiagonal3+countDiagonal4

def patternSearch(input):
    size = len(input)
    code = Util.flattenToString(input)
    pattern1 = re.compile(r"M(?=\wS(\w{"+str(size-2)+r"})A(\w{"+str(size-2)+r"})M\wS(\w{0,"+str(size-3)+r"})(\w{"+str(size)+r"})*)")
    pattern2 = re.compile(r"S(?=\wM(\w{"+str(size-2)+r"})A(\w{"+str(size-2)+r"})S\wM(\w{0,"+str(size-3)+r"})(\w{"+str(size)+r"})*)")
    pattern3 = re.compile(r"M(?=\wM(\w{"+str(size-2)+r"})A(\w{"+str(size-2)+r"})S\wS(\w{0,"+str(size-3)+r"})(\w{"+str(size)+r"})*)")
    pattern4 = re.compile(r"S(?=\wS(\w{"+str(size-2)+r"})A(\w{"+str(size-2)+r"})M\wM(\w{0,"+str(size-3)+r"})(\w{"+str(size)+r"})*)")
    
    amount = 0

    if debug:
        print(f'For Pattern: {pattern1}')
    amount += len(re.findall(pattern1, code))

    if debug:
        print(f"Found {amount} so far")

    if debug:
        print(f'For Pattern: {pattern2}')
    amount += len(re.findall(pattern2, code))

    if debug:
        print(f"Found {amount} so far")

    if debug:
        print(f'For Pattern: {pattern3}')
    amount += len(re.findall(pattern3, code))

    if debug:
        print(f"Found {amount} so far")

    if debug:
        print(f'For Pattern: {pattern4}')
    amount += len(re.findall(pattern4, code))

    if debug:
        print(f"Found {amount} so far")
    
    return amount

if(part == 1):
    word = "XMAS"

    print(f"The word {word} appears {wordPuzzle(content, word)} times") 

if(part ==2):
    print(f"Who thinks of this bullshit man")

    xmas = patternSearch(content)

    print(f"Found an X-MAS {xmas} times")
