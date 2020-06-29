package uom.distributed.filesharing.Server;

import uom.distributed.filesharing.Server.model.Node;

import java.io.IOException;
import java.net.InetAddress;

public class NodeServer extends Thread{
    private Node node;
    private InetAddress nodeIp;
    private static NodeServer instance = null;

    public NodeServer(Node node) {
        this.node = node;
    }

    public NodeServer() {
    }

    static NodeServer getInstance () {
        if(instance == null) {
            instance = new NodeServer();
        }
        return instance;
    }
    public void run()
    {
        try {
            startServer();
            Thread.sleep(2000L);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startServer() throws IOException {
        System.out.println("STARTING NODE SERVER..." + node.getName());

        nodeIp = InetAddress.getByName(node.getIp());
        String response = BootstrapServerConnection.getInstance().registerNode(nodeIp.getHostAddress(), node.getPort(), node.getName());

        String[] responseSplit = response.split(" ");

        int no_nodes = Integer.parseInt(responseSplit[2].trim());

        // Logic to receive and update the routing table
        if (no_nodes == 0) {
            System.out.println("First Node Registered");
            node.setJoined(true);
        } else if (no_nodes == 1) {
            System.out.println("Info of one node received");
            node.routing_table.put((String) responseSplit[4], (String) responseSplit[3]);
            Join JoinRequest = new Join(node);
            JoinRequest.SendJoinRequest();
        } else if (no_nodes == 2) {
            System.out.println("Info of two nodes received");
            node.routing_table.put((String) responseSplit[4], (String) responseSplit[3]);
            //node.routing_table.put((String) responseSplit[6], (String) responseSplit[5]);
            Join JoinRequest = new Join(node);
            JoinRequest.SendJoinRequest();
        } else if (no_nodes == 9999) {
            System.out.println("Failed, there is some error in the command");
        } else if (no_nodes == 9998) {
            System.out.println("Failed, already registered to you, unregister first");
        } else if (no_nodes == 9997) {
            System.out.println("Failed, registered to another user, try a different IP and port");
        } else if (no_nodes == 9996) {
            System.out.println("Failed, can’t register. BS full");
        } else {
            System.out.println("Bootstrap error");
        }
    }

}
