package uom.distributed.filesharing.Initializers;

import uom.distributed.filesharing.Server.NodeListener;
import uom.distributed.filesharing.Server.NodeServer;
import uom.distributed.filesharing.Server.model.Node;

import java.io.IOException;

public class Node3 {
    public static void main(String[] args) throws IOException {

        Node node3 = new Node("localhost",1236,"node_3");

        try {
            NodeListener nodeListener3 = new NodeListener(node3);
            nodeListener3.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        NodeServer nodeServer3 = new NodeServer(node3);
        nodeServer3.start();

    }
}
