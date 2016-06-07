package com.cdancy.gradle.etcd.rest

import org.gradle.testkit.runner.BuildResult
import spock.lang.Requires

@Requires({ TestPrecondition.ETCD_URL_REACHABLE })
class EtcdVersionFunctionalTest extends AbstractFunctionalTest {

    def "Can get Etcd version"() {

        buildFile << """
            task getVersion(type: com.cdancy.gradle.etcd.rest.tasks.miscellaneous.Version) {}
            
            task workflow {
                dependsOn getVersion
            }
        """

        when:
        BuildResult result = build('workflow')

        then:
        result.output.contains('Server-Version:')
        result.output.contains('Cluster-Version:')
    }
}
