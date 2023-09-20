# WebProxyManager
** Project Description: HTTP Proxy Server with File Download**

This project consists of two Java programs: `HW1Client` and `HW1Server`, which together create a basic HTTP proxy server capable of downloading web content to a local file. Here's a summary of each program's functionality:

**1. HW1Client:**
- The `HW1Client` program acts as the client-side component of the project.
- It expects two command-line arguments: the host name and port number of the proxy server it should connect to.
- Upon execution, the client establishes a socket connection to the specified proxy server.
- It continuously reads user input (assumed to be URLs) from the console.
- For each URL entered, it sends an HTTP request to the proxy server, requesting the content of the URL.
- The client also handles the downloading of the web content to a local file, displaying download progress.
- The program runs until the user enters an empty line, at which point it exits.

**2. HW1Server:**
- The `HW1Server` program acts as the server-side component of the project.
- It expects one command-line argument: the port number on which the server should listen for incoming client connections.
- The server creates a `ServerSocket` and enters an infinite loop to accept client connections.
- For each client connection, it spawns a separate `ClientWorker` thread to handle the client's request.
- The `ClientWorker` class performs the following tasks:
  - Handles incoming HTTP GET requests from the client.
  - Makes HTTP GET requests to web servers to retrieve web content.
  - Sends the retrieved content back to the client.
  - Downloads the same content to the proxy server with a "proxy-" prefix.
- The server can handle multiple client connections concurrently.

**Project Overview:**
- When a client connects to the proxy server and submits a URL, the proxy server fetches the content of the URL from the web server specified in the URL.
- The proxy server sends the content length to the client and then streams the content back to the client.
- Simultaneously, the proxy server downloads the content to its local file system with a "proxy-" prefix.
- Both client and server use socket communication for data exchange.

**Note:** While this project provides a basic illustration of an HTTP proxy server, it lacks comprehensive error handling and robustness required for production use. Additional enhancements and error management would be necessary for real-world applications.
