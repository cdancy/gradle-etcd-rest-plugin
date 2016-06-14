package com.cdancy.gradle.etcd.rest

import org.gradle.testkit.runner.BuildResult
import spock.lang.Requires

@Requires({ TestPrecondition.ETCD_URL_REACHABLE })
class StatisticsFunctionalTest extends AbstractFunctionalTest {

    def "Can get etcd Leader"() {

        buildFile << """
            task getLeader(type: com.cdancy.gradle.etcd.rest.tasks.statistics.Leader) {
            	doLast {
            		def foundLeader = leader()
            		println "Leader name: "	+ foundLeader.leader()
            		println "Leader followers: " + foundLeader.followers().toString()
            		println "Leader followers-size: " + foundLeader.followers.size()
            	}
            }
            
            task workflow {
                dependsOn getLeader
            }
        """

        when:
        BuildResult result = build('workflow')

        then:
        result.output.contains('Leader:')
        !result.output.contains('Leader name: null')
        !result.output.contains('Leader followers: null')
        !result.output.contains('Leader followers-size: -1')
    }
    
    def "Can get etcd Self"() {

        buildFile << """
            task getSelf(type: com.cdancy.gradle.etcd.rest.tasks.statistics.Self) {
            	doLast {
            		def foundSelf = self()
            		println "Self id: " + foundSelf.id
            		println "Self LeaderInfo: " + foundSelf.leaderInfo.toString()
            		println "Self name: " + foundSelf.name
            		println "Self state: " + foundSelf.state
            		
            	}
            }
            
            task workflow {
                dependsOn getSelf
            }
        """

        when:
        BuildResult result = build('workflow')

        then:
        result.output.contains('Self:')
        !result.output.contains('Self id: null')
        !result.output.contains('Self LeaderInfo: null')
        !result.output.contains('Self name: null')
        !result.output.contains('Self state: null')

    }
    
    def "Can get etcd Store"() {

        buildFile << """
            task getStore(type: com.cdancy.gradle.etcd.rest.tasks.statistics.Store) {
            	doLast {
            		def foundStore = store()
            		println "Store createFail: " + foundStore.createFail()
            		println "Store createSuccess: " + foundStore.createSuccess()
            		println "Store deleteFail: " + foundStore.deleteFail()
            		println "Store deleteSuccess: " + foundStore.deleteSuccess()
            	}
            }
            
            task workflow {
                dependsOn getStore
            }
        """

        when:
        BuildResult result = build('workflow')

        then:
        result.output.contains('Store:')
        !result.output.contains('Store createFail: -1')
        !result.output.contains('Store createSuccess: -1')
        !result.output.contains('Store deleteFail: -1')
        !result.output.contains('Store deleteSuccess: -1')

    }
}
