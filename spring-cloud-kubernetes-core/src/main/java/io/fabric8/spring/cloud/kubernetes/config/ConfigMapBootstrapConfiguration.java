/*
 * Copyright (C) 2016 to the original authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.fabric8.spring.cloud.kubernetes.config;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.spring.cloud.kubernetes.KubernetesAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnProperty(value = "spring.cloud.kubernetes.enabled", matchIfMissing = true)
@ConditionalOnClass(ConfigMap.class)
public class ConfigMapBootstrapConfiguration {

    @Configuration
    @EnableConfigurationProperties(ConfigMapConfigProperties.class)
    @Import(KubernetesAutoConfiguration.class)
    @ConditionalOnProperty(name = "spring.cloud.kubernetes.config.enabled", matchIfMissing = true)
    protected static class KubernetesPropertySourceConfiguration {
        @Autowired
        private KubernetesClient client;

        @Bean
        public ConfigMapPropertySourceLocator configMapPropertySourceLocator(ConfigMapConfigProperties properties) {
            return new ConfigMapPropertySourceLocator(client, properties);
        }
    }
}
