package com.cdancy.gradle.etcd.rest

import org.gradle.testkit.runner.BuildResult
import spock.lang.Requires

@Requires({ TestPrecondition.ETCD_URL_REACHABLE })
class KeysFunctionalTest extends AbstractFunctionalTest {

    def "Can set, get, and delete a key"() {

        buildFile << """
            task setKey(type: com.cdancy.gradle.etcd.rest.tasks.keys.SetKey) {
                key { "hello" }
                value { "world" }
            	doLast {
            		def foundInstance = instance()
            		println "Key action: " + foundInstance.action
            		println "Key node-key: " + foundInstance.node.key
                    println "Key node-value: " + foundInstance.node.value
            		println "Key ErrorMessage: " + foundInstance.errorMessage
            	}
            }

            task getKey(type: com.cdancy.gradle.etcd.rest.tasks.keys.GetKey, dependsOn: setKey) {
                key { "hello" }
                doLast {
                    def foundInstance = instance()
                    println "Key get action: " + foundInstance.action
                    println "Key get node-value: " + foundInstance.node.value
                }
            }

            task deleteKey(type: com.cdancy.gradle.etcd.rest.tasks.keys.DeleteKey, dependsOn: getKey) {
                key { "hello" }
                doLast {
                    def foundInstance = instance()
                    println "Key delete action: " + foundInstance.action
                }
            }
            
            task workflow {
                dependsOn deleteKey
            }
        """

        when:
        BuildResult result = build('workflow')

        then:
        result.output.contains('Get Key:')
        result.output.contains('Key action: set')
        result.output.contains('Key node-key: /hello')
        result.output.contains('Key node-value: world')
        result.output.contains('Key ErrorMessage: null')
        result.output.contains('Key get action: get')
        result.output.contains('Key get node-value: world')
        result.output.contains('Key delete action: delete')
    }

    def "Can set, get, and delete an in-order key"() {

        buildFile << """
            task setKey(type: com.cdancy.gradle.etcd.rest.tasks.keys.SetKey) {
                key { "hello-in-order" }
                value { "world-in-order" }
                inOrder = true
            	doLast {
            		def foundInstance = instance()
            		println "Key action: " + foundInstance.action
            		println "Key node-key: " + foundInstance.node.key
                    println "Key node-value: " + foundInstance.node.value
            		println "Key ErrorMessage: " + foundInstance.errorMessage
            	}
            }

            task getKey(type: com.cdancy.gradle.etcd.rest.tasks.keys.GetKey, dependsOn: setKey) {
                key { "hello-in-order" }
                doLast {
                    def foundInstance = instance()
                    println "Key get action: " + foundInstance.action
                }
            }

            task workflow {
                dependsOn getKey
            }
        """

        when:
        BuildResult result = build('workflow')

        then:
        result.output.contains('Set Key:')
        result.output.contains('Key action: create')
        result.output.contains('Key node-key: /hello-in-order')
        result.output.contains('Key node-value: world-in-order')
        result.output.contains('Key ErrorMessage: null')
        result.output.contains('Key get action: get')
    }

    def "Can set key with ttl"() {

        buildFile << """
            task setKey(type: com.cdancy.gradle.etcd.rest.tasks.keys.SetKey) {
                key { "hello-ttl" }
                value { "world-ttl" }
                ttl = 1
            	doLast {
            		sleep 2000
            	}
            }

            task getKey(type: com.cdancy.gradle.etcd.rest.tasks.keys.GetKey, dependsOn: setKey) {
                key { "hello-ttl" }
                doLast {
                    def foundInstance = instance()
                    println "Key ErrorMessage: " + foundInstance.errorMessage.message
                }
            }

            task deleteKey(type: com.cdancy.gradle.etcd.rest.tasks.keys.DeleteKey, dependsOn: getKey) {
                key { "hello-ttl" }
                doLast {
                    def foundInstance = instance()
                    println "Key Delete ErrorMessage: " + foundInstance.errorMessage.message
                }
            }

            task workflow {
                dependsOn deleteKey
            }
        """

        when:
        BuildResult result = build('workflow')

        then:
        result.output.contains('Key ErrorMessage: Key not found')
        result.output.contains('Key Delete ErrorMessage: Key not found')
    }
}
