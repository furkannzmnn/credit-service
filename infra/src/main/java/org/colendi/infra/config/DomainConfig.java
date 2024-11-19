package org.colendi.infra.config;

import org.colendi.domain.config.usecase.DomainComponent;
import org.colendi.domain.config.usecase.MessageDriven;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "org.colendi.domain",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = DomainComponent.class),
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MessageDriven.class)
        }

)
public class DomainConfig {
}
