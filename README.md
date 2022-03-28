# 34359-ONOSWebAuthenticationService
Authentication Service. Before any host can send (or receive) network traffic, each host must first authenticate with an authentication service, via a web interface. The web interface is associatied to a web application managing the authentication operation, as well as communicating to ONOS via its REST API. Your project should follow the following workflow:

1. Initially all hosts are disabled to transmit / receive, by the network.
2. In order to be authenticated, a host user should visit a web interface and enter his credentials. The web interface should submit the info to a web server running a web application, to verity the credentials:
  a. If the credentials are invalid the web server should return an ERROR message.
  b. If the credentials are valid, the web server should ISSUE a REST call to the SDN Controller containing the ID of the host.
3. Upon reception of a REST call from the web application, the SDN controller should then enable connectivity in the network for the corresponding host.
4. The web application should reply to the user, via its web interface of the result of the operation.
