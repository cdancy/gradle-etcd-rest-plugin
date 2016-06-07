package com.cdancy.gradle.etcd.rest

final class TestConfiguration {
    private static final String ETCD_URL_SYS_PROP = 'etcdUrl'

    private TestConfiguration() {}

    static String getEtcdUrl() {
        System.properties[ETCD_URL_SYS_PROP] ?: 'http://127.0.0.1:2379'
    }
}
