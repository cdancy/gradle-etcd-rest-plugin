package com.cdancy.gradle.etcd.rest

import org.gradle.testkit.runner.BuildResult
import spock.lang.Requires

@Requires({ TestPrecondition.ETCD_URL_REACHABLE })
class MembersFunctionalTest extends AbstractFunctionalTest {

    def "Can list members"() {

        buildFile << """
            task listMembers(type: com.cdancy.gradle.etcd.rest.tasks.members.List) {
            	doLast {
            		def foundMembers = members()
            		println "Found members: " + foundMembers.size()
            		println "Member name: " + foundMembers.get(0).name
            	}
            }
            
            task workflow {
                dependsOn listMembers
            }
        """

        when:
        BuildResult result = build('workflow')

        then:
        result.output.contains('Members:')
        result.output.contains('Found members: 1')
        !result.output.contains('Member name: null')
    }
}
