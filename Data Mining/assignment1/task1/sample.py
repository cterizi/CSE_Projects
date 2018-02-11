import sys
import random

k = sys.argv[1]
reservoirList = []
outputLines = []
numberOFLine = 0

def sample():
	global k, reservoirList, numberOFLine, f

	for line in sys.stdin:
		numberOFLine = numberOFLine + 1
		if(len(reservoirList) + 1 <= int(k)):
			reservoirList.append(numberOFLine)
			outputLines.append(line)
		else:
			j = random.randint(1,numberOFLine)
			if(j <=  int(k)):
				reservoirList[j - 1] = numberOFLine
				outputLines[j - 1] = line
			else:
				x = 0
			
	print reservoirList
	for i in outputLines:
		print i

sample()