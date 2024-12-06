import os.path
import Util
import re
import numpy as np

#varialbles
debug = False #set to true for debug messages
part = 2

#File Directories
root = os.path.join(os.path.abspath(os.getcwd()),"Python")
resc = os.path.join(root,"Ressources")

content = Util.importText(os.path.join(resc,"input6.txt"))


print(f"Advent of Code day 6:")

def parseContent(content):

    input = Util.splitLetters(content)
    
    obstacles = np.zeros((len(content[0]), len(content))) #create an array of obstacles with the lengths of x = lines and y = chars in line
    visited = np.zeros((len(content[0]), len(content))) #Fields visited by guard
    guardposition = [] #Coordinates for guard
    guardrotation = 0 #Rotation Guard. 0=north, 1=east, 2=south, 3=west

    if(debug):
        print(f"Creating Maps")
    for x in range(len(input[0])):
        for y in range(len(input)):
            if(input[x][y] == '#'):
                if(debug):
                    print(f"Obstacle found at coordinates x = {x}, y = {y}")
                obstacles[x][y] = 1

            if(input[x][y] == '^'):
                if(debug):
                    print(f"Guard starts at coordinates x = {x}, y = {y}")
                guardposition = [x,y]
    return [obstacles, visited, guardposition, guardrotation]

def printRotation(rotation):
    switch={0:'^', 1:">",2:"v",3:"<"}
    return switch.get(rotation, "invalid direction")


def printMap(obstacles, visited, guardPosition, guardRotation):
    print("Displaying current Map")

    #printing coordinats
    print("\\ y", end="")
    for y in range(len(obstacles)):
        print(f"{(y//100)}",end="")
    print("\n \\ ", end="")
    for y in range(len(obstacles)):
        print(f"{(y//10)%10}",end="")
    print("\nx \\", end = "")
    for y in range(len(obstacles)):
        print(f"{y%10}",end="")
    print("")

    #printMaps:
    for x in range(len(obstacles[0])):
        print(f"{x:03d}",end="")
        for y in range(len(obstacles)):
            if(guardPosition == [x,y]):
                print(printRotation(guardRotation), end="")
            elif(visited[x][y] > 0):
                print(f"X", end="")
            elif(obstacles[x][y] == 1):
                print(f"#", end="")
            else:
                print(f".", end="")
        print("")
            
def printLoopMap(obstacles, visited, guardPosition, guardRotation, obstX, obstY):
    print("Loop Possibility with Obstacle at x = {obstX} / y = {obstY}")

    #map = np.char.array((len(obstacles[0]), len(obstacles)), unicode=True)

    map = []
    for x in range(len(obstacles[0])):
        map.append([])
        for y in range(len(obstacles)):
            map[x].append(".")

    for x in range(len(obstacles[0])):
        for y in range(len(obstacles)):
            map[x][y]='.'
            if(obstacles[x][y]>0):
                map[x][y]="#"
            if x==obstX and y==obstY:
                map[x][y]="0"
            if(visited[x][y]>0):
                map[x][y]="°"
            if(visited[x][y]>1):
                map[x][y]="+"
            if(x==guardPosition[0] and y == guardPosition[1]):
                map[x][y]=printRotation(guardRotation)


    #printing coordinats
    print("\\ y", end="")
    for y in range(len(obstacles)):
        print(f"{(y//100)}",end="")
    print("\n \\ ", end="")
    for y in range(len(obstacles)):
        print(f"{(y//10)%10}",end="")
    print("\nx \\", end = "")
    for y in range(len(obstacles)):
        print(f"{y%10}",end="")
    print("")

    #printMap:
    for x in range(len(obstacles[0])):
        print(f"{x:03d}",end="")
        for y in range(len(obstacles)):
            print (map[x][y], end="")
        print("")



def guardPath(obstacles, visited, guardPosition, guardRotation):
    outOfBounds = False
    stepCounter = 0
    max = 10000000000

    while(not outOfBounds and stepCounter < max):
        (x,y) = guardPosition

        visited[x][y]+=1

        #Facing North
        if(guardRotation == 0):
            if(x == 0):
                outOfBounds = True #Reached End of Map

            else:
                if(obstacles[x-1][y] > 0):
                    guardRotation = (guardRotation + 1)%4 #Hit Obstacle.Turn right
                else:
                    guardPosition[0] -= 1 #Go north 1
                    visited[x-1][y] +=1 #Increase visit counter
        
        #Facing east
        elif(guardRotation == 1):
            if(y == len(obstacles[0]-1)):
                outOfBounds = True #Reached End of Map

            else:
                if(obstacles[x][y+1] > 0):
                    guardRotation = (guardRotation + 1)%4 #Hit Obstacle.Turn right
                else:
                    guardPosition[1] += 1 #Go east 1
                    visited[x][y+1] +=1 #Increase visit counter
        
        elif(guardRotation == 2):
            if(x == len(obstacles)-1):
                outOfBounds = True #Reached End of Map

            else:
                if(obstacles[x+1][y] > 0):
                    guardRotation = (guardRotation + 1)%4 #Hit Obstacle.Turn right
                else:
                    guardPosition[0] += 1 #Go south 1
                    visited[x+1][y] +=1 #Increase visit counter
        
        elif(guardRotation == 3):
            if(y == 0):
                outOfBounds = True #Reached End of Map

            else:
                if(obstacles[x][y-1] > 0):
                    guardRotation = (guardRotation + 1)%4 #Hit Obstacle.Turn right
                else:
                    guardPosition[1] -= 1 #Go West
                    visited[x][y-1] +=1 #Increase visit counter

        stepCounter+=1
        if(debug):
            printMap(obstacles,visited,guardPosition,guardRotation)
            print(f"Current Guard position: x={guardPosition[0]}, y={guardPosition[1]}; facing{printRotation(guardRotation)}\n")

    guardPosition[0]=-1
    guardPosition[1]=-1

def guardLoops(tempObstacles, tempVisited, tempPosition, tempRotation):
    outOfBounds = False
    loop = False
    stepCounter = 0
    pos = tempPosition
    
    
    loopCheck=np.zeros((len(tempObstacles[0]),len(tempObstacles),4))

    while((not outOfBounds) and (not loop)):
        (x,y) = pos
        tempVisited[x][y]+=1


        #Facing North
        if(tempRotation == 0):
            if(x <= 0):
                outOfBounds = True #Reached End of Map

            else:
                if(tempObstacles[x-1][y] > 0):
                    tempRotation = (tempRotation + 1)%4 #Hit Obstacle.Turn right
                    if(loopCheck[x][y][tempRotation] > 0):#bumped into this before
                        loop = True
                    loopCheck[x][y][tempRotation] +=1
                else:
                    pos[0] -= 1 #Go north 1
                    tempVisited[x-1][y] +=1 #Increase visit counter
        
        #Facing east
        elif(tempRotation == 1):
            if(y >= len(tempObstacles[0])-1):
                outOfBounds = True #Reached End of Map

            else:
                if(tempObstacles[x][y+1] > 0):
                    tempRotation = (tempRotation + 1)%4 #Hit Obstacle.Turn right
                    if(loopCheck[x][y][tempRotation] > 0):#bumped into this before
                        loop = True
                    loopCheck[x][y][tempRotation] +=1
                else:
                    pos[1] += 1 #Go east 1
                    tempVisited[x][y+1] +=1 #Increase visit counter
        
        elif(tempRotation == 2):
            if(x >= len(tempObstacles)-1):
                outOfBounds = True #Reached End of Map

            else:
                if(tempObstacles[x+1][y] > 0):
                    tempRotation = (tempRotation + 1)%4 #Hit Obstacle.Turn right
                    if(loopCheck[x][y][tempRotation] > 0):#bumped into this before
                        loop = True
                    loopCheck[x][y][tempRotation] +=1
                else:
                    pos[0] += 1 #Go south 1
                    tempVisited[x+1][y] +=1 #Increase visit counter
        
        elif(tempRotation == 3):
            if(y <= 0):
                outOfBounds = True #Reached End of Map

            else:
                if(tempObstacles[x][y-1] > 0):
                    tempRotation = (tempRotation + 1)%4 #Hit Obstacle.Turn right
                    if(loopCheck[x][y][tempRotation] > 0):#bumped into this before
                        loop = True
                    loopCheck[x][y][tempRotation] +=1
                else:
                    pos[1] -= 1 #Go West
                    tempVisited[x][y-1] +=1 #Increase visit counter

        stepCounter+=1
        if(debug):
            printMap(tempObstacles,tempVisited,pos,tempRotation)
            print(f"Current Guard position: x={pos[0]}, y={pos[1]}; facing{printRotation(tempRotation)}\n")

    if(debug):
        printMap(tempObstacles,tempVisited,pos,tempRotation)

    return loop

if part == 1:
    (obstacles, visited, guardPosition, guardRotation) = parseContent(content)

    print(f"\nThe Starting Map:")
    printMap(obstacles, visited, guardPosition, guardRotation)


    guardPath(obstacles, visited, guardPosition, guardRotation)
    print(f"\nThe Map after the Guard finished their path:")
    printMap(obstacles,visited,guardPosition,guardRotation)

    print(f"They visited a total of {len(visited[visited>0])}")


if part == 2:
    (obstacles, visited, guardPosition, guardRotation) = parseContent(content)

    print(f"\nThe Starting Map:")
    printMap(obstacles, visited, guardPosition, guardRotation)

    loopMaps = []
    loopObstacles = 0
    for i in range(len(obstacles[0])):
        for j in range(len(obstacles)):
            if(obstacles[i][j] == 0):
                if(debug):
                    print(f"Adding an obstacle to x={2};y={j}")
                obst = np.copy(obstacles)
                vis = np.copy(visited)
                pos=[guardPosition[0],guardPosition[1]]
                rot = guardRotation
                obst[i][j] = 1
                loops = guardLoops(obst, vis, pos, rot)
                if(loops):
                    if(debug):
                        print(f"Causing a Loop when adding an obstacle to x={2};y={j}")
                    loopObstacles+=1
                    loopMaps.append([obst,vis,pos,rot,i,j])
            else:
                if(debug):
                    print(f"There already is an obstacle at x={2};y={j}")


    
    print(f"\nWe found a total of {loopObstacles} possible Obstacles that Loop.")
    if(debug):        
        for map in loopMaps:
            (obst, vis, guard, pos, ox, oy)=map
            print(F"Possible Loop Obstacle at{map[4]}/{map[5]}")
            printLoopMap(obst, vis, guardPosition, guardRotation, ox, oy)
