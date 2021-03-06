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

package com.swisscom.cloud.sb.broker.config

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.boot.env.YamlPropertySourceLoader
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.PropertySource
import org.springframework.core.io.FileSystemResource

@CompileStatic
@Slf4j
class WebContainerConfigurationInitializer implements ApplicationContextInitializer {
    private static final String CONFIG_FILE = "/servicebroker.yml"
    private static final String CATALINA_HOME = "catalina.home"
    private static final String CATALINA_BASE = "catalina.base"
    private static final String FOLDER_CONFIG = "/conf"

    void initialize(ConfigurableApplicationContext applicationContext) {
        String filePath = getFilePathInTomcatConfigFolder(CONFIG_FILE)
        if(!filePath){
            log.warn("Please make sure that catalina.home or catalina.base is configured correctly.")
            return
        }

        final File file = new File(filePath)
        if(file.exists()){
            log.warn('Adding config location:'+ filePath)
            applicationContext.getEnvironment().getPropertySources().addFirst(loadYamlResource(file))
        }else{
            log.warn("Config does not exist at location:${filePath}")
        }
    }

    static PropertySource loadYamlResource(File file) {
        FileSystemResource resource = new FileSystemResource(file)
        YamlPropertySourceLoader sourceLoader = new YamlPropertySourceLoader()
        PropertySource<?> yamlProperties = sourceLoader.load("externalYamlProperties", resource).first()
        return yamlProperties
    }

    static String getFilePathInTomcatConfigFolder(String fileName){
        if(System.getProperty(CATALINA_BASE)){
            return System.getProperty(CATALINA_BASE) + FOLDER_CONFIG + fileName
        }

        if(System.getProperty(CATALINA_HOME)){
            return System.getProperty(CATALINA_HOME) + FOLDER_CONFIG + fileName
        }

        return null
    }
}
