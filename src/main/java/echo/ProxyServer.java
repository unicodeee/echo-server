package echo;

import java.net.Socket;

public class ProxyServer extends Server {

    String peerHost;
    int peerPort;

    public ProxyServer(int myPort, String service, int peerPort, String peerHost) {
        super(myPort,service);
        this.peerHost = peerHost;
        this.peerPort = peerPort;
    }

    public RequestHandler makeHandler(Socket s) {
        // make a proxy handler and call initPeer
        try {
            // Create an instance of the handler type
            ProxyHandler handler = (ProxyHandler) handlerType.getDeclaredConstructor().newInstance();
            // Set handler's socket to s
            handler.setSocket(s);
            // Initialize the peer connection
            handler.initPeer(peerHost, peerPort);

            if (DEBUG) System.out.println("Created proxy handler with peer at " + peerHost + ":" + peerPort);

            // Return handler
            return handler;
        } catch (Exception e) {
            System.err.println("Error creating proxy handler: " + e.getMessage());
            try {
                s.close();
            } catch (java.io.IOException ioe) {
                // Ignore
            }
            return null;
        }
    }



    public static void main(String[] args) {
        int port = 5555;
        int peerPort = 6666;
        String peerHost = "localhost";
        String service = "echo.ProxyHandler";

        if (1 <= args.length) {
            service = args[0];
        }
        if (2 <= args.length) {
            peerPort = Integer.parseInt(args[1]);
        }
        if (3 <= args.length) {
            port = Integer.parseInt(args[2]);
        }
        if (4 <= args.length) {
            peerHost = args[3];
        }
        Server server = new ProxyServer(port, service, peerPort, peerHost);
        server.listen();
    }
}

