package com.cdancy.gradle.etcd.rest

import org.gradle.testkit.runner.BuildResult
import spock.lang.Requires

@Requires({ TestPrecondition.ETCD_URL_REACHABLE })
class MiscellaneousFunctionalTest extends AbstractFunctionalTest {

    def "Can get etcd version"() {

        buildFile << """
            task getVersion(type: com.cdancy.gradle.etcd.rest.tasks.miscellaneous.Version) {
            	doLast {
            		def foundVersion = version()
            		println "Etcd server-version: " + foundVersion.etcdServer()
            		println "Etcd cluster-version: " + foundVersion.etcdCluster()
            	}
            }
            
            task workflow {
                dependsOn getVersion
            }
        """

        when:
        BuildResult result = build('workflow')

        then:
        result.output.contains('Version:')
        !result.output.contains('Etcd server-version: null')
        !result.output.contains('Etcd cluster-version: null')
    }
    
    def "Can get health of cluster"() {

        buildFile << """
            task getHealth(type: com.cdancy.gradle.etcd.rest.tasks.miscellaneous.Health) {
            	doLast {
            		boolean status = health()
            	    println "Cluster health good: " + status
            	}
            }
            
            task workflow {
            	dependsOn getHealth
            }
        """

        when:
        BuildResult result = build('workflow')

        then:
        result.output.contains('Health:')
        result.output.contains('Cluster health good: true')
    }
    
    def "Can get metrics of cluster"() {

        buildFile << """
            task getMetrics(type: com.cdancy.gradle.etcd.rest.tasks.miscellaneous.Metrics) {
            	doLast {
            		String foundMetrics = metrics()
            		println "Cluster metrics: " + foundMetrics
            	}
            }
            
            task workflow {
                dependsOn getMetrics
            }
        """

        when:
        BuildResult result = build('workflow')

        then:
        result.output.contains('Metrics:')
        !result.output.contains('Cluster metrics: null')
    }
}
