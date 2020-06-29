package uom.distributed.filesharing.Server.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.net.DatagramSocket;
import java.util.Hashtable;

@XStreamAlias("Node")
public class Node {
    private String ip;
    private int port;
    private String name;
    private Boolean joined;

    // Declaring all the static variables here in this section
    public static DatagramSocket nodeSendSocket = null;
    public static DatagramSocket nodeReceiveSocket = null;

    // Creating a Hash table
    public static Hashtable<String, String> routing_table = new Hashtable<String, String>();

    public Node(String ip, int port, String name) {
        this.ip = ip;
        this.port = port;
        this.name = name;
        this.joined = false;
        try {
            this.nodeSendSocket = new DatagramSocket();
            this.nodeReceiveSocket = new DatagramSocket(this.port);
        }
        catch (Exception e) {

            System.out.println("Problem opening a socket");
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Boolean getJoined() {
        return joined;
    }

    public void setJoined(Boolean joined) {
        this.joined = joined;
    }

    public int getPort() {
        return port;
    }

    public String getStringPort() {
        return Integer.toString(port);
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
