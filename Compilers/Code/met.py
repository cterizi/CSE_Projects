'''
TEAM: Chryssa Terizi
AM: 2553
USERNAME: cse32553 
'''
import sys

'''
	============================
	============================
	Klaseis gia ton pinaka 
	symbolwn.
	============================
	============================
'''
class Scope(object):
	def __init__(self, name, nestinglevel):
		self.entityList = []
		self.nestingLevel = nestinglevel
		self.name = name
		self.offset = 12

	def addEntity(self, entity):
		self.entityList.append(entity)
		if(entity.getType() != "function"):
			self.offset = self.offset + 4

	def getNestingLevel(self):
		return(self.nestingLevel)

	def getName(self):
		return(self.name)

	def getEntityList(self):
		return(self.entityList)

	def getOffset(self):
		return(self.offset)

	def printEntityList(self):
		for i in self.entityList:
			i.printEntity()

	def getFrameLength(self):
		return self.offset - 4;

class Variable(object):
	def __init__(self, name, offset):
		global scopeList
		self.name = name
		self.offset = offset
		self.type = "variable"
		self.nestingLevel = scopeList[-1].getNestingLevel()

	def getName(self):
		return(self.name)

	def getOffset(self):
		return(self.offset)

	def getType(self):
		return("variable")

	def getNestingLevel(self):
		return self.nestingLevel

	def printEntity(self):
		print([self.name, self.type, self.offset])

class Function(object):
	def __init__(self, name, startQuad, returnType, framelength):
		global scopeList
		self.name = name
		self.startQuad = startQuad
		self.returnType = returnType
		self.framelength = framelength
		self.argumentList = []
		self.type = "function"
		self.nestingLevel = scopeList[-1].getNestingLevel()

	def addArgument(self, argument):
		self.argumentList.append(argument)

	def setArgumentList(self, argumentList):
		self.argumentList = argumentList

	def getName(self):
		return(self.name)

	def getStartQuad(self):
		return(self.startQuad)

	def getReturnType(self):
		return(self.returnType)

	def getType(self):
		return(self.type)

	def getFramelength(self):
		return(self.framelength)

	def getArgumentList(self):
		return(self.argumentList)

	def getNestingLevel(self):
		return self.nestingLevel

	def printEntity(self):
		print([self.name, self.startQuad, self.returnType, self.argumentList, self.framelength])

class Temp(object):
	def __init__(self, name, offset):
		global scopeList
		self.name = name
		self.type = "temp"
		self.offset = offset

	def getName(self):
	   return(self.name)

	def getType(self):
		return(self.type)

	def getOffset(self):
		return(self.offset)

	def getNestingLevel(self):
		return self.nestingLevel

	def printEntity(self):
		print([self.name, self.type, self.offset])

class Parameter(object):
	def __init__(self, name, parMode, offset):
		global scopeList
		self.name = name
		self.type = "param"
		self.parMode = parMode
		self.offset = offset

	def getName(self):
		return(self.name)

	def getType(self):
		return(self.type)

	def getParMode(self):
		return(self.parMode)

	def getOffset(self):
		return(self.offset)

	def getNestingLevel(self):
		return self.nestingLevel

	def printEntity(self):
		print([self.name, self.type, self.parMode, self.offset])
		
arithmetic_operators = ["+", "-", "*", "/"]
relative_operators = ["<", ">", "="]
grouping_symbols = ["(", ")", "[", "]", "{", "}"]
reserved_words = ["and", "exit", "if", "program", "declare", "procedure", "in", "or" ,\
					"do", "function", "inout", "return", "else", "print", "not", "while" ,\
					"enddeclare", "call", "select", "default"]
other = [":", "\\", ";", " ", "\n", "\t"]

numberOfLine = 1
numberOfColumns = 0
tempLine = 1
tempColumn = 0
f = open(sys.argv[-1],"r")
line = f.readline()
letters = list(line)
previousWord = ""
reuseWord = 0
isFunction = []
counterFunction = 0
isDoWhile = []

'''
	============================
	============================
	Metavlhtes gia ton endiameso
	kwdika.
	============================
	============================
'''
sessionNames = []
exitList = []
callList = []

'''
	===============================
	===============================
	Metavlhtes gia ton tin paragwgh
	tou kwdika se C
	===============================
	===============================
'''
listWithVariablesForInitializeAtTop = []

'''
	===============================
	===============================
	Metavlhtes gia ton tin paragwgh
	tou pinaka symbolwn
	===============================
	===============================
'''
scopeList = []
parModeList = []


'''
	============================
	============================
	Metavlhtes gia ton teliko
	kwdika.
	============================
	============================
'''
finalCodeFile = open("finalCode.asm", "w")
relopDict = {"=":"beq", "<":"blt", ">":"bgt", "<=":"ble", ">=":"bge", "<>":"bne"}
opDict = {"+":"add", "-":"sub", "*":"mul", "/":"div"}
quadList = []
parCounter = 0
parList = []

def main():
	program()

def program():
	global isFunction, sessionNames

	isFunction.append(False)
	
	if(lexWord() == "program"):
		token = lexWord()
		writeFinalFile("j 90")
		if(id(token) == 1):
			addScope(token) #ps
			sessionNames.append(token) #ek
			genquad("begin_block", token, "_", "_") #ek
			block(0)
		else:
			error("id not found after keyword 'program'")
	else:
		error("keyword 'program' expected")

def block(start):
	global reuseWord, isFunction, counterFunction, sessionNames, quadList

	if(lexWord() == "{"):
		declarations()
		subprograms()
		sequence()
		if(lexWord() != "}"):
			error("keyword 'declare' is missing || symbol '}' is missing")
		else:
			if(len(isFunction) > 1 and isFunction[len(isFunction) - 1] == False):
				del isFunction[len(isFunction) - 1]
			else:
				if(counterFunction > 0):
					counterFunction = counterFunction - 1
				if(counterFunction != isFunction.count(True)):
					error("function should have at least one 'return'")
			if(len(sessionNames) == 1): #ek
				genquad("halt", "_", "_", "_") #ek
			genquad("end_block", sessionNames[-1], "_", "_") #ek
			del sessionNames[-1]
			
			if (len(scopeList) == 1):
				writeFinalFile("90: add $sp,$sp," + str(scopeList[0].getFrameLength()))
				writeFinalFile("move $s0,$sp")

			for i in range(len(quadList) - start): #tk
				quadToFile(quadList[start]) #tk
				del quadList[start] #tk

			if (len(scopeList) >= 2): #tl
				deleteScope() #ps
	else:
		error("symbol '{' is missing")

def declarations():
	global reuseWord 

	if(lexWord() == "declare"):
		varlist()
		if(lexWord() != "enddeclare"):
			error("keyword 'enddeclare' is missing")
	else:
		reuseWord = 1

def varlist():
	global reuseWord, scopeList

	token = lexWord()
	if(id(token) == 1):
		searchDuplicate(Variable(token, 0)) #ps
		scopeList[-1].addEntity(Variable(token, scopeList[-1].getOffset())) #ps
		while(True):
			token = lexWord()
			if(token == ","):
				token = lexWord()
				searchDuplicate(Variable(token, 0)) #ps
				scopeList[-1].addEntity(Variable(token, scopeList[-1].getOffset())) #ps
				if(id(token) != 1):
					error("id expected after ','")
			else:
				reuseWord = 1
				break
	else:
		reuseWord = 1

def subprograms():
	global reuseWord, isFunction, counterFunction, quadList

	while(True):
		token = lexWord()
		start = len(quadList)
		if(token == "procedure" or token == "function"):
			if(token == "procedure"):
				rtype = "void"
				isFunction.append(False)
			else:
				rtype = "int"
				counterFunction = counterFunction + 1
				isFunction.append(True)
			func(rtype, start)
		else:
			reuseWord = 1
			break

def func(rtype, start):
	global sessionNames, nextquad, scopeList

	token = lexWord()
	if(id(token) == 1):
		searchDuplicate(Function(token, 0, "", 0)) #ps gia na min yparxei diplo onoma synartisis
		scopeList[-1].addEntity(Function(token, nextquad + 10, rtype, scopeList[-1].getOffset())) #ps
		addScope(token) #ps   	
		sessionNames.append(token) #ek
		genquad("begin_block", token, "_", "_") #ek
		funcbody(start)
	else:
		error("id is wrong || id not found")

def funcbody(start):

	formalpars()
	block(start)

def formalpars():

	token = lexWord()
	if(token == "("):
		formalparlist()
		token = lexWord()
		if(token != ")"):
			error("')' is missing")
	else:
		error("'(' expected")

def formalparlist():
	global reuseWord

	formalparitem(0)
	while(True):
		token = lexWord()
		if(token == ","):
			formalparitem(1)
		else:
			reuseWord = 1
			break

def formalparitem(errorFlag):
	global reuseWord, scopeList

	token = lexWord()
	if(token == "in" or token == "inout"):
		if(token == "in"): #ps
			mode = "cv" #ps
		else: #ps
			mode = "ref" #ps
		token = lexWord()
		scopeList[-2].getEntityList()[-1].addArgument([token, mode]) #ps
		scopeList[-1].addEntity(Parameter(token, mode, scopeList[-1].getOffset())) #ps
		if(id(token) != 1):
			error("id expected")
	else:
		if(errorFlag == 0):
			reuseWord = 1
		else:
			error("'in' expected || 'inout' expected")

def sequence():
	global reuseWord

	statement()
	while(True):
		token = lexWord()
		if(token == ";"):
			statement()
		else:
			reuseWord = 1
			break

def bracketsSeq():
	global isDoWhile

	token = lexWord()
	if(token == "{"):
		sequence()
		token = lexWord()
		if(token != "}"):
			error("'}' is expected after sequence")
		else:
			del isDoWhile[len(isDoWhile) - 1]
	else:
		error("'{' is expected")

def brackOrStat():
	global reuseWord, isDoWhile

	token = lexWord()
	reuseWord = 1
	if(token == "{"):
		#del isDoWhile[len(isDoWhile) - 1]
		bracketsSeq()
	else:
		if((statement() == 0)):
			error("statement expected")
		else:
			token = lexWord()
			if(token != ";"):
				error("';' is expected after statement")
			del isDoWhile[len(isDoWhile) - 1]
	#else:
		#error("expected brackOrStat")

def statement():
	global reuseWord, isDoWhile

	token = lexWord()
	if(id(token) == 1):
		assignmentStat()
	elif(token == "if"):
		isDoWhile.append(False)
		ifStat()
	elif(token == "while"):
		isDoWhile.append(False)
		whileStat()
	elif(token == "select"):
		selectStat()
	elif(token == "do"):
		isDoWhile.append(True)
		doWhileStat()
	elif(token == "exit"):
		exitStat()
	elif(token == "return"):
		returnStat()
	elif(token == "print"):
		printStat()
	elif(token == "call"):
		callStat()
	else:
		reuseWord = 1
		if(token != ";"):
			return 0

def assignmentStat():
	global reuseWord

	reuseWord = 1 #ek
	myId = lexWord() #ek

	searchEntity(Variable(myId, 0))#ps

	token = lexWord()
	if(token == ":="):
		E_place = expression() #ek
		genquad(":=", E_place, "_", myId) #ek
	else:
		error("after id expected ':='")

def ifStat():
	global reuseWord

	token = lexWord()
	if(token == "("):
		B = condition() #ek
		B_True = B[0] #ek
		B_False = B[1] #ek
		token = lexWord()
		if(token == ")"):
			backpatch(B_True, nextQuad()) #ek
			brackOrStat()
			ifList = makeList(nextQuad()) #ek
			genquad("jump", "_", "_", "_") #ek
			backpatch(B_False, nextQuad()) #ek
			elsePart()
			backpatch(ifList, nextQuad()) #ek
		else:
			error("')' is expected after condition")
	else:
		error("'(' is expected")

def elsePart():
	global reuseWord, isDoWhile

	token = lexWord()
	if(token == "else"):
		isDoWhile.append(False)
		brackOrStat()
	else:
		reuseWord = 1

def whileStat():

	token = lexWord()
	if(token == "("):
		Bquad = nextQuad() #ek
		B = condition() #ek
		B_True = B[0] #ek
		B_False = B[1] #ek
		token = lexWord()
		if(token == ")"):
			backpatch(B_True, nextQuad()) #ek
			brackOrStat()
			genquad("jump", "_", "_", Bquad) #ek
			backpatch(B_False, nextQuad()) #ek
		else:
			error("')' is expected after condition")
	else:
		error("'(' is expected after while")

def selectStat():
	global isDoWhile

	token = lexWord()
	if(token == "("):
		token = lexWord()
		if(id(token) == 1):
			myId = token #ek
			searchEntity(Variable(myId, 0)) #ps
			token = lexWord()
			if(token != ")"):
				error("')' is expected after id")
			else:
				w = newTemp() #ek
				genquad(":=", 0, "_", w) #ek
				exitSelectList = [] #ek
				expectedConst = 1
				while(True):
					token = lexWord()
					if(str(token).isdigit()):
						if(int(token) == expectedConst):
							myConst = str(token) #ek
							isDoWhile.append(False)
							expectedConst = expectedConst + 1
						else:
							error("constants must be series")
						token = lexWord()
						if(token == ":"):
							B_False = makeList(nextQuad()) #ek
							genquad("<>", myId, myConst, "_") #ek
							genquad(":=", 1, "_", w) #ek
							brackOrStat()
							exitSelectList.append(nextQuad()) #ek
							genquad("=", 1, w, "_") #ek
							backpatch(B_False, nextQuad()) #ek
						else:
							error("':' is expected after constant")
					elif(token == "default"):
						token = lexWord()
						if(token == ":"):
							isDoWhile.append(False)
							brackOrStat()
							backpatch(exitSelectList, nextQuad()) #ek
						else:
							error("':' is expected after default")
						break
					else:
						error("constant or default expected")
		else:
			error("id is expected after '('")
	else:
		error("'(' is expected after select")

def doWhileStat():
	global exitList

	sizeExitList = len(exitList) #ek
	Dquad = nextQuad() #ek
	brackOrStat()
	token = lexWord()
	if(token == "while"):
		token = lexWord()
		if(token == "("):
			B = condition() #ek
			B_True = B[0] #ek
			B_False = B[1] #ek
			B_False = merge(B_False, exitList[sizeExitList:]) #ek
			token = lexWord()
			if(token != ")"):
				error("')' is expected after condition")
			backpatch(B_True, Dquad) #ek
			backpatch(B_False, nextQuad()) #ek
			exitList = exitList[0:sizeExitList] #ek
		else:
			error("'(' is expected")
	else:
		error("'while' is expected")

def exitStat():
	global isDoWhile, exitList

	exitList.append(nextQuad()) #ek
	genquad("jump", "_", "_", "_") #ek
	if(isDoWhile == []):
		error("'exit' can only be used inside 'do-while'")
	if(isDoWhile[len(isDoWhile) - 1] == False):
		error("'exit' can only be used inside 'do-while'")


def returnStat():
	global isFunction
	
	if(isFunction[len(isFunction) - 1] == False):
		error("'return' can only be used inside a function")
	else:
		del isFunction[len(isFunction) - 1]
	token = lexWord()
	if(token == "("):
		E_place = expression() #ek
		token = lexWord()
		if(token != ")"):
			error("')' is expected")
		genquad("retv", E_place, "_", "_")
	else:
		error("'(' is expected after return")

def printStat():

	token = lexWord()
	if(token == "("):
		E_place = expression() #ek
		token = lexWord()
		if(token != ")"):
			error("')' is expected")
		genquad("out", E_place, "_", "_") #ek
	else:
		error("'(' is expected after print")

def callStat():
	global parModeList
	token = lexWord()
	tokenAgain = token
	if(id(token) == 1):
		myId = token
		actualPars()
		tempFunction = Function(myId, 0, "", 0) #ps
		tempFunction.setArgumentList(parModeList) #ps
		searchEntity(tempFunction) #ps
		genquad("call", tokenAgain, "_", "_")
	else:
		error("id is expected after 'call'")

def actualPars():

	token = lexWord()
	if(token == "("):
		actualparlist()
		token = lexWord()
		if(token != ")"):
			error("')' is expected")
		if(isFunction[-1] == True): #ek
			genquad("par", newTemp(), "ret", "_") #ek
	else:
		error("'(' is expected")

def actualparlist():
	global reuseWord, parModeList

	parModeList = [] #ps
	actualparitem(0)
	while(True):
		token = lexWord()
		if(token == ","):
			actualparitem(1)
		else:
			reuseWord = 1
			break

def actualparitem(errorFlag):
	global reuseWord, callList, parModeList

	token = lexWord()
	if(token == "in"):
		E_place = expression() #ek
		if(isTemp(E_place)): #ek
			callList.append(["par", E_place, "cv", "_"]) #ek
			parModeList.append("cv") #ps
		else: #ek
			genquad("par", E_place, "cv", "_") #ek
			parModeList.append("cv") #ps
	elif(token == "inout"):
		token = lexWord()
		if(id(token) != 1):
			error("id expected after 'inout'")
		searchEntity(Variable(token, 0)) #ps
		genquad("par", token, "ref", "_") #ek
		parModeList.append("ref") #ps
	else:
		if(errorFlag == 1):
			error("'in' or 'inout' is expected")
		reuseWord = 1

def condition():
	global reuseWord

	Q1 = boolterm() #ek
	B_True = Q1[0] #ek
	B_False = Q1[1] #ek
	while(True):
		token = lexWord()
		if(token == "or"):
			backpatch(B_False, nextQuad()) #ek
			Q2 = boolterm() #ek
			B_True = merge(B_True, Q2[0]) #ek
			B_False = Q2[1] #ek
		else:
			reuseWord = 1
			break
	return([B_True, B_False]) #ek

def boolterm():
	global reuseWord

	R1 = boolfactor() #ek
	Q_True = R1[0] #ek
	Q_False = R1[1] #ek
	while(True):
		token = lexWord()
		if(token == "and"):
			backpatch(Q_True, nextQuad()) #ek
			R2 = boolfactor() #ek
			Q_False = merge(Q_False, R2[1]) #ek
			Q_True = R2[0] #ek
		else:
			reuseWord = 1
			break
	return([Q_True, Q_False]) #ek

def boolfactor():
	global reuseWord

	token = lexWord()
	isNot = False
	if(token == "not"):
		isNot = True
	else:
		reuseWord = 1

	token = lexWord()
	if(token == "["):
		B = condition() #ek
		R_True = B[0] #ek
		R_False = B[1] #ek
		token = lexWord()
		if(token != "]"):
			error("']' is expected after condition")
		if(isNot): #ek
			return([R_False, R_True]) #ek
		else: #ek
			return([R_True, R_False]) #ek
	else:
		if(isNot):
			error("'[' is expected after not")
		else:
			reuseWord = 1
			E1_place = expression() #ek
			if(relationalOper() == 0):
				error("relational operator expected")
			reuseWord = 1 #ek
			relop = lexWord() #ek
			E2_place = expression() #ek
			R_True = makeList(nextQuad()) #ek
			genquad(relop, E1_place, E2_place, "_") #ek
			R_False = makeList(nextQuad()) #ek
			genquad("jump", "_", "_", "_") #ek
			return([R_True, R_False]) #ek

def expression():
	global reuseWord

	optionalSign()
	T1_place = term() #ek
	while(True):
		if(addOper() == 1):
			reuseWord = 1 #ek
			addOperator = lexWord() #ek
			T2_place = term() #ek
			w = newTemp() #ek
			genquad(addOperator, T1_place, T2_place, w) #ek
			T1_place = w #ek
		else:
			reuseWord = 1
			break

	return(T1_place) #ek

def term():
	global reuseWord

	F1_place = factor() #ek
	while(True):
		if(mulOper() == 1):
			reuseWord = 1 #ek
			mulOperator = lexWord() #ek
			F2_place = factor() #ek
			w = newTemp() #ek
			genquad(mulOperator, F1_place, F2_place, w) #ek
			F1_place = w #ek
		else:
			reuseWord = 1
			break
	
	return(F1_place) #ek

def factor():

	token = lexWord()
	if(str(token).isdigit()):
		#dum = 1
		return(token) #ek
	elif(token == "("):
		F_place = expression() #ek
		token = lexWord()
		if(token != ")"):
			error("')' expected after expression")
		#print(F_place)
		return(F_place) #ek
	elif(id(token) == 1):
		return(idtail()) #ek
	else:
		error("wrong factor")

def idtail():
	global reuseWord, callList, parModeList
	
	reuseWord = 1 #ek
	myId = lexWord() #ek
	token = lexWord()
	if(token == "("):
		if(callList == []): #ek
			callList.append(1) #ek
		else: #ek
			callList[0] = callList[0] + 1 #ek
		actualparlist()
		w = newTemp() #ek
		tempFunction = Function(myId, 0, "", 0) #ps
		
		if(callList[0] == 1): #ek
			for i in range(1, len(callList)): #ek
				genquad("par", callList[i][1], callList[i][2], "_") #ek
			callList = [] #ek
		else: #ek
			callList[0] = callList[0] - 1 #ek
		tempFunction.setArgumentList(parModeList) #ps
		searchEntity(tempFunction) #ps
		genquad("par", w, "ret", "_") #ek
		token = lexWord()
		if(token != ")"):
			error("')' is expected")
		genquad("call", myId, "_", "_") #ek
		return(w) #ek
	else:
		searchEntity(Variable(myId, 0)) #ps
		reuseWord = 1
		return(myId) #ek

def relationalOper():
	global reuseWord

	token = lexWord()
	if(token in relative_operators or token == "<>" or token == "<=" or token == ">="):
		return 1
	else:
		reuseWord = 1
		return 0

def addOper():

	token = lexWord()
	if(token == "+" or token == "-"):
		return 1
	else:
		return 0

def mulOper():

	token = lexWord()
	if(token == "*" or token == "/"):
		return 1
	else:
		return 0

def optionalSign():
	global reuseWord

	if(addOper() != 1):
		reuseWord = 1

def id(word):
	#print "id {}".format(word)
	if(str(word).isdigit()):
		return 0
	if(word in arithmetic_operators):
		return 0
	if(word in relative_operators):
		return 0
	if(word in grouping_symbols):
		return 0
	if(word in reserved_words): 
		return 0
	if(word in other):
		return 0
	return 1

def lexWord():
	global f, numberOfColumns, numberOfLine, tempColumn, tempLine, reuseWord, previousWord

	if(reuseWord == 1):
		reuseWord = 0
		return previousWord

	while(True):
		word = lex()
		if(word != "eof"):
			if(len(word) > 30):
				word = word[0:30]
			if(word.isdigit()):
				word = int(word)
				if(word < -32768 or word > 32767):
					error("integer must be between [-32768, 32767]")
			
			if(word == "\*"):
				tempColumn = numberOfColumns
				tempLine = numberOfLine
				skipComments()
				continue
			reuseWord = 0
			previousWord = word
			return word
		break

	f.close()
   
def lex():
	global line, letters, numberOfLine, numberOfColumns
	word = ""

	if (skipSpaces() == 1):
		return "eof"

	while(True):
		if(eof()):
			if(word != ""):
				return word
			else:
				return "eof"

		if(letters[numberOfColumns].isalpha()):
			if(word != ""):
				if(word[0].isdigit()):
					error("id starts with digit")
			word = word + letters[numberOfColumns]
			numberOfColumns = numberOfColumns + 1
			continue

		if(letters[numberOfColumns].isdigit()):
			word = word + letters[numberOfColumns]
			numberOfColumns = numberOfColumns + 1
			continue

		if(letters[numberOfColumns] in arithmetic_operators):
			if(word != ""):
				return word
			else:
				word = word + letters[numberOfColumns]
				numberOfColumns = numberOfColumns + 1
				return word

		if(letters[numberOfColumns] in relative_operators):
			if(word != ""):
				return word

			word = word + letters[numberOfColumns]
			numberOfColumns = numberOfColumns + 1

			if(numberOfColumns < len(letters)):
				if(word != "="):
					if(letters[numberOfColumns] == "="):
						word = word + letters[numberOfColumns]
						numberOfColumns = numberOfColumns + 1
					elif(letters[numberOfColumns] == ">" and letters[numberOfColumns - 1] == "<"):
						word = word + letters[numberOfColumns]
						numberOfColumns = numberOfColumns + 1
			return word

		if(letters[numberOfColumns] in grouping_symbols or letters[numberOfColumns] == ","):
			if(word != ""):
				return word

			word = word + letters[numberOfColumns]
			numberOfColumns = numberOfColumns + 1
			return word

		if(letters[numberOfColumns] == ":"):
			if(word != ""):
				return word

			word = word + letters[numberOfColumns]
			numberOfColumns = numberOfColumns + 1

			if(numberOfColumns < len(letters)):
				if(letters[numberOfColumns] == "="):
					word = word + letters[numberOfColumns]
					numberOfColumns = numberOfColumns + 1
			return word

		if(letters[numberOfColumns] == "\\"):
			if(word != ""):
				return word

			word = word + letters[numberOfColumns]
			numberOfColumns = numberOfColumns + 1

			if(letters[numberOfColumns] != "*"):
				error("char \\ is appearing without char *")
			else:
				word = word + letters[numberOfColumns]
				numberOfColumns = numberOfColumns + 1
				return word

		if(letters[numberOfColumns] == ";"):
			if(word != ""):
				return word
			numberOfColumns = numberOfColumns + 1
			return ";"

		if(letters[numberOfColumns] == " "):
			#numberOfColumns = numberOfColumns + 1
			return word

		if(letters[numberOfColumns] == "\t"):
			#numberOfColumns = numberOfColumns + 1
			return word

		if(letters[numberOfColumns] == "\n" or ord(letters[numberOfColumns]) == 13):
			#numberOfColumns = 0
			#numberOfLine = numberOfLine + 1
			#print("new line after word")
			return word

		error("symbol '{}' is unknown".format(letters[numberOfColumns]))

def skipSpaces():
	global line, letters, numberOfLine, numberOfColumns
	
	#print(letters)
	while(True):
		#print("skipSpaces")
		if(eof()):
			return 1

		if(letters[numberOfColumns] == " " or letters[numberOfColumns] == "\t"):
			numberOfColumns = numberOfColumns + 1
			continue
		if(letters[numberOfColumns] == "\n" or ord(letters[numberOfColumns]) == 13):
			if(skipNewlines() == 1):
				return 1
			continue
		return 0

def skipNewlines():
	global line, letters, numberOfLine, numberOfColumns

	while(True):
		if (letters == []):
			return 1
		#print("skipNewLines")
		if(letters[numberOfColumns] == "\n" or ord(letters[numberOfColumns]) == 13):
			line = f.readline()
			letters = list(line)
			#print(letters)
			numberOfColumns = 0
			numberOfLine = numberOfLine + 1
			continue
		if(letters[numberOfColumns] == ""):
			#TODO an yparxei mh anamenwmeno telos programmatos ti tha kanw, h ama teleiwnei swsta
			print("End of file\n")
			return 1
		return 0

def eof():
	global numberOfColumns, letters, line, numberOfLine

	if(numberOfColumns >= len(letters)):
		error("Unexpected end of program")
		return True
	return False

def skipComments():
	global numberOfColumns, letters, numberOfLine, line, tempLine, tempColumn
	
	if(skipSpaces() == 1):
		error("comments don't close")
	
	if(numberOfColumns < len(letters)):
		if(letters[numberOfColumns] == "\\"):
			numberOfColumns = numberOfColumns + 1
			if(numberOfColumns < len(letters)):
				if(letters[numberOfColumns] == "*"):
					numberOfColumns = numberOfColumns + 1

		if(letters[numberOfColumns] != "*"):
			numberOfColumns = numberOfColumns + 1 
			return (skipComments())
	   
		if(letters[numberOfColumns] == "*"):
			numberOfColumns = numberOfColumns + 1
			if(numberOfColumns < len(letters)):
				if(letters[numberOfColumns] == "\\"):
					numberOfColumns = numberOfColumns + 1
					return "*\\"
		
	if(eof()):
		error("comments don't close")

def error(message):
	global numberOfColumns, numberOfLine, letters
	
	print("Error: Line {}, Column {}, {}".format(numberOfLine, numberOfColumns, message))
	sys.exit()

''' 
	================================
	================================
	Oi vohthitikes synarthseis gia 
	ton endiameso kwdika
	================================
	================================
'''
nextquad = 90
newtemp = -1
quads = {}

def genquad(op, x, y, z):
	global nextquad, quads, quadList

	nextquad = nextquad + 10
	quads[nextquad] = [op, x, y, z]
	quadList.append([nextquad, quads[nextquad]])
	return(quads[nextquad])

def nextQuad():
	global nextquad

	return(nextquad + 10)

def newTemp():
	global newtemp, scopeList

	newtemp = newtemp + 1
	scopeList[-1].addEntity(Temp("T_" + str(newtemp), scopeList[-1].getOffset())) #ps
	return("T_" + str(newtemp))

def emptyList():
	return(genquad("_", "_", "_", "_"))

def makeList(x):
	return([x])

def merge(list1, list2):
	return(list1 + list2)

def backpatch(list, z):
	global quads

	for i in list:
		if(i in quads):
			quads[i][3] = z
		else:
			error("label " + str(i) + " doesn't exist") 

def isTemp(variable):
	variable = str(variable)
	if(len(variable) > 2):
		if(variable[0:2] != "T_"):
			return(False)
		else:
			if(variable[2:].isdigit()):
				return(True)
			else:
				return(False)
	return(False)

''' 
	============================
	============================
	Synarthsh h opoia grafei sto 
	arxeio test.int. Ta dedomena
	ta pairnei apo to dictionary
	quads.
	============================
	============================
'''

def writeQuadsToFile():
	f = open("test.int", "w")

	for i in range(100, nextQuad(), 10):
		f.write(str(i) + ":" + str(quads[i]) + "\n")

	f.close()

'''
	============================
	============================
	Synarthseis gia na paraxthei
	to arxeio se c
	============================
	============================
'''

def makeCFile():
	global listWithVariablesForInitializeAtTop

	f = open("test.int", "r")
	cFile = open("tempTest.c", "w")
	variableLCount = 110
	counterOfProcedures = 0
	intoBlockCounter = 0

	writeMain(cFile)

	for line in f:
		tokensFromLine = splitLine(line)

		if(tokensFromLine[1] == "halt"):
			writeFinish(cFile, variableLCount)
			break
		if(len(tokensFromLine) == 6):
			if(intoBlockCounter > 0):
				variableLCount = variableLCount + 10
				continue
			if(tokensFromLine[2] == "="):
				cFile.write("\tL_" + str(variableLCount) +": " + tokensFromLine[-1] + " = " + str(tokensFromLine[3]) + ";\n")
				variableLCount = variableLCount + 10
				listWithVariablesForInitializeAtTop.append(tokensFromLine[-1])
				if(not(tokensFromLine[3].isdigit())):
					listWithVariablesForInitializeAtTop.append(tokensFromLine[3])
				continue
		if(tokensFromLine[1] == "+" or tokensFromLine[1] == "*" or tokensFromLine[1] == "-" or tokensFromLine[1] == "/"):
			if(intoBlockCounter > 0):
				variableLCount = variableLCount + 10
				continue
			cFile.write("\tL_" + str(variableLCount) + ": " + tokensFromLine[-1] + " = " + tokensFromLine[2] + " " +tokensFromLine[1] + " " +tokensFromLine[3] + ";\n")
			variableLCount = variableLCount + 10
			listWithVariablesForInitializeAtTop.append(tokensFromLine[-1])
			if(not(tokensFromLine[2].isdigit())):
				listWithVariablesForInitializeAtTop.append(tokensFromLine[2])
			if(not(tokensFromLine[3].isdigit())):
				listWithVariablesForInitializeAtTop.append(tokensFromLine[3])
			continue
		if(tokensFromLine[1] == "<>"):
			if(intoBlockCounter > 0):
				variableLCount = variableLCount + 10
				continue
			cFile.write("\tL_" + str(variableLCount) + ": if (" + tokensFromLine[2] + " " + "!=" + " " + tokensFromLine[3] + ") goto L_"+  tokensFromLine[-1] + ";\n")
			variableLCount = variableLCount + 10
			if(not(tokensFromLine[2].isdigit())):
				listWithVariablesForInitializeAtTop.append(tokensFromLine[2])
			if(not(tokensFromLine[3].isdigit())):
				listWithVariablesForInitializeAtTop.append(tokensFromLine[3])
			continue
		if(tokensFromLine[1] == "<" or tokensFromLine[1] == ">" or tokensFromLine[1] == ">=" or tokensFromLine[1] == "<="):
			if(intoBlockCounter > 0):
				variableLCount = variableLCount + 10
				continue
			cFile.write("\tL_" + str(variableLCount) + ": if (" + tokensFromLine[2] + " " + tokensFromLine[1] + " " + tokensFromLine[3] + ") goto L_"+  tokensFromLine[-1] + ";\n")
			variableLCount = variableLCount + 10
			if(not(tokensFromLine[2].isdigit())):
				listWithVariablesForInitializeAtTop.append(tokensFromLine[2])
			if(not(tokensFromLine[3].isdigit())):
				listWithVariablesForInitializeAtTop.append(tokensFromLine[3])
			continue
		if(tokensFromLine[1] == "jump"):
			if(intoBlockCounter > 0):
				variableLCount = variableLCount + 10
				continue
			cFile.write("\tL_" + str(variableLCount) + ": " + "goto L_"+  tokensFromLine[-1] + ";\n")
			variableLCount = variableLCount + 10
			continue
		if(tokensFromLine[1] == "out"):
			if(intoBlockCounter > 0):
				variableLCount = variableLCount + 10
				continue
			cFile.write("\tL_" + str(variableLCount) + ": " + "printf(\"%d\", " + tokensFromLine[2] + ");\n")
			variableLCount = variableLCount + 10
			if(not(tokensFromLine[2].isdigit())):
				listWithVariablesForInitializeAtTop.append(tokensFromLine[2])
			continue
		if(tokensFromLine[1] == "="):
			if(intoBlockCounter > 0):
				variableLCount = variableLCount + 10
				continue
			cFile.write("\tL_" + str(variableLCount) + ": " + "goto L_" + tokensFromLine[-1] + ";\n")
			variableLCount = variableLCount + 10
			continue
		if(tokensFromLine[1] == "begin_block" and not(int(tokensFromLine[0]) == 100)):
			intoBlockCounter = intoBlockCounter + 1
			variableLCount = variableLCount + 10
		if(tokensFromLine[1] == "end_block"):
			intoBlockCounter = intoBlockCounter - 1
			variableLCount = variableLCount + 10	
		if(tokensFromLine[1] == "call"):
			if(intoBlockCounter > 0):
				variableLCount = variableLCount + 10
				continue
			variableLCount = variableLCount + 10
			continue
		if(tokensFromLine[1] == "par"):
			if(intoBlockCounter > 0):
				variableLCount = variableLCount + 10
				continue
			variableLCount = variableLCount + 10
			continue
		if(tokensFromLine[1] == "retv"):
			if(intoBlockCounter > 0):
				variableLCount = variableLCount + 10
				continue
			variableLCount = variableLCount + 10
			continue

	listWithVariablesForInitializeAtTop = (list(set(listWithVariablesForInitializeAtTop)))
	f.close()

def writeMain(cFile):
	cFile.write("#include <stdio.h>\n")
	cFile.write("int main(){\n")
	cFile.write("\tL_0:\n")

def writeFinish(cFile, variableLCount):
	cFile.write("\tL_" + str(variableLCount) + ": {" + "}\n")
	cFile.write("}")
	cFile.close()
	finalCFile()

def splitLine(line):
	splitedLine = line.replace(":", " ")
	splitedLine = splitedLine.replace("[", "")
	splitedLine = splitedLine.replace("'", "")
	splitedLine = splitedLine.replace("]", "")
	splitedLine = splitedLine.replace(",", "")
	splitedLine = splitedLine.split(" ")
	splitedLine[-1] = splitedLine[-1].replace("\n", "")
	return (splitedLine)

def finalCFile():
	global listWithVariablesForInitializeAtTop

	f = open("tempTest.c", "r")
	finalCFile = open("test.c", "w")
	counter = 0
	listWithVariablesForInitializeAtTop = list(set(listWithVariablesForInitializeAtTop))
	
	for line in f:
		counter = counter + 1
		if(counter == 3):
			finalCFile.write("\tint ")
			if(len(listWithVariablesForInitializeAtTop) == 0):
				finalCFile.write(";\n")
				continue
			for i in range(len(listWithVariablesForInitializeAtTop)):
				if(i == len(listWithVariablesForInitializeAtTop) - 1):
					finalCFile.write(listWithVariablesForInitializeAtTop[i] + ";\n")
					continue
				finalCFile.write(listWithVariablesForInitializeAtTop[i] + ", ")
		finalCFile.write(line)

	f.close()
	finalCFile.close()

'''
	============================
	============================
	Synarthseis gia ton pinaka
	symvolwn
	============================
	============================
'''
def addScope(name):
	global scopeList

	scopeList.append(Scope(name, len(scopeList)))

def deleteScope():
	global scopeList

	del scopeList[-1]

def searchEntity(searchingEntity):
	global scopeList

	for scope in scopeList:
		for entity in scope.getEntityList():
			if(entity.getName() == searchingEntity.getName()):
				if(searchingEntity.getType() == "function"):
					if(entity.getType() != "function"):
						continue
					listA = entity.getArgumentList()
					listB = searchingEntity.getArgumentList()
					if(listA == listB):
						return(entity, scope)

					return(entity, scope)
				else:
					return()
	error("Entity \"" + str(searchingEntity.getName()) + "\" not found")

def searchEntityByName(entityName):
	global scopeList
	isFunction = False
	functionName = ""
	for scope in scopeList:
		if (isFunction):
			if (scope.getName() == functionName):
				for entity in scope.getEntityList():
					if (entity.getName() == entityName):
						return entity, scope
		for entity in scope.getEntityList():
			if (entity.getType() == "function"):
				for argument in entity.getArgumentList():
					if (argument[0] == entityName):
						isFunction = True
						functionName = entity.getName()
						break
				if (isFunction):
					break
						#return entity, scope

			elif (entity.getName() == entityName):
				return entity, scope
	return None

def searchFunctionByName(functionName):
	global scopeList

	for scope in scopeList:
		for entity in scope.getEntityList():
			if(entity.getType() == "function" and functionName == entity.getName()):
				return(entity, scope)

def searchDuplicate(searchingEntity):
	global isFunction, scopeList

	if(len(scopeList) == 1):
		index = 0
	else:
		index = len(isFunction) - 2
	for entity in scopeList[index].getEntityList():
		if(entity.getName() == searchingEntity.getName()):
			if(entity.getType() == searchingEntity.getType()):
				error("Duplicate of " + entity.getType() + " \"" + entity.getName() + "\"")

'''
	============================
	============================
	Synarthseis gia ton teliko
	kwdika
	============================
	============================
'''
def gnvlcode(variable):
	entity, scope = searchEntityByName(variable)
	currentNestingLevel = scopeList[-1].getNestingLevel()
	scopeNestingLevel = scope.getNestingLevel()
	writeFinalFile("lw $t0,-4($sp)")
	while(scopeNestingLevel < currentNestingLevel):
		writeFinalFile("lw $t0,-4($t0)")
		scopeNestingLevel = scopeNestingLevel + 1
	writeFinalFile("add $t0,$t0,-" + str(entity.getOffset()))

def loadvr(v, r):
	r = str(r)
	if(str(v).isdigit()):
		writeFinalFile("li $t" + r + "," + str(v))
		return()

	entity, scope = searchEntityByName(v)
	#r = str(r)
	if (scope.getNestingLevel() == 0):
		writeFinalFile("lw $t" + r + ",-" + str(entity.getOffset()) + "($s0)")
		return()

	nestingCondition = scope.getNestingLevel() == scopeList[-1].getNestingLevel()
	condition1 = entity.getType() == "param" and entity.getParMode() == "cv" and nestingCondition
	condition2 = entity.getType() == "temp"
	if (nestingCondition or condition1 or condition2):
		writeFinalFile("lw $t" + r + ",-" + str(entity.getOffset()) + "($sp)")
		return()

	condition = entity.getType() == "param" and entity.getParMode() == "ref" and scope.getNestingLevel() == scopeList[-1].getNestingLevel()
	if (condition):
		writeFinalFile("lw $t0,-" + str(entity.getOffset()) + "($sp)")
		writeFinalFile("lw $t" + r + ",($t0)")
		return()

	nestingCondition = scope.getNestingLevel() < scopeList[-1].getNestingLevel()
	condition = entity.getType() == "param" and entity.getParMode() == "cv" and nestingCondition
	if(nestingCondition or condition):
		gnvlcode(v)
		writeFinalFile("lw $t" + r + ",($t0)")
		return()

	condition = entity.getType() == "param" and entity.getParMode() == "ref" and scope.getNestingLevel() < scopeList[-1].getNestingLevel()
	if(condition):
		gnvlcode(v)
		writeFinalFile("lw $t0,($t0)")
		writeFinalFile("lw $t" + r + ",($t0)")
		return()

def storerv(r, v):
	entity, scope = searchEntityByName(v)
	r = str(r)

	if (scope.getNestingLevel() == 0):
		writeFinalFile("sw $t" + r + ",-" + str(entity.getOffset()) + "($s0)")
		return()

	nestingCondition = scope.getNestingLevel() == scopeList[-1].getNestingLevel()
	condition1 = entity.getType() == "param" and entity.getParMode() == "cv" and nestingCondition
	condition2 = entity.getType() == "temp"
	if (nestingCondition or condition1 or condition2):
		writeFinalFile("sw $t" + r + ",-" + str(entity.getOffset()) + "($sp)")
		return()

	condition = entity.getType() == "param" and entity.getParMode() == "ref" and scope.getNestingLevel() == scopeList[-1].getNestingLevel()
	if (condition):
		writeFinalFile("lw $t0,-" + str(entity.getOffset()) + "($sp)")
		writeFinalFile("sw $t" + r + ",($t0)")
		return()

	nestingCondition = scope.getNestingLevel() < scopeList[-1].getNestingLevel()
	condition = entity.getType() == "param" and entity.getParMode() == "cv" and nestingCondition
	if(nestingCondition or condition):
		gnvlcode(v)
		writeFinalFile("sw $t" + r + ",($t0)")
		return()

	condition = entity.getType() == "param" and entity.getParMode() == "ref" and scope.getNestingLevel() < scopeList[-1].getNestingLevel()
	if(condition):
		gnvlcode(v)
		writeFinalFile("lw $t0,($t0)")
		writeFinalFile("sw $t" + r + ",($t0)")
		return()

def quadToFile(quad):
	global opDict, relopDict, parCounter, parList, scopeList

	finalCodeFile.write(str(quad[0]) + ":")
	quad = quad[1]

	if(quad[0] == "par"):
		parList.append(quad)
		return()
	if(quad[0] == "call"):
		writePar(quad)
		parList = []
		writeCall(quad)
		return()
	if(quad[0] == "jump"):
		writeFinalFile("j " + str(quad[-1]))
		return()
	if(quad[0] == ":="):
		loadvr(quad[1], 1)
		storerv(1, quad[-1])
		return()
	if(quad[0] in relopDict):
		loadvr(quad[1], 1)
		loadvr(quad[2], 2)
		writeFinalFile(relopDict[quad[0]] + ",$t1,$t2," + str(quad[-1]))
		return()
	if(quad[0] in opDict):
		loadvr(quad[1], 1)
		loadvr(quad[2], 2)
		writeFinalFile(opDict[quad[0]] + "$t1,$t1,$t2")
		storerv(1, quad[-1])
		return()
	if(quad[0] == "out"):
		writeFinalFile("li $v0,1")
		writeFinalFile("li $a0," + str(quad[1]))
		writeFinalFile("syscall")
		return()
	if(quad[0] == "retv"):
		loadvr(quad[1], 1)
		writeFinalFile("lw $t0,-8($sp)")
		writeFinalFile("sw $t1,($t0)")
		return()
	if(quad[0] == "end_block" and len(scopeList) > 1):
		writeFinalFile("lw $ra,($sp)")
		writeFinalFile("jr $ra")

def writeCall(quad):
	global scopeList

	functionName = quad[1]

	entity, scope = searchFunctionByName(functionName)

	condition1 = entity.getNestingLevel() == scopeList[-1].getNestingLevel()
	if(condition1):
		writeFinalFile("lw $t0,-4($sp)")
		writeFinalFile("sw $t0,-4($fp)")
	else:
		writeFinalFile("sw $sp,-4($fp)")
	writeFinalFile("add $sp,$sp," + str(entity.getFramelength()))
	writeFinalFile("jal " + functionName)
	writeFinalFile("add $sp,$sp,-" + str(entity.getFramelength()))
	writeFinalFile("sw $ra,($sp)")

def writePar(quad):
	global parList

	functionName = quad[1]
	entity, scope = searchFunctionByName(functionName)
	argumentList = entity.getArgumentList()
	writeFinalFile("add $fp,$sp," + str(entity.getFramelength()))
	for i in range(len(parList)):
		q = parList[i]
		if (q[2] != "ret" and argumentList[i][1] != q[2]):
			error("Wrong parameter mode")
		if(q[2] == "cv"):
			loadvr(q[1], 0)
			writeFinalFile("sw $t0,-(12+" + str(4*(i + 1)) + ")($fp)")
			continue
		qEntity, qScope = searchEntityByName(q[1])
		if(q[2] == "ret"):
			writeFinalFile("add $t0,$sp,-" + str(qEntity.getOffset()))
			writeFinalFile("sw $t0,-8($fp)")
			continue
		if(scope.getNestingLevel() == qScope.getNestingLevel()):
			condition1 =  hasLocal(entity, qEntity) or hasArgument(entity, q[1], "cv")
			condition2 = hasArgument(entity, q[1], "ref")
			if(condition1):
				writeFinalFile("add $t0,$sp,-" + str(qEntity.getOffset()))
				writeFinalFile("sw $t0,-(12+" + str(4*i) + ")($fp)")
				continue
			if(condition2):
				writeFinalFile("lw $t0,-" + str(qEntity.getOffset()) + "($sp)")
				writeFinalFile("sw $t0,-(12+" + str(4*i) + ")($fp)")
				continue
		if(scope.getNestingLevel() != qScope.getNestingLevel()):
			condition1 =  hasLocal(entity, qEntity) or hasArgument(entity, q[1], "cv")
			condition2 = hasArgument(entity, q[1], "ref")
			if(condition1):
				gnvlcode(q[1])
				writeFinalFile("sw $t0,-(12+" + str(4*i) + ")($fp)")
				continue
			if(condition2):
				gnvlcode(q[1])
				writeFinalFile("lw $t0,($t0)")
				writeFinalFile("sw $t0,-(12+" + str(4*i) + ")($fp)")
				continue

def hasLocal(entity, local):
	if (local.getType() != "par"):
		return entity.getNestingLevel() == local.getNestingLevel()

	return(False)

def hasArgument(entity, argumentName, parMode):
	for arg in entity.getArgumentList():
		if(entity.getName() == argumentName and parMode == entity.getParMode()):
			return(True)
	return(False)

def writeFinalFile(line):
	global finalCodeFile
	finalCodeFile.write(line + "\n")

main()
writeQuadsToFile()
makeCFile()
finalCodeFile.close()