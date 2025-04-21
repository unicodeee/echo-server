package echo;

import java.net.Socket;

public class RequestHandler extends echo.Correspondent implements Runnable {
    protected boolean active; // response can set to false to terminate thread
    public RequestHandler(Socket s) {
        super(s);
        active = true;
    }
    public RequestHandler() {
        super();
        active = true;
    }
    // override in a subclass:
    protected String response(String msg) throws Exception {
        return "echo: " + msg;
    }
    // any housekeeping can be done by an override of this:
    protected void shutDown() {
        if (Server.DEBUG) System.out.println("handler shutting down");
        active = false;
    }
    public void run() {
        while(active) {
            try {
                String request = receive();
                // receive request
                if(request.equals("quit")) {
                    shutDown();
                    break;
                }
                else if (request.equals("who")) {
                    send(toString());
                }

                send(response(request));
                // send response
                // sleep
            } catch(Exception e) {
                send(e.getMessage() + "... ending session");
                break;
            }
        }
        // close
    }
}