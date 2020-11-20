import java.net.*;
import java.io.*;
import java.util.*;

public class UDP_Client{
    public static void main(String args[]){
        // args give message contents and server hostname
        DatagramSocket aSocket = null;
        Scanner scan = new Scanner(System.in);
        boolean cont = true;

        while (cont) {
            try {
                System.out.print("What ip address would you like to send a message to: ");
                String address = scan.nextLine();
        
                System.out.print("What port number would you like to use: ");
                int port = Integer.parseInt(scan.nextLine());
        
                System.out.print("What message would you like to send: ");
                String message = scan.nextLine();

                aSocket = new DatagramSocket();
                aSocket.setSoTimeout(10*1000);

                byte[] m = message.getBytes();
                InetAddress aHost = InetAddress.getByName(address);
                DatagramPacket request = new DatagramPacket(m, m.length, aHost, port);
                aSocket.send(request);

                byte[] buffer = new byte[1000];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(reply);
                System.out.println("Reply: " + new String(reply.getData()));

                System.out.print("Would you like to continue sending messages (y/n): ");
                cont = ('y' == scan.nextLine().toLowerCase().charAt(0));
            }
            catch (SocketException e) {
                System.out.println("Socket Exception: " + e.getMessage());
            }
            catch (SocketTimeoutException e) {
                System.out.println("Socket Timeout Exception: " + e.getMessage());
            }
            catch (IOException e) {
                System.out.println("IO: " + e.getMessage());
            }
            finally {
                if(aSocket != null) aSocket.close();
            }
        }

    }
}