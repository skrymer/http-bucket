package com.muspelheim.httpbucket.mavenplugin;

import com.muspelheim.httpbucket.server.HttpBucketServer;

public class ServerContext {
  public static final ThreadLocal<HttpBucketServer> userThreadLocal = new ThreadLocal<HttpBucketServer>();
   
  public static void set(HttpBucketServer server) {
    userThreadLocal.set(server);
  }
  public static void unset() {
    userThreadLocal.remove();
  }
 
  public static HttpBucketServer get() {
    return userThreadLocal.get();
  }
}
