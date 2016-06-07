package com.cdancy.gradle.etcd.rest

import org.gradle.testkit.runner.BuildResult
import spock.lang.Requires

@Requires({ TestPrecondition.ETCD_URL_REACHABLE })
class StatisticsFunctionalTest extends AbstractFunctionalTest {

    def "Can get etcd Leader"() {

        buildFile << """
            task getLeader(type: com.cdancy.gradle.etcd.rest.tasks.statistics.Leader) {}
            
            task workflow {
                dependsOn getLeader
            }
        """

        when:
        BuildResult result = build('workflow')

        then:
        result.output.contains('Leader:')
    }
    
    def "Can get etcd Self"() {

        buildFile << """
            task getSelf(type: com.cdancy.gradle.etcd.rest.tasks.statistics.Self) {}
            
            task workflow {
                dependsOn getSelf
            }
        """

        when:
        BuildResult result = build('workflow')

        then:
        result.output.contains('Self:')
    }
    
    def "Can get etcd Store"() {

        buildFile << """
            task getStore(type: com.cdancy.gradle.etcd.rest.tasks.statistics.Store) {}
            
            task workflow {
                dependsOn getStore
            }
        """

        when:
        BuildResult result = build('workflow')

        then:
        result.output.contains('Store:')
    }
}
