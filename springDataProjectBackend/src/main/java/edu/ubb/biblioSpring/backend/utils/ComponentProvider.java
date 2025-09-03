package edu.ubb.biblioSpring.backend.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class ComponentProvider {
    @Scope("prototype")
    @Bean
    public Logger LOGGER(InjectionPoint injectionPoint){
        return LoggerFactory.getLogger(injectionPoint.getField().getDeclaringClass());
    }
}
