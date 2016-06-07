
# gradle-etcd-rest-plugin

Gradle plugin for interacting with Etcd's REST API.

## Setup

```
buildscript() {
 	repositories {
 		jcenter()
 	}
 	dependencies {
 		classpath group: 'com.cdancy', name: 'gradle-etcd-rest-plugin', version: '0.0.1', changing: true
 	}
 }

 apply plugin: 'gradle-etcd-rest-plugin'
 ```
## Extension

The `etcdRest` extension is provided to define the `url` and `credentials` for connecting to an Etcd instance.
Using the extension, and subsequently exposing this potentially private information, is required only if one does NOT want to use the various means of setting the aforementioned properties noted in the `Credentials` section below.

```
 etcdRest {
 	url { "http://127.0.0.1:2379" }
 	credentials { "admin:password" }
 }
```

## Url and Credentials

Because this plugin builds on top of [etcd-rest](https://github.com/cdancy/etcd-rest) library one can supply
the [url and credentials](https://github.com/cdancy/etcd-rest#credentials) in any form this library accepts. Furthermore,
[etcd-rest](https://github.com/cdancy/etcd-rest#property-based-setup) allows the `url` and `credentials`
to be optionally supplied through properties or environment variables. This gives great flexibility in the way the user
wants to define and/or hide their url or credentials assuming one does not want to use the `etcdRest` extension.

