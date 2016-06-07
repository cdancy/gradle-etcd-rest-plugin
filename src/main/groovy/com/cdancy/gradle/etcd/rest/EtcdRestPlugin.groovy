/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cdancy.gradle.etcd.rest;

import com.cdancy.gradle.etcd.rest.tasks.AbstractEtcdRestTask;
import com.cdancy.gradle.etcd.rest.utils.EtcdRestThreadContextClassLoader
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

/**
 * Gradle plugin that provides custom tasks for interacting with Etcd via its remote API.
 */
class EtcdRestPlugin implements Plugin<Project> {
    public static final String ETCD_CONFIGURATION_NAME = 'etcdRest'
    public static final String ETCD_REST_DEFAULT_VERSION = '0.9.2'
    public static final String EXTENSION_NAME = 'etcdRest'
    public static final String DEFAULT_TASK_GROUP = 'EtcdRest'

    @Override
    void apply(Project project) {

        Configuration configuration = project.configurations.create(ETCD_CONFIGURATION_NAME)
                .setVisible(false)
                .setTransitive(true)
                .setDescription('The Etcd Java libraries to be used for this project.')

        EtcdRestExtension extension = project.extensions.create(EXTENSION_NAME, EtcdRestExtension)
		configureAbstractEtcdTask(project, extension)
    }
	
    private void configureAbstractEtcdTask(Project project, EtcdRestExtension extension) {
        EtcdRestThreadContextClassLoader etcdClassLoader = new EtcdRestThreadContextClassLoader(extension, configurePluginClassPath(project))
        project.tasks.withType(AbstractEtcdRestTask) {
            group = DEFAULT_TASK_GROUP
            threadContextClassLoader = etcdClassLoader
        }
    }

    private static Configuration configurePluginClassPath(Project project) {
        project.repositories.addAll(project.buildscript.repositories.collect())
        Configuration configuration = project.configurations.getByName(ETCD_CONFIGURATION_NAME)
        configuration.defaultDependencies { dependencies ->
            dependencies.add(project.dependencies.create("com.cdancy:etcd-rest:$EtcdRestPlugin.ETCD_REST_DEFAULT_VERSION"))
            dependencies.add(project.dependencies.create('org.slf4j:slf4j-simple:1.7.5'))
        }
        configuration
    }
}
