


# test cache proxy:


## flow: 
 client   ---> cache (proxy)  --> request handler (ex: Math/Casino)  (server)
             (if request never seen) ---> server
          <-------   if seen, return result from cache

```agsl
    java casino.Casino            // server port 5555
    java echo.Casino 5555 6666    // proxy port 6666, connect to server 5555, 
    
    java echo.SimpleClient 6666   // client connect to proxy at port 6666, 
                                  // run this line many times to create more clients
```

