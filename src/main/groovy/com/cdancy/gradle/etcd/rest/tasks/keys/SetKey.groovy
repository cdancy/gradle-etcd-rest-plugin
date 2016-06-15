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
package com.cdancy.gradle.etcd.rest.tasks.keys

import com.cdancy.gradle.etcd.rest.tasks.KeyValueAware
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional;

/**
 * Set/Create a <a href="https://github.com/cdancy/etcd-rest/blob/master/src/main/java/com/cdancy/etcd/rest/features/KeysApi.java#L51">Key</a>
 */
class SetKey extends KeyValueAware {

	@Input
    @Optional
    Integer ttl

    @Input
    @Optional
    Boolean inOrder

    private def instance

    @Override
    void runRemoteCommand(etcdClient) {
        def api = etcdClient.api().keysApi()
        if (getInOrder()) {
            if (getTtl()) {
                instance = api.createInOrderKey(key(), value(), getTtl())
            } else {
                instance = api.createInOrderKey(key(), value())
            }
        } else {
            if (getTtl()) {
                instance = api.createKey(key(), value(), getTtl())
            } else {
                instance = api.createKey(key(), value())
            }
        }
        logger.quiet "Set Key: ${instance}"
    }

    def instance() { instance }
}
