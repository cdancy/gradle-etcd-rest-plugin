
[![Build Status](https://travis-ci.org/cdancy/gradle-etcd-rest-plugin.svg?branch=master)](https://travis-ci.org/cdancy/gradle-etcd-rest-plugin)
# gradle-etcd-rest-plugin
![alt tag](https://github.com/cdancy/etcd/blob/master/logos/etcd-horizontal-color.png)

Gradle plugin for interacting with Etcd's REST API.

## Setup

```
buildscript() {
 	repositories {
 		jcenter()
 	}
 	dependencies {
 		classpath group: 'com.cdancy', name: 'gradle-etcd-rest-plugin', version: '0.0.2', changing: true
 	}
 }

 apply plugin: 'gradle-etcd-rest-plugin'
 ```
 
## Documentation

groovydocs can be found via [github pages here](http://cdancy.github.io/gradle-etcd-rest-plugin/docs/groovydoc/)

## Tasks

| Name | Description |
| --- | --- |
| [Version](https://github.com/cdancy/gradle-etcd-rest-plugin/blob/master/src/main/groovy/com/cdancy/gradle/etcd/rest/tasks/miscellaneous/Version.groovy) | Get the version of etcd |
| [Health](https://github.com/cdancy/gradle-etcd-rest-plugin/blob/master/src/main/groovy/com/cdancy/gradle/etcd/rest/tasks/miscellaneous/Health.groovy) | Get the health of the cluster |
| [Metrics](https://github.com/cdancy/gradle-etcd-rest-plugin/blob/master/src/main/groovy/com/cdancy/gradle/etcd/rest/tasks/miscellaneous/Metrics.groovy) | Get the metrics of the cluster |
| [Leader](https://github.com/cdancy/gradle-etcd-rest-plugin/blob/master/src/main/groovy/com/cdancy/gradle/etcd/rest/tasks/statistics/Leader.groovy) | Get the current leader of cluster |
| [Self](https://github.com/cdancy/gradle-etcd-rest-plugin/blob/master/src/main/groovy/com/cdancy/gradle/etcd/rest/tasks/statistics/Self.groovy) | Get the self instance of cluster |
| [Store](https://github.com/cdancy/gradle-etcd-rest-plugin/blob/master/src/main/groovy/com/cdancy/gradle/etcd/rest/tasks/statistics/Store.groovy) | Get the store (statistics) for cluster |
| [List](https://github.com/cdancy/gradle-etcd-rest-plugin/blob/master/src/main/groovy/com/cdancy/gradle/etcd/rest/tasks/members/List.groovy) | List of members in cluster |


## Extension

The `etcdRest` extension is provided to define the `url` and `credentials` for connecting to an Etcd instance.
Using the extension, and subsequently exposing this potentially private information, is required only if one does NOT want to use the various means of setting the aforementioned properties noted in the `Credentials` section below.

```
 etcdRest {
 	url { "http://127.0.0.1:2379" } // Optional and defaults to http://127.0.0.1:2379
 	credentials { "admin:password" } // Optional and defaults to null
 }
```

## On Url and Credentials

Because this plugin builds on top of [etcd-rest](https://github.com/cdancy/etcd-rest) library one can supply
the [url and credentials](https://github.com/cdancy/etcd-rest#credentials) in any form this library accepts. Furthermore,
[etcd-rest](https://github.com/cdancy/etcd-rest#property-based-setup) allows the `url` and `credentials`
to be optionally supplied through properties or environment variables. This gives great flexibility in the way the user
wants to define and/or hide their url or credentials assuming one does not want to use the `etcdRest` extension.

## Examples

The [functional](https://github.com/cdancy/gradle-etcd-rest-plugin/tree/master/src/functTest/groovy/com/cdancy/gradle/etcd/rest) tests provide many examples that you can use in your own code.

## Components

- etcd-rest \- java library used to interact with etcd program

## Testing
	
Running functional tests against an existing etcd program can be done like so:

	./gradlew functionalTest -PetcdUrl=http://127.0.0.1:2379
	
Running functional tests with docker can be done like so:

	./gradlew functionalTest -PbootstrapDocker=true
	
## Contributing
If you're looking for a new feature, or are interested in contributing, we'd love to review your PR. If you don't have a new feature in mind, and are more interested in just hacking on the project, feel free to reach out for suggestions.
	
## Additional Resources

* [Etcd REST API](https://github.com/coreos/etcd/blob/master/Documentation/api.md)
* [etcd-rest](https://github.com/cdancy/etcd-rest)
