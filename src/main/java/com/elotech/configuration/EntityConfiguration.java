package com.elotech.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("com.elotech.entity")
public class EntityConfiguration {
}
