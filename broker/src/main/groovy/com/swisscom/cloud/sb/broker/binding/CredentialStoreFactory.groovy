/*
 * Copyright (c) 2018 Swisscom (Switzerland) Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.swisscom.cloud.sb.broker.binding

import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CredentialStoreFactory implements FactoryBean<CredentialStoreStrategy> {

    @Autowired
    private DefaultCredentialStoreStrategy defaultCredentialStoreStrategy
    @Autowired
    private CredHubCredentialStoreStrategy credHubCredentialStoreStrategy

    @Override
    CredentialStoreStrategy getObject() throws Exception {
        credHubCredentialStoreStrategy.isCredHubServiceAvailable() ? credHubCredentialStoreStrategy : defaultCredentialStoreStrategy
    }

    @Override
    Class<?> getObjectType() {
        return CredentialStoreStrategy
    }

    @Override
    boolean isSingleton() {
        return true
    }

}
