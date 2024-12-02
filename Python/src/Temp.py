#Try to read the input File
if debug: 
    print(f"Root Directory: {root}, ressources: {resc} leading to file: {filepath}")

if not os.path.isfile(filepath):
    print("Input File couldn't be found")
else:
    print("Read File:")
    with open(filepath) as file:
        content = file.read().splitlines()
    if debug:
        for line in content:
            print(line)

#split string into two numbers:
num1 = []
num2 = []
for line in content:
    split = re.split("\s+",line)
    num1.append(int(split[0]))
    num2.append(int(split[1]))

#Add to new lists
num1.sort()
num2.sort()

#calculate distance:
out = 0
for i in range(len(num1)):
    if debug:
        print(f"Calculating the difference between the {i}{".st"if i==1 else ".nd" if i==2 else ".rd" if i==3 else ".th"} Numbers of {num1[i-1]} and {num2[i-1]}.")
        print(f"Adding {abs(num1[i-1] - num2[i-1])} to {out}.")
    out = out + abs(num1[i-1] - num2[i-1])

print(f"Final Distance is {out}")

#calculate similarity:
out2 = 0

for i in num1:
    count = num2.count(i)
    if debug:
        print(f"Found {count} instances of {i} in second list")
    out2 = out2 + (i*count)

print(f"Total Similarity Value calculated is {out2}")