package com.cdancy.gradle.etcd.rest

import org.gradle.testkit.runner.BuildResult
import spock.lang.Requires

@Requires({ TestPrecondition.ETCD_URL_REACHABLE })
class MiscellaneousFunctionalTest extends AbstractFunctionalTest {

    def "Can get etcd version"() {

        buildFile << """
            task getVersion(type: com.cdancy.gradle.etcd.rest.tasks.miscellaneous.Version) {}
            
            task workflow {
                dependsOn getVersion
            }
        """

        when:
        BuildResult result = build('workflow')

        then:
        result.output.contains('Version:')
    }
}
