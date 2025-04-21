package echo;

import java.util.HashMap;
import java.util.Map;

public class CacheHandler extends ProxyHandler {
    private static SafeTable safeTable;
    public CacheHandler() {
        safeTable = SafeTable.getInstance();
    }

    @Override
    protected String response(String msg) throws Exception {
        if (!safeTable.cache.isEmpty() && safeTable.cache.containsKey(msg)) {
            return safeTable.cache.get(msg) + " (From cache)";
        }

        peer.send(msg);
        String response = peer.receive();
        SafeTable.addCache(msg,  response);
        return response;
    }
}


class SafeTable {
    private static SafeTable instance;
    Map<String, String> cache;
    private SafeTable() {}
    public static SafeTable getInstance() {
        if (instance == null) {
            instance = new SafeTable();
            instance.cache = new HashMap<>();
        }
        return instance;
    }

    public static void addCache(String key, String value) {
        if (instance.cache == null) {
            instance.cache = new HashMap<>();
        }
        instance.cache.putIfAbsent(key, value);
        System.out.println("Added cache key: " + key + " value: " + value);
    }


}
