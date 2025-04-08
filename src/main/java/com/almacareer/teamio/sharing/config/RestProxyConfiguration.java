package com.almacareer.teamio.sharing.config;

import eu.lmc.base.spring.beans.resteasy.RestEasyProxyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestProxyConfiguration {

    @Bean
    public ISlowApi slowApi(RestEasyProxyFactory restEasyProxyFactory) {
        return restEasyProxyFactory.proxy(ISlowApi.class, "slow.host", "");
    }

}
