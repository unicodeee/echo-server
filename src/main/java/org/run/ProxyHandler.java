package org.run;

import java.net.Socket;

public class ProxyHandler extends RequestHandler {

    protected echo.Correspondent peer;

    public ProxyHandler(Socket s) {
        super(s);
    }

    public ProxyHandler() {
        super();
    }

    public void initPeer(String host, int port) {
        peer = new Correspondent();
        peer.requestConnection(host, port);
    }

    protected String response(String msg) throws Exception {
        // forward msg to peer
        // resurn peer's response
    }
}
