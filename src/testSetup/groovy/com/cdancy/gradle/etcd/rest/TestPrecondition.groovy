package com.cdancy.gradle.etcd.rest

final class TestPrecondition {
    public static final List<String> ALLOWED_PING_PROTOCOLS = ['http', 'https']
    public static final boolean ETCD_URL_REACHABLE = isEtcdUrlReachable()

    private TestPrecondition() {}

    private static boolean isEtcdUrlReachable() {    
        isUrlReachable(new URL("${TestConfiguration.getEtcdUrl()}/version"))
    }

    private static boolean isUrlReachable(URL url) {
        if(!ALLOWED_PING_PROTOCOLS.contains(url.protocol)) {
            throw new IllegalArgumentException("Unsupported URL protocol '$url.protocol'")
        }

        try {
            HttpURLConnection connection = url.openConnection()
            connection.requestMethod = 'GET'
            connection.connectTimeout = 3000
            return connection.responseCode == HttpURLConnection.HTTP_OK
        }
        catch(Exception e) {
            return false
        }
    }
}
