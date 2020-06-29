package uom.distributed.filesharing.Server;

import uom.distributed.filesharing.Server.model.Node;

import java.io.IOException;
import java.net.DatagramPacket;

public class NodeListener extends Thread{
    private Node node;

    public NodeListener(Node node) {
        this.node = node;
    }

    public NodeListener() {
    }

    public void run()
    {
        try {
            listen();
            Thread.sleep(2000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listen() throws IOException{
        System.out.println("LISTENING ON: " + node.getPort());

        byte[] receive = new byte[65535];

        DatagramPacket packet = null;

        while (true) {

            // Step 2 : create a DatagramPacket to receive the data.
            packet = new DatagramPacket(receive, receive.length);

            // receive the data in byte buffer.
            node.nodeReceiveSocket.receive(packet);

            // Convert it back to string
            String msg = new String(packet.getData(),
                    packet.getOffset(), packet.getLength());

            System.out.println("Received: " + msg);

            MessageProcess.messageCheck(node, msg);

            // Clear the buffer after every message.
            receive = new byte[65535];
        }
    }

}
