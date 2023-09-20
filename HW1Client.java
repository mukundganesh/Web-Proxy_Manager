import java.io.*;
import java.net.*;


public class HW1Client {
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java HW1Client <host name> <port number>");
            System.exit(1);
        }
        String answer = null;
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        int contentLength = -1;
        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            String inputus;
            while ((inputus = stdIn.readLine()) != null) {
                while (inputus.isEmpty()){System.exit(0);}
                String substr=inputus.toString();
                String filenam = substr.substring(substr.lastIndexOf("/") + 1);
                out.println(inputus);
                answer = in.readLine();
                int capacity = Integer.parseInt(answer);
                System.out.println("Content-Length\t" + capacity);
                FileWriter FWriter = new FileWriter(""+filenam);//File Name set method
                System.out.println("File:"+filenam);
                for (int i = capacity; i >0; i--) {
                    FWriter.write(in.read());
                    if (i % 100 == 0) {
                        System.out.print("#$#$#$#");//Download progress
                    }
                }
                FWriter.close();
                System.out.println("\n\nDownload Finished");
                System.out.println("\n\nPlease Check the Content file");
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}

