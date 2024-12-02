import os.path
import Util
#varialbles
debug = True #set to true for debug messages

#File Directories
root = os.path.join(os.path.abspath(os.getcwd()),"Python")
resc = os.path.join(root,"Ressources")

print("Advent of Code day 1:")
inputFile="input1.txt"

content = Util.importText(os.path.join(resc,inputFile))
content = Util.splitLines(content)
columns = Util.transpose(content)


numbers = Util.parseInt(columns)

#sort lists
numbers[0].sort()
numbers[1].sort()

#calculate distance:
out = 0
for i in range(len(numbers[0])):
    if debug:
        print(f"Calculating the difference between the {i}{"st."if i==0 else "nd." if i==1 else "rd." if i==2 else "th."} Numbers of {numbers[0][i-1]} and {numbers[1][i-1]}.")
        print(f"Adding {abs(numbers[0][i-1] - numbers[1][i-1])} to {out}.")
    out = out + abs(numbers[0][i-1] - numbers[1][i-1])

print(f"Final Distance is {out}")


#calculate similarity:
out2 = 0

for i in numbers[0]:
    count = numbers[2].count(i)
    if debug:
        print(f"Found {count} instances of {i} in second list")
    out2 = out2 + (i*count)

print(f"Total Similarity Value calculated is {out2}")