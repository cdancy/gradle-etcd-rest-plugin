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
package com.cdancy.gradle.etcd.rest.utils

import com.cdancy.gradle.etcd.rest.EtcdRestExtension
import org.gradle.api.GradleException
import org.gradle.api.file.FileCollection

import java.lang.reflect.Method

class EtcdRestThreadContextClassLoader {
    public static final String CLIENT_CLASS = "com.cdancy.etcd.rest.EtcdClient"

    private final EtcdRestExtension etcdRestExtension
    private final FileCollection classpath
    private def etcdClient

    EtcdRestThreadContextClassLoader(EtcdRestExtension etcdRestExtension, FileCollection classpath) {
        this.etcdRestExtension = etcdRestExtension
        this.classpath = classpath
    }

    void withClosure(Closure closure) {
        if (!etcdClient) {
            etcdClient = generateClient()
        }
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.delegate = this
        closure(etcdClient)
    }
	
    private def generateClient() {
        ClassLoader classLoader = EtcdRestUtil.createClassLoader(classpath.files)
        Class clientClass = EtcdRestUtil.loadClass(classLoader, CLIENT_CLASS)
		String endPoint = etcdRestExtension.url ? etcdRestExtension.url.call() : null;
		String credentials = etcdRestExtension.credentials ? etcdRestExtension.credentials.call() : null;
		clientClass.getConstructor(String, String).newInstance(endPoint,credentials)
    }
}
