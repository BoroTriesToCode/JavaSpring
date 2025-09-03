package edu.ubb.biblioSpring.backend.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.PropertyResolver;
import java.util.Properties;

@Configuration
@PropertySource("classpath:/application.properties")
public class PropertyManager {
    @Autowired
    private PropertyResolver propertyResolver;
    private static Properties properties;

    public String getProperties(String key){
        return propertyResolver.getProperty(key);
    }

}
