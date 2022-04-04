#!/usr/bin/python

from mininet.topo import Topo
from mininet.net import Mininet
from mininet.node import OVSSwitch
from mininet.node import CPULimitedHost
from mininet.link import TCLink
from mininet.util import dumpNodeConnections
from mininet.util import dumpNetConnections
from mininet.log import setLogLevel
from mininet.cli import CLI
from mininet.link import Intf
from mininet.node import RemoteController

class MyTopo(Topo):
    def __init__(self, **opts):
        Topo.__init__(self, **opts)

	s1 = self.addSwitch('s1', cls = OVSSwitch, protocols='OpenFlow13')
            
   	h2 = self.addHost('h2', address="10.0.0.2")
    	h3 = self.addHost('h3', address="10.0.0.3")
    	
    	self.addLink(s1, h2)
    	self.addLink(s1, h3)
        
        intf_ = Intf("v2", node=s1) # s1.attach("mytap")

class ONOSController (RemoteController):

    def __init__ (self):
        RemoteController.__init__(self,'ONOSController','127.0.0.1',6633)

def setupNet():

    topo = MyTopo()
        
    net = Mininet(topo=topo, controller=None, link=TCLink, listenPort=6634)
   
    c0 = ONOSController()
    net.addController(c0)   

    hosts = net.hosts

    net.start()
    CLI(net)
    
    net.stop()

if __name__ == '__main__':

    setLogLevel('info')   
    setupNet()

