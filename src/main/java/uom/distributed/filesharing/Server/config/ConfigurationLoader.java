package uom.distributed.filesharing.Server.config;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import uom.distributed.filesharing.Server.model.Node;
import uom.distributed.filesharing.Server.model.NodeConfiguration;

import java.io.*;
import java.util.List;

public class ConfigurationLoader {
    private static ConfigurationLoader instance;
    private XStream xstream;
    InputStream resourceAsStream;

    private ConfigurationLoader() {
        xstream = new XStream();
        xstream.processAnnotations(NodeConfiguration.class);
        xstream.processAnnotations(Node.class);
        xstream.addPermission(NoTypePermission.NONE);
        xstream.allowTypesByWildcard(new String[] {
                "p2p.filesharing.model.**"
        });

        resourceAsStream = getClass().getResourceAsStream(("config.xml"));
    }

    public static ConfigurationLoader getInstance() {
        if (instance == null) {
            instance = new ConfigurationLoader();
        }
        return instance;
    }

    public List<Node> getNodeConfigurations() throws IOException {
        byte[] buffer = new byte[resourceAsStream.available()];
        resourceAsStream.read(buffer);
        File targetFile = new File("config.xml");
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);
        NodeConfiguration nodeConfiguration = (NodeConfiguration) xstream.fromXML(targetFile);
        return nodeConfiguration != null ? nodeConfiguration.getNodes() : null;
    }
}
