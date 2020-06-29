package uom.distributed.filesharing.Server;

import uom.distributed.filesharing.Server.model.Node;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Enumeration;

public class Join{
    private Node node;

    public Join(Node node) {
        this.node = node;
    }

    public Join() {
    }

    public void SendJoinRequest()
            throws IOException {

        byte[] sendbuffer_join = new byte[1024];

        // Prepare the join message to send
        StringBuilder join_message = new StringBuilder();

        int length = 12
                + node.getStringPort().length()
                + InetAddress.getLocalHost().getHostAddress().toString()
                .length();

        join_message.append("00" + length + " " + "JOIN" + " "
                + InetAddress.getLocalHost().getHostAddress() + " "
                + node.getStringPort());

        // String conversion
        String message = join_message.toString();

        // Enumeration
        Enumeration<String> keys = node.routing_table.keys();

        while (keys.hasMoreElements()) {

            String entry = keys.nextElement();

            InetAddress ip = InetAddress.getByName(node.routing_table.get(entry)
                    .toString().trim());

            int port = Integer.parseInt(entry.toString().trim());

            System.out.println("Sending the Join Message to: " + ip);
            System.out.println(message + "\n");

            sendbuffer_join = message.getBytes();

            // Sending the Join Message
            DatagramPacket join_packet = new DatagramPacket(sendbuffer_join,
                    sendbuffer_join.length, ip, port);
            node.nodeSendSocket.send(join_packet);

            System.out.println("Waiting for JOINOK:");
        }

    }
}