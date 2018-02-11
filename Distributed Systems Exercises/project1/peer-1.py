#Chrysoula Terizi AM.2553 chryssa.terizi@gmail.com
#TEAM: Panagiotis Kouzouglidis AM.2276
import optparse
from twisted.internet.protocol import Protocol, ClientFactory
from twisted.internet import reactor
import time

connectionList = []
queue = {}
sorted_keys = []
clock_counter = 0
counterMessage = 0
timestamp = ''
ack_counter = [0] * 20
lastMessageCounter = 0

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

	#acks = 0
	connected = False
	wringBytes = ""

	#Pairnei ena epipleon orisma to id ths diergasias
	def __init__(self, factory, process_id):
		print "init"
		self.process_id = process_id
		self.factory = factory

	#Ginetai h syndesh twn diergasiwn
	def connectionMade(self):
		print "connectionMade"

		self.connected = True
		#To connectionList einai mia global lista opou gia kathe 
		#diergasia exei mesa ton arithmo apo tis diergasies stis 
		#opoies thelei na steilei mhnymata 
		connectionList.append(self.transport)

		if (len(connectionList) == 2):
			reactor.callLater(3, self.sendMessage)

	#einai h synarthsh mesa apo thn opoia stelnontai ta mhnymata
	def sendMessage(self):
		print "sendMessage"

		global counterMessage
		global clock_counter
		global ack_counter
		global sorted_keys

		try:
			#einai o arithmos gia to mhnyma pou stelnetai
			counterMessage += 1
			#to topiko roloi ths kathe diergasias
			clock_counter += 1
			#einai h strfagida
			timestamp = str(clock_counter) + '.' + str(self.process_id)
			
			#h kathe diergasia stelnei mhnyma kai ston eayto ths, opote tha to vazoume kateytheian mesa sthn queue 
			queue[timestamp] = 'message ' + str(counterMessage) + ' from ' + str(self.process_id) + ' with timestamp ' + timestamp
			#auxanoume ton counter gia ta acks pou stelnontai kai einai gia to mhnyma pou stelnei ston eayto tou
			ack_counter[counterMessage] = ack_counter[counterMessage] + 1
			#taxinomoune tin lista pou periexei ta kleidia(sfragides)
			sorted_keys = sorted ([float(i) for i in queue.keys()])

			#Stelnw to mhnyma sto dataReceive tha mou emfanizei ti esteila
			connectionList[0].write('message ' + str(counterMessage) + ' from ' + str(self.process_id) + ' with timestamp ' + timestamp)
			connectionList[1].write('message ' + str(counterMessage) + ' from ' + str(self.process_id) + ' with timestamp ' + timestamp)
		except Exception, ex1:
			print "Exception trying to send: ", ex1.args[0]

		if self.connected == True:
			if (counterMessage < 20):
				reactor.callLater(3, self.sendMessage)
			else:
				print "---------------------"
				print queue.keys()
				print "---------------------"
				print ack_counter
				print "---------------------"

	def sendAck(self, numberOfMessage):
		print "sendAck"

		global clock_counter
		global timestamp

		try:
			clock_counter += 1
			timestamp = str(clock_counter) + '.' + str(self.process_id)

			#grafoume to mhnuma gia to ack
			connectionList[0].write('ack ' + str(numberOfMessage) + ' from ' + str(self.process_id) + ' with timestamp ' + timestamp)
			connectionList[1].write('ack ' + str(numberOfMessage) + ' from ' + str(self.process_id) + ' with timestamp ' + timestamp)
		except Exception, e:
			print "Exception trying to send ack: ",e.args[0]

	def dataReceived(self, data):
		print "dataReceived"

		global ack_counter
		global clock_counter
		global sorted_keys
		global lastMessageCounter

		#Kanoume split gia na paroume ta merh toy mhnymatos pou steilame
		x = data.split(' ')

		if x[0] == 'message':
			x_message = 'message'
			x_counterForMessage = x[1]
			x_MessageFrom = x[3]

			#Elegxoume ama stelnoume mazi me to timestamp kai kapoio ack
			try:
				float(x[6])
				wrongAck = True
			except ValueError:
				wrongAck = False

			if wrongAck == False:
				startIndex = x[6].index('a') #emfanizei ton deikth ths prwths amfanishw tou a
				wronwgBytes = x[6][startIndex:len(x[6])]
				x[6] = x[6][0:startIndex]

			x_timestamp = x[6]
			#Kanoume upate to clock counter
			x_clockReceived = int(x[6][0])
			#sygkrisi me to clock_counter
			if x_clockReceived >= clock_counter:
				clock_counter = x_clockReceived + 1
				timestamp = str(clock_counter) + '.' + str(self.process_id)

			#Vasoume sto lexiko to mhnyma me kleidi thn sfragida
			queue[x_timestamp] = x_message + ' ' + x_counterForMessage + ' from ' + x_MessageFrom + ' with timestamp ' + x_timestamp 

			#Taximonoume tin lista me ta kleidia
			sorted_keys = sorted ([float(i) for i in queue.keys()])

			#stelnoume ack
			self.sendAck(int(x_counterForMessage))
		else:
			print 'Ack received'
			y_ack = 'ack'
			y_counterForMessage = int(x[1])
			ack_counter[y_counterForMessage - 1] = ack_counter[y_counterForMessage - 1] + 1

		#Thewroume oti ta mhnymata stelnontai me thn seira
		if(int(x[1]) == 20):
			lastMessageCounter += 1
			if(lastMessageCounter == 20):
				f = open('delivered-messages-' + self.process_id + '.txt', 'w')
				for	key in sorted_keys:
					f.write(queue[str(key)])
					f.write('\n')
				f.close()

	def connectionLost(self, reason):
		print "connectionLost"

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