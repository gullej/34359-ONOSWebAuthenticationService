#! /usr/bin/env bash
VETH="v1"
VPEER1="v2"
VADD="10.0.0.1"

#Create veth
ip link add ${VETH} type veth peer name ${VPEER1}

#Define networking
ip link set ${VETH} up
ip addr add ${VADD}/16 dev v1
ip route add 10.0.0.0/24 via ${VADD}
