/*
 * Copyright 2021-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.student.RESTacl;

import com.google.common.collect.ImmutableSet;
import org.onlab.packet.*;
import org.onosproject.cfg.ComponentConfigService;
import org.onosproject.core.ApplicationId;
import org.onosproject.core.CoreService;
import org.onosproject.net.DeviceId;
import org.onosproject.net.Host;
import org.onosproject.net.HostId;
import org.onosproject.net.PortNumber;
import org.onosproject.net.flow.*;
import org.onosproject.net.flowobjective.DefaultForwardingObjective;
import org.onosproject.net.flowobjective.FlowObjectiveService;
import org.onosproject.net.flowobjective.ForwardingObjective;
import org.onosproject.net.host.HostService;
import org.onosproject.net.packet.*;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.onlab.util.Tools.get;

/**
 * Skeletal ONOS application component.
 */
@Component(immediate = true)
public class AppComponent {

    private final Logger log = LoggerFactory.getLogger(getClass());

    ConcurrentHashMap<DeviceId, ConcurrentHashMap<MacAddress,PortNumber>> switchTable = new ConcurrentHashMap<>();

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected PacketService packetService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected FlowRuleService flowRuleService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected FlowObjectiveService flowObjectiveService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected CoreService coreService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected HostService hostService;

    private ReactivePacketProcessor processor = new ReactivePacketProcessor();
    private ApplicationId appId;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected ComponentConfigService cfgService;

    private AccessControlList acl = AccessControlList.getInstance();

    @Activate
    protected void activate() {
        appId = coreService.registerApplication("org.student.acl");
        packetService.addProcessor(processor, PacketProcessor.director(1));
        //Specify a priority of 1 in our packet processor, so we handle the packets before the Forwarding app.
        defineAclRules();
        log.info("Started", appId.id());
    }

    @Deactivate
    protected void deactivate() {
        flowRuleService.removeFlowRulesById(appId);
        packetService.removeProcessor(processor);
        processor = null;
        log.info("Stopped");
    }

    public void defineAclRules(){
        TrafficSelector.Builder selectorBuilder = DefaultTrafficSelector.builder();
        selectorBuilder.matchEthType(Ethernet.TYPE_IPV4);
        selectorBuilder.matchIPDst(IpPrefix.valueOf(IpAddress.valueOf("10.0.2.15"),IpPrefix.MAX_INET_MASK_LENGTH));
        acl.addClient(selectorBuilder.build());
        TrafficSelector.Builder selectorBuilder2 = DefaultTrafficSelector.builder();
        selectorBuilder2.matchEthType(Ethernet.TYPE_IPV4);
        selectorBuilder2.matchIPSrc(IpPrefix.valueOf(IpAddress.valueOf("10.0.2.15"),IpPrefix.MAX_INET_MASK_LENGTH));
        acl.addClient(selectorBuilder2.build());
    }

    private class ReactivePacketProcessor implements PacketProcessor {

        @Override
        public void process(PacketContext context) {
            InboundPacket pkt = context.inPacket();
            Ethernet ethPkt = pkt.parsed();

            //Discard if  packet is null.
            if (ethPkt == null) {
                return;
            }

            IPv4 ipv4Packet;
            //We only care for IPv4 packets, discard the rest.
            switch (EthType.EtherType.lookup(ethPkt.getEtherType())) {
                case IPV4:
                    ipv4Packet = (IPv4) ethPkt.getPayload();
                    break;
                default:
                    return;
            }
            
            // Generate a traffic selector based on the packet that arrived.
            TrafficSelector.Builder dstSelector = DefaultTrafficSelector.builder();
            dstSelector.matchEthType(Ethernet.TYPE_IPV4);
            dstSelector.matchIPDst(IpPrefix.valueOf(IpAddress.valueOf(ipv4Packet.getDestinationAddress()),IpPrefix.MAX_INET_MASK_LENGTH));
            
            // Generate another traffic selector that matches SRC instead of DST.
            TrafficSelector.Builder srcSelector = DefaultTrafficSelector.builder();
            srcSelector.matchEthType(Ethernet.TYPE_IPV4);
            srcSelector.matchIPSrc(IpPrefix.valueOf(IpAddress.valueOf(ipv4Packet.getSourceAddress()),IpPrefix.MAX_INET_MASK_LENGTH));

            // Fetch the current rules
            ArrayList<TrafficSelector> aclRules = acl.getAclRules();

            // If the current packet's selector matches any of the ACL rules, DROP the packet and its flow.
            Boolean block = true;
            for (TrafficSelector selector:aclRules){
                if (selector.equals(dstSelector.build()) || selector.equals((srcSelector.build()))){
                    block = false;
                    break;
                }
            }

            if (block) {
                log.info("Flow should be dropped");
                log.info(dstSelector.build().toString());
                log.info(srcDelector.build().toString());
                dropFlow(packetSelector.build(),context);
                context.block();    //Since we already handled the packet, BLOCK any access to it by other ONOS apps (e.g. the Forwarding app)
                return;
            }
        }


    public void dropFlow(TrafficSelector selector,PacketContext context){
        TrafficTreatment treatment = DefaultTrafficTreatment.builder().drop().build();
        ForwardingObjective forwardingObjective = DefaultForwardingObjective.builder()
                .withSelector(selector)
                .withTreatment(treatment)
                .withPriority(1000)
                .withFlag(ForwardingObjective.Flag.VERSATILE)
                .fromApp(appId)
                .makeTemporary(5)
                .add();
        flowObjectiveService.forward(context.inPacket().receivedFrom().deviceId(), forwardingObjective);
        return;
    }
}

}
