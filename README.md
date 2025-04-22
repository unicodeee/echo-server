


# test cache proxy:


## flow: 
 client   ---> cache (proxy)  --> request handler (ex: Math/Casino)  (server)
             (if request never seen) ---> server
          <-------   if seen, return result from cache

```agsl
    java echo.Server casino.CasinoHandler            // server port 5555
    java echo.ProxyServer echo.CacheHandler 5555 6666    // proxy port 6666, connect to server 5555, 
    
    java echo.SimpleClient 6666   // client connect to proxy at port 6666, 
                                  // run this line many times to create more clients
```
echo/ProxyServer.java
