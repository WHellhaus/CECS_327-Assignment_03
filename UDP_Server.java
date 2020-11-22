import java.net.*;
import java.io.*;

public class UDP_Server {
    public static void main(String args[]){
        // socket used to receive messages from client and send responses
        DatagramSocket aSocket = null;
        try {
            // sets up socket locally on port 6789
            aSocket = new DatagramSocket(6789);
            // buffer for getting and sending message
            byte[] buffer = new byte[1000];
            // continually runs to receive new messages
            while(true) {
                // packet for receiving requests
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                // wait to receive request
                aSocket.receive(request);
                // response message to client with same message received
                DatagramPacket reply = new DatagramPacket(request.getData(),
                    request.getLength(), request.getAddress(), request.getPort());
                // sends response message
                aSocket.send(reply);
            }
        }
        // exception catches
        catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } 
        catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
        // close socket when done
        finally {
            if(aSocket != null) aSocket.close();
        }

    }
}