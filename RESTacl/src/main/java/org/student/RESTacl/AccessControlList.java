package org.student.RESTacl;

import org.onosproject.net.flow.TrafficSelector;
import org.onosproject.rest.AbstractWebResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class AccessControlList {
    private static AccessControlList instance = null;

    private ArrayList<TrafficSelector> aclRules  =  new ArrayList<>();

    /*
    private ConcurrentHashMap<String, Boolean> clients = new ConcurrentHashMap<String, Boolean>() {{
       put("00:00:00:00:00:01", true); //Web Portal
       put("00:00:00:00:00:03", true); //Random Web Service guest is trying to access
       put("00:00:00:00:00:04", true); //NAT Connection

    }};

     */

    public void addClient(TrafficSelector rule) {
        aclRules.add(rule);
    }

    public ArrayList<TrafficSelector> getAclRules() {
        return aclRules;
    }
/*
    public void authenticateClient(String id) {
        addClient(id);
        clients.put(id, true);
    }

    public boolean isAuthenticated(String id) {
        return clients.getOrDefault(id, false);
    }

    public String DEBUGgetAuthenticated() {
        return clients.toString();
    }

    private void AuthenticationHandler() {

    }

 */

    public static AccessControlList getInstance() {
        if(instance == null) {
            instance = new AccessControlList();
        }
        return instance;
    }
}