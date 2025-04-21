package echo;

public class CacheProxy extends ProxyHandler{
    protected String response(String msg) throws Exception {
        System.out.println("[CacheProxy] " + msg);
        return super.response(msg);
    }
}
