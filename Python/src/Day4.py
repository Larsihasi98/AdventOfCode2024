import os.path
import Util
import re

#varialbles
debug = False #set to true for debug messages
part = 1

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

if(part == 1):
    word = "XMAS"

    print(f"The word {word} appears {wordPuzzle(content, word)} times") 