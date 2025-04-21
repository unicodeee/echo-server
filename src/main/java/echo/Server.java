package echo;

import java.lang.reflect.Method;
import java.net.*;

public class Server {

    protected ServerSocket mySocket;
    protected int myPort;
    public static boolean DEBUG = true;
    protected Class<?> handlerType;

    public Server(int port, String handlerTypeName) {
        try {
            myPort = port;
            mySocket = new ServerSocket(myPort);
            this.handlerType = Class.forName(handlerTypeName);
        } catch(Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } // catch
    }


    public void listen() {
        while(true) {
            // accept a connection
            try {
            // make handler
                RequestHandler handler = makeHandler(mySocket.accept());
                if (DEBUG) {
                    System.out.println("Starting handler: " + handler.toString());
                }
            // start handler in its own thread
                Thread thread = new Thread(handler);
                thread.start();
            }
            catch(Exception e) {
                System.err.println(e.getMessage());
            }
        } // while
    }

    public RequestHandler makeHandler(Socket s)  {
        try{
            RequestHandler handler = (RequestHandler) handlerType.getDeclaredConstructor().newInstance();
            handler.setSocket(s);
            return handler;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }



    public static void main(String[] args) {
        int port = 5555;
        String service = "echo.RequestHandler";
//        String service = "casino.CasinoHandler";
        if (1 <= args.length) {
            service = args[0];
        }
        if (2 <= args.length) {
            port = Integer.parseInt(args[1]);
        }
        Server server = new Server(port, service);
        server.listen();
    }
}
