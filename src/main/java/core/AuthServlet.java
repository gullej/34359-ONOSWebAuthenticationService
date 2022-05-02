package core;

import pojo.*;



import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@WebServlet(name = "AuthServlet")
public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String SW1="of:0000000000000001";

        String h = request.getParameter("H");
        String p = request.getParameter("P");

        System.out.println(h);
        System.out.println(p);

        if (p.equals("admin")) {
            StringBuilder target_switch_for_URL = new StringBuilder(SW1);
            target_switch_for_URL.delete(2,3);        //editing the string for conformance with ONOS REST API formatting for the URL
            target_switch_for_URL.insert(2, "%3A");   //editing the string for conformance with ONOS REST API formatting for the URL

            Client client = ClientBuilder.newClient().register(new core.Authenticator("onos", "rocks"));

            WebTarget target = client.target("http://localhost:8181/onos/v1/flowobjectives/" + target_switch_for_URL.toString() + "/forward?appId=77777");

            List<Criterium> mycriteria = new ArrayList<>();
            Criterium c1 = new Criterium("ETH_TYPE", null, "0x800");
            Criterium c2 = new Criterium("IPV4_SRC", h+"/32", null);
            mycriteria.add(c1);
            mycriteria.add(c2);
            Selector mysel = new Selector(mycriteria);
            Treatment mytreat = new Treatment();

            FlowObjective myfo = new FlowObjective("PERMIT", 1, 0, true, SW1, "ADD", mysel, mytreat);
            Response restResponse = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(myfo));


            RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/approved.jsp");
            RequetsDispatcherObj.forward(request, response);
        } else {
            RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/denied.jsp");
            RequetsDispatcherObj.forward(request, response);
        }

    }
}
