package uom.distributed.filesharing.Server;

import uom.distributed.filesharing.Server.model.Node;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.Hashtable;

public class MessageProcess {
    public static void messageCheck(Node node, String output) throws IOException {
        byte[] sendbuffer = new byte[10024];

        // To remove last character
        output = output.replace("\n", "").trim();

        String[] response = output.split(" ");

        switch (response[1]) {
            case "JOIN": {
                String join_ip = response[2].trim();
                String join_port = response[3].trim();

                InetAddress source_ip = InetAddress.getByName(join_ip);

                // Updating routing table
                node.routing_table.put(join_port, join_ip);

                StringBuilder packet = new StringBuilder();

                // Making the packet ready for transmission to server
                packet.append("0014").append(" ");
                packet.append("JOINOK").append(" ");
                packet.append("0");

                String message = packet.toString();
                System.out.println("Send Join Okay msg \n" + message);

                // Converting message to be sent to server
                sendbuffer = message.getBytes();

                // Creating send packet
                DatagramPacket packet_to_be_sent = new DatagramPacket(sendbuffer,
                        sendbuffer.length, source_ip, Integer.parseInt(join_port));

                node.nodeSendSocket.send(packet_to_be_sent);
                break;
            }

            case "JOINOK": {
                // Printing the JoinOK message
                System.out.println("Join has been Successful \n" + output);
                node.setJoined(true);
                break;

            }
            default:
                break;

        }

    }
}
