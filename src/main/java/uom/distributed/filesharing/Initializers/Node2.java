package uom.distributed.filesharing.Initializers;

import uom.distributed.filesharing.Server.NodeListener;
import uom.distributed.filesharing.Server.NodeServer;
import uom.distributed.filesharing.Server.model.Node;

import java.io.IOException;

public class Node2 {
    public static void main(String[] args) throws IOException {
        Node node2 = new Node("localhost",1235,"node_2");
        try {
            NodeListener nodeListener2 = new NodeListener(node2);
            nodeListener2.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        NodeServer nodeServer2 = new NodeServer(node2);
        nodeServer2.start();
    }
}
