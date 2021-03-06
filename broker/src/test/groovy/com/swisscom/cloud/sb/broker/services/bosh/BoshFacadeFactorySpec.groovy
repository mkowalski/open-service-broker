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

package com.swisscom.cloud.sb.broker.services.bosh

import com.swisscom.cloud.sb.broker.services.bosh.client.BoshClientFactory
import com.swisscom.cloud.sb.broker.services.common.TemplateConfig
import com.swisscom.cloud.sb.broker.services.mongodb.enterprise.openstack.OpenStackClientFactory
import spock.lang.Specification

class BoshFacadeFactorySpec extends Specification {

    def "facade is created correctly"() {
        given:
        BoshClientFactory boshClientFactory = Mock(BoshClientFactory)
        OpenStackClientFactory openStackClientFactory = Mock(OpenStackClientFactory)
        BoshTemplateFactory boshTemplateFactory = Mock(BoshTemplateFactory)
        TemplateConfig templateConfig = Mock(TemplateConfig)
        BoshFacadeFactory boshFacadeFactory = new BoshFacadeFactory(boshClientFactory, openStackClientFactory, boshTemplateFactory, templateConfig)

        BoshBasedServiceConfig boshBasedServiceConfig = new DummyConfig()
        when:
        def result = boshFacadeFactory.build(boshBasedServiceConfig)

        then:
        result.openStackClientFactory == openStackClientFactory
        result.boshClientFactory == boshClientFactory
        result.serviceConfig == boshBasedServiceConfig
        result.boshTemplateFactory == boshTemplateFactory
    }
}
