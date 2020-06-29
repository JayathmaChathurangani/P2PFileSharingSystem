package uom.distributed.filesharing.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Hashtable;

public class BootstrapServerConnection {

    private static BootstrapServerConnection instance;

    private static final String BOOTSTRAP_SERVER_IP = "localhost";
    private static final int BOOTSTRAP_SERVER_PORT = 55555;
    private static final String REG = "REG";
    private static final String REGOK = "REGOK";
    private static final String UNREG = "UNREG";
    private static final String UNREGOK = "UNROK";

    public static DatagramSocket bSServerConnectSocket;

    private BootstrapServerConnection() throws IOException {
        bSServerConnectSocket = new DatagramSocket();
    }

    public static BootstrapServerConnection getInstance() throws IOException {
        if (instance == null) {
            instance = new BootstrapServerConnection();
        }
        return instance;
    }

    public String registerNode(String nodeIp, int nodePort, String nodeName) throws IOException{
        final String regMsg = REG + " " + nodeIp + " " + nodePort + " " + nodeName;
        String response = sendMessage(regMsg);
        System.out.println(nodeIp + " " + nodePort + " " + nodeName + " response is:" + response);
        return response;
    }

    public void unregisterNode(String nodeIp, int nodePort, String nodeName) throws IOException{
        final String unRegMsg = UNREG + " " + nodeIp + " " + nodePort + " " + nodeName;
        String response = sendMessage(unRegMsg);
        System.out.println(response);
    }

    private String sendMessage(String message) throws IOException{
        System.out.println("MESSAGE : "+message);
        InetAddress ip = InetAddress.getByName(BOOTSTRAP_SERVER_IP);

        byte[] buf = (message.length()+" "+message).getBytes();

        DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, BOOTSTRAP_SERVER_PORT);
        bSServerConnectSocket.send(packet);

        packet = new DatagramPacket(buf, buf.length);
        bSServerConnectSocket.receive(packet);

        String received = new String(packet.getData(), 0, packet.getLength());
        return received;
    }
}
