package uom.distributed.filesharing.Initializers;

import uom.distributed.filesharing.Server.NodeListener;
import uom.distributed.filesharing.Server.NodeServer;
import uom.distributed.filesharing.Server.model.Node;

import java.io.IOException;

public class Node1 {

    public static void main(String[] args) throws IOException {
        Node node1 = new Node("localhost",1234,"node_1");

        try {
            NodeListener nodeListener1 = new NodeListener(node1);
            nodeListener1.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        NodeServer nodeServer1 = new NodeServer(node1);
        nodeServer1.start();

    }
}
