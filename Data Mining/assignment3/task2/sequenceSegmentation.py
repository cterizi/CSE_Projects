def sequenceSegmentation(numbers, k):
    bounds = set([])
    for i in range(k-1):
        minError = calculateError(numbers, bounds)
        minIndex = 0
        for j in range(1, len(numbers)-1):
            if j in bounds: continue
            bounds.add(j)
            error = calculateError(numbers, sorted(bounds))
            bounds.remove(j)
            if error < minError:
                minError = error
                minIndex = j
        bounds.add(minIndex)
        
    print "BOUNDS " + str(sorted(bounds))
    print "SUM " + str(calculateError(numbers, sorted(bounds)))
    print "----TEAMS----"
    printTeams(numbers, bounds)

def calculateError(numbers, boundaries):
    error = 0
    leftBoundary = 0
    for boundary in boundaries:
        error += numbers[boundary] - numbers[leftBoundary]
        leftBoundary = boundary + 1
        
    error += numbers[-1] - numbers[leftBoundary]
    return error

def printTeams(numbers, boundaries):
    leftBoundary = 0
    for boundary in boundaries:
        print numbers[leftBoundary:boundary+1]
        leftBoundary = boundary + 1
    print numbers[leftBoundary:len(numbers)+1]
    
sequenceSegmentation([5, 10, 11, 50, 60, 100, 1000], 4)