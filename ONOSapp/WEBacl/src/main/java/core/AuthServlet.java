package core;



import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Null;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static java.lang.System.out;

@WebServlet(name = "AuthServlet")
public class AuthServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = null;
        Boolean postOK = false;

        if (servletContext == null) {
            servletContext = getServletContext();
            out.println("pushRule: servletContext obtained OK");
        }

        String h = request.getParameter("H");
        String p = request.getParameter("P");

        if (p == null) {

        } else {
            if (p.equals("admin")) {

                System.out.println(h + " authenticated with pw " + p);

                Client client = ClientBuilder.newClient().register(new Authenticator("karaf", "karaf"));

                WebTarget target = client.target("http://localhost:8181/onos/RESTacl/authenticateClient/");

                String string = "{\"IPV4_SRC\":\"" + h + "\"}";

                Response restResponse = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(string));
                System.out.println("pushRule: POST executed");

                int status = restResponse.getStatus();
                if (status > 199 && status < 300) {
                    System.out.println("pushRule: POST OK-----");
                    postOK = true;
                }
                System.out.println(status);

                RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/approved.jsp");
                RequetsDispatcherObj.forward(request, response);
            } else {
                RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/denied.jsp");
                RequetsDispatcherObj.forward(request, response);
            }
        }
    }
}
