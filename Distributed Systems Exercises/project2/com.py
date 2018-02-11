#Chrysoula Terizi AM.2553 chryssa.terizi@gmail.com
#TEAM: Panagiotis Kouzouglidis AM.2276
import optparse
from time import sleep
from twisted.internet.protocol import Protocol, ClientFactory
from twisted.internet import reactor
import time

connectionList = []
vectorClock = [0, 0, 0]
counterQuestions = 0
f = None
notDelivered = ""
X = 0
Y = 0
Z = 0

def parse_args():
	usage = """usage: %prog [options] [process_id] [hostname]:port

	python peer.py 0 127.0.0.1:port """

	parser = optparse.OptionParser(usage)

	_, args = parser.parse_args()

	if len(args) != 2:
		print parser.format_help()
		parser.exit()

	process_id, addresses = args

	def parse_address(addr):
		if ':' not in addr:
			host = '127.0.0.1'
			port = addr
        	else:
			host, port = addr.split(':', 1)

		if not port.isdigit():
			parser.error('Ports must be integers.')

		return host, int(port)

	return process_id, parse_address(addresses)

class Peer(Protocol):
	connected = False

	def __init__(self, factory, process_id):
		global nextToSendAnswer, f

		self.process_id = process_id
		self.factory = factory

		f = open('delivered-messages-' + self.process_id + '.txt', 'w')

	def connectionMade(self):
		print "connectionMade"
		global vectorClock, connectionList

		self.connected = True
		connectionList.append(self.transport)
		if (len(connectionList) == 2):
			print ('vector clock for pid ' + str(self.process_id) + ' ' +str(vectorClock) + '\n')
			if(self.process_id == '0'):
				reactor.callLater(3, self.sendQuestion)

	def wrongSendQuestion(self):
		question = 'question ' + str(1) + ' ' + str(1) + ' ' + str(0) + ' ' + str(0)
		connectionList[1].write(question)

 	def sendQuestion(self): #xrhsimopoiei h 0
		print "sendQuestion"
		global counterQuestions, vectorClock, connectionList, f

		counterQuestions = counterQuestions + 1
		vectorClock[int(self.process_id)] = vectorClock[int(self.process_id)] + 1

		try:
			question = 'question ' + str(counterQuestions) + ' ' + str(vectorClock[int(self.process_id)]) + ' ' + str(vectorClock[int(self.process_id) + 1]) + ' ' + str(vectorClock[int(self.process_id) + 2])
			print(question)
			f.write(question)
			f.write('\n')
			connectionList[0].write(question)
			#connectionList[1].write(question)
			if(counterQuestions != 1):
				print(question)
				connectionList[1].write(question)
			else:
				reactor.callLater(1, self.wrongSendQuestion)
		except Exception, ex1:
			print "Exception trying to send question: ", ex1.args[0]

		if self.connected == True:
			if (counterQuestions < 20):
				reactor.callLater(3, self.sendQuestion)
			else:
				print('questions = ' + str(counterQuestions) + ' \n')

	def sendAnswer(self, numberOfQuestion): #xrhsimopoioun 1,2 
		print "sendAnswer"
		global vectorClock

		vectorClock[int(self.process_id)] = vectorClock[int(self.process_id)] + 1
		try:
			if(int(self.process_id) == 1):
				answer = 'answer for ' + str(numberOfQuestion) + ' question from ' + str(self.process_id) + ' ' + str(vectorClock[int(self.process_id)]) + ' ' + str(vectorClock[int(self.process_id) - 1 ]) + ' ' + str(vectorClock[int(self.process_id) + 1])
				print(answer)
				f.write(answer)
				f.write('\n')
			else:
				answer = 'answer for ' + str(numberOfQuestion) + ' question from ' + str(self.process_id) + ' ' + str(vectorClock[int(self.process_id)]) + ' ' + str(vectorClock[int(self.process_id) - 2 ]) + ' ' + str(vectorClock[int(self.process_id) - 1])
				print(answer)
				f.write(answer)
				f.write('\n')
			connectionList[0].write(answer)
			connectionList[1].write(answer)
		except Exception, e:
			print "Exception trying to send answer: ",e.args[0]

	def dataReceived(self, data):
		global vectorClock, f, X, Y, Z, notDelivered

		if(int(self.process_id) == 0):
			print "AnswerReceived"
			y = data.split(' ')

			if(int(y[5]) == 1):
				vectorClock[int(self.process_id) + 1] = max(int(y[6]), vectorClock[int(self.process_id) + 1])
				vectorClock[int(self.process_id)] = max(int(y[7]), vectorClock[int(self.process_id)])
				vectorClock[int(self.process_id) + 2] = max(int(y[8]), vectorClock[int(self.process_id) + 2])
			else:
				vectorClock[int(self.process_id) + 2] = max(int(y[6]), vectorClock[int(self.process_id) + 2])
				vectorClock[int(self.process_id)] = max(int(y[7]), vectorClock[int(self.process_id)])
				vectorClock[int(self.process_id) + 1] = max(int(y[8]), vectorClock[int(self.process_id) + 1])
				
			print(data)

			f.write(data)
			f.write('\n')
		else:
			x = data.split(' ')

			if(x[0] == "question"):
				print "questionReceived"
				if(int(self.process_id) == 1):
					vectorClock[int(self.process_id) - 1] = max(int(x[2]), vectorClock[int(self.process_id) - 1])
					vectorClock[int(self.process_id)] = max(int(x[3]), vectorClock[int(self.process_id)])
					vectorClock[int(self.process_id) + 1] = max(int(x[4]), vectorClock[int(self.process_id) + 1])
					
					print('pid = 1 ' + data + ' vector clock ' + str(vectorClock) + '\n')
					
					f.write(data)
					f.write('\n')
					if(notDelivered != ""):
						print(notDelivered)
						f.write(notDelivered)
						f.write('\n')
						notDelivered = ""
						vectorClock[int(self.process_id) + 1] = X
						vectorClock[int(self.process_id) - 1] = Y
						vectorClock[int(self.process_id)] = Z

				else:
					vectorClock[int(self.process_id) - 2] = max(int(x[2]), vectorClock[int(self.process_id) - 2])
					vectorClock[int(self.process_id) - 1] = max(int(x[3]), vectorClock[int(self.process_id) - 1])
					vectorClock[int(self.process_id)] = max(int(x[4]), vectorClock[int(self.process_id)])
					
					print('pid = 2 ' + ' ' + data + ' vector clock ' + str(vectorClock) + '\n')
					
					f.write(data)
					f.write('\n')

					if(notDelivered != ""):						
						print(notDelivered)
						f.write(notDelivered)
						f.write('\n')
						notDelivered = ""
						vectorClock[int(self.process_id) - 1] = X
						vectorClock[int(self.process_id) - 2] = Y
						vectorClock[int(self.process_id)] = Z

				if(int(x[1]) % 2 == 2 - int(self.process_id)):
					self.sendAnswer(x[1])
				else:
					print("@\n")

			else:
				print "answerReceived"

				if(int(self.process_id) == 1):
					if(not(int(x[7]) <= vectorClock[0] and int(x[6]) == vectorClock[2] + 1)):
						print("NOT DELIVER \n")
						notDelivered = data
						X = max(int(x[6]), vectorClock[int(self.process_id) + 1])
						Y = max(int(x[7]), vectorClock[int(self.process_id)] -1 )
						Z = max(int(x[8]), vectorClock[int(self.process_id)])
					else:
						vectorClock[int(self.process_id) + 1] = max(int(x[6]), vectorClock[int(self.process_id) + 1])
						vectorClock[int(self.process_id) - 1] = max(int(x[7]), vectorClock[int(self.process_id)] -1 )
						vectorClock[int(self.process_id)] = max(int(x[8]), vectorClock[int(self.process_id)])
						f.write(data)
						f.write('\n')
						print('pid = 1 ' + data + ' vector clock ' + str(vectorClock) + '\n')
					
				else:
					if(not(int(x[7]) <= vectorClock[0] and int(x[6]) == vectorClock[1] + 1)):
						print("NOT DELIVER \n")
						notDelivered = data
						X = max(int(x[6]), vectorClock[int(self.process_id) - 1])
						Y = max(int(x[7]), vectorClock[int(self.process_id) - 2])
						Z = max(int(x[8]), vectorClock[int(self.process_id)])
					else:
						vectorClock[int(self.process_id) - 1] = max(int(x[6]), vectorClock[int(self.process_id) - 1])
						vectorClock[int(self.process_id) - 2] = max(int(x[7]), vectorClock[int(self.process_id) - 2])
						vectorClock[int(self.process_id)] = max(int(x[8]), vectorClock[int(self.process_id)])
						f.write(data)
						f.write('\n')
						print('pid = 2 ' + data + ' vector clock' + str(vectorClock) + '\n')
					
	def connectionLost(self, reason):
		print "connectionLost"
		f.close()

	def done(self):
		print "done"

class PeerFactory(ClientFactory):
	def __init__(self, process_id, fname):
		self.process_id = process_id
	def finished(self, arg):
		self.acks = arg
		self.report()
	def report(self):
		print 'Received %d acks' % self.acks
	def clientConnectionFailed(self, connector, reason):
		print 'Failed to connect to:', connector.getDestination()
		self.finished(0)
	def clientConnectionLost(self, connector, reason):
		print 'Lost connection.  Reason:', reason
	def startFactory(self):
		print "startFactory"
	def stopFactory(self):
		print "stopFactory"
	def buildProtocol(self, addr):
		print "buildProtocol"
		protocol = Peer(self, self.process_id)
		return protocol

if __name__ == '__main__':
	process_id, address = parse_args()

	if process_id == '0': #port:2553
		factory = PeerFactory('0', 'log')
		reactor.listenTCP(2553, factory)
	elif process_id == '1': #port:2554
		host, port = address
		factory = PeerFactory('1', 'log')
		reactor.connectTCP(host, 2553, factory)
		reactor.listenTCP(2554, factory)
	else: #port:2555
		host, port = address
		factory = PeerFactory('2', 'log')
		reactor.connectTCP(host, 2553, factory)
		reactor.connectTCP(host, 2554, factory)

	reactor.run()