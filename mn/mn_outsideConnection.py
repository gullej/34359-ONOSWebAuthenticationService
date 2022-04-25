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
from mininet.node import Controller, OVSKernelSwitch, RemoteController
from mininet.log import setLogLevel, info

def topology():
    "Create a network."
    net = Mininet(controller=RemoteController, switch=OVSKernelSwitch)

    info("*** Creating nodes\n")

    c1 = net.addController('c1', controller=RemoteController, ip="127.0.0.1", port=6653)
    
    s1 = net.addSwitch('s1', cls = OVSSwitch, protocols='OpenFlow13')
    h2 = net.addHost('h2', address="10.0.0.2")
    h3 = net.addHost('h3', address="10.0.0.3")

    info("*** Creating links\n")
    net.addLink(s1, h2)
    net.addLink(s1, h3)
    intf_=Intf("v2", node=s1) # s1.attach("mytap")
    
   
    info("*** Starting network\n")
    net.build()
    c1.start()
    s1.start([c1])
 
    info("*** Running CLI\n")
    CLI(net)

    info("*** Stopping network\n")
    net.stop()


if __name__ == '__main__':
    setLogLevel('info')
    topology()

