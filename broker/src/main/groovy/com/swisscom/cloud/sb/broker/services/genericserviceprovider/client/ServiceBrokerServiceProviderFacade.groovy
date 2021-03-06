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

package com.swisscom.cloud.sb.broker.services.genericserviceprovider.client

import com.swisscom.cloud.sb.broker.model.ServiceInstance
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@CompileStatic
@Slf4j
class ServiceBrokerServiceProviderFacade {

    private final String TESTING_SERVICE_INSTANCE_ID = "asyncServiceBrokerServiceProviderInstanceId"

    private ServiceBrokerServiceProviderRestClient sbspRestClient

    @Autowired
    ServiceBrokerServiceProviderFacade(ServiceBrokerServiceProviderRestClient sbspRestClient) {
        this.sbspRestClient = sbspRestClient
    }

    boolean provisionServiceInstance(ServiceInstance serviceInstance) {
        if (serviceInstance.guid == TESTING_SERVICE_INSTANCE_ID) {
            sbspRestClient = new TestableServiceBrokerServiceProviderRestClient()
        }
        return sbspRestClient.provisionServiceInstance(serviceInstance)

    }

    boolean deprovisionServiceInstance(ServiceInstance serviceInstance) {
        if (serviceInstance.guid == TESTING_SERVICE_INSTANCE_ID)
            sbspRestClient = new TestableServiceBrokerServiceProviderRestClient()
        return sbspRestClient.deprovisionServiceInstance(serviceInstance)
    }
}
