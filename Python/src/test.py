import numpy as np

input = np.array([[1,2],[3,4],[5,6],[7,8],[9,10]])

rows, cols=(2, int(len(input)/2))


# for line in input:
#     print(line)
# output = [[]*cols]*rows
# for line in input:
#     for i in range(2):
#         output[i-1].append(line[i-1])

# output[0].append(11)
# output[1].append(12)

# for line in output:
#     print(line)

# print(input[0][1])

output = np.transpose(input)

print(output)