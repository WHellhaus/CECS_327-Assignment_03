import java.net.*;
import java.io.*;
import java.util.*;
// packages from IPAddress.jar
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;
import inet.ipaddr.AddressStringException;

public class UDP_Client{
    public static void main(String args[]){
        DatagramSocket aSocket = null;// used for sending and receiving messages to server
        Scanner scan = new Scanner(System.in);
        boolean cont = true;// used to check if user wants to continue sending message

        while (cont) {
            try {
                boolean isValid = false; 

                String address = null;
                // loop until valid address is input
                while(!isValid) {
                    //Prompting user to enter IP address.
                    System.out.print("What ip address would you like to send a message to: ");
                    //System takes in next line from user.
                    address = scan.nextLine();
                    try {
                        // if a new IPAdressString object can be created without an exception then it is valid
                        IPAddress addressCheck = new IPAddressString(address).toAddress();
                        isValid = true;
                    } catch(AddressStringException e) {
                        System.out.println("Invalid ip address inputted");
                        System.out.println(e.getMessage());
                    }
                }
                
                isValid = false;
                int port = 1;
                // loop until valid port is input
                while(!isValid) {
                    //Prompting user to enter port number.
                    System.out.print("What port number would you like to use: ");
                    //System takes in next line from user.
                    port = Integer.parseInt(scan.nextLine());
                    // check if port number is valid
                    if(1 <= port && port <= 65535) {
                        isValid = true;
                    } else {
                        System.out.println("Invalid port number inputted. Port number must be between 1 and 65535");
                    }
                }
                
		
        	    //Prompting user to enter message to IP address.
                System.out.print("What message would you like to send: ");
                //System takes in next line from user.
		        String message = scan.nextLine();

                aSocket = new DatagramSocket();
                // setting a timeout of 10 seconds
                aSocket.setSoTimeout(10*10000);

                // need message as a byte array to send it
                byte[] m = message.getBytes();
                // getting address based on host name entered by user
                InetAddress aHost = InetAddress.getByName(address);
                // builds request with message, ip address, and port number
                DatagramPacket request = new DatagramPacket(m, m.length, aHost, port);
                // actually sends request
                aSocket.send(request);

                // new array for response message
                byte[] buffer = new byte[1000];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                // receives reply from server on same socket
                aSocket.receive(reply);
                // prints out the message sent back from the server
                System.out.println("Reply: " + new String(reply.getData()));

                // asks if the user would like to send another message
                System.out.print("Would you like to continue sending messages (y/n): ");
                cont = ('y' == scan.nextLine().toLowerCase().charAt(0));
            }
            // catches for socket and timeout exceptions
            catch (SocketException e) {
                System.out.println("Socket Exception: " + e.getMessage());
            }
            catch (SocketTimeoutException e) {
                System.out.println("Socket Timeout Exception: " + e.getMessage());
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
}