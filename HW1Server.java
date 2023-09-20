//importing neccessary java libraries
import java.net.*;
import java.io.*;

//proxy server downloads file from the web server with the name proxy-filename
public class HW1Server {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java HW1Server <port number>");
            System.exit(1);
        }
        //portnumber is taken from arg passed by the client
        int portNumber = Integer.parseInt(args[0]);
        //creating a server socket
        try{
            ServerSocket serverSocket =
                    new ServerSocket(portNumber);

            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("New Client Connected:\t"+clientSocket.getInetAddress().getHostAddress());    //getRemoteSocketAddress returns the ip address od the client socket
                ClientWorker w=new ClientWorker(clientSocket);
                Thread t=new Thread(w);
                t.start();
            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}

class ClientWorker implements Runnable {
    private Socket client;

    //Constructor
    ClientWorker(Socket client) {
        this.client = client;

    }
    //the

    public static void downloadproxyfile(String yourl) {
        String[] urlElements = yourl.split("/");
        String path = yourl.replace(urlElements[0], "");
        int port = 80;
        try {
            InetAddress Internetaddress = InetAddress.getByName(urlElements[0]);
            Socket socket = new Socket(Internetaddress, port);
            PrintStream printwriter = new PrintStream(socket.getOutputStream());
            BufferedInputStream bufferedinputstream = new BufferedInputStream(socket.getInputStream());
            FileOutputStream fisoooo = new FileOutputStream("proxy-" + urlElements[urlElements.length - 1]);
            printwriter.println("GET " + path + " HTTP/1.0");
            printwriter.println("Host: " + urlElements[0]);
            printwriter.println();
            byte[] buffer = new byte[1024];
            int count000 = 0;
            while ((count000 = bufferedinputstream.read(buffer, 0, 1024)) != -1) {
                fisoooo.write(buffer, 0, count000);
            }
            fisoooo.close();
            bufferedinputstream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getwebcontent(String normalurl, PrintWriter outpo) {
        int lengthContent = -1, portNumber = -1;
        Socket socks = null;
        URL targetURL= null;
        PrintWriter printWrite = null;
        BufferedReader bufferread = null;
        System.out.println("Web Server: " + normalurl);
        try {
            if (normalurl.startsWith("http://")) {
                targetURL = new URL(normalurl);portNumber = 80;
            } else if (normalurl.startsWith("https://"))
            {
                targetURL = new URL(normalurl);portNumber = 443;
            } else
            {
                targetURL = new URL("http://" + normalurl);
                portNumber = 80;
            }

            String pathhost = targetURL.getHost();
            String pathsub = targetURL.getPath();
            socks = new Socket(InetAddress.getByName(pathhost), portNumber);
            printWrite = new PrintWriter(socks.getOutputStream());
            printWrite.print("GET " + pathsub + " HTTP/1.1\r\n");
            printWrite.print("Host: " + pathhost + "\r\n");
            printWrite.print(" HW1Server-cs646\r\n");
            printWrite.print("Accept: */*\r\n");
            printWrite.print("Connection: close\r\n\r\n");
            printWrite.flush();
            bufferread = new BufferedReader(new InputStreamReader (socks.getInputStream()));
            String trauma;
            String test= bufferread.readLine();
            System.out.println(test);
            while((trauma = bufferread.readLine()) != null)
            {
                if(trauma.startsWith("Content-Length:")) {
                    String cl = trauma.replaceFirst("Content-Length: ", "");
                    lengthContent = Integer.parseInt(cl);
                    outpo.println(lengthContent);
                    System.out.println(" Content Length:" + lengthContent);
                }
                else if (trauma.isEmpty()) {
                    break;
                }
            }
            for (int i = lengthContent; i>0; i--) 
            {
                outpo.write(bufferread.read());
            }
            bufferread.close();
            socks.close();
        } 
        catch (Exception e) 
        {
            System.out.println("Exception:" + e.getMessage());
        }
    }

    public void run() {
        String strLine;
        BufferedReader input = null;
        PrintWriter output = null;
        try
        {
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            Boolean val=true;
            output = new PrintWriter(client.getOutputStream(), val);
        } 
        catch (IOException e) 
        {
            System.out.println("input or output failed");
            System.exit(-1);
        }
        while(!false){

            try{
                strLine = input.readLine();
                if (strLine.isEmpty())
                {
                    client.close();
                    break;
                }
                String[] argumentin = strLine.split(" ");
                if (argumentin[0].equals("GET"))
                {
                    getwebcontent(argumentin[1], output);
                    downloadproxyfile(argumentin[1]);
                    output.flush();
                }
            }
            catch (IOException f)
            {
                f.printStackTrace();
                System.out.println("Message:" + f.getMessage() + "\nRead failed");
                System.exit(-1);
            }
        }
    }
}

