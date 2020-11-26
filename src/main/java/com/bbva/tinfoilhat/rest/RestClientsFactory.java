package com.bbva.tinfoilhat.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.enterprise.context.ApplicationScoped;

import com.bbva.tinfoilhat.rest.api.TaskServicesInterface;
import com.bbva.tinfoilhat.rest.api.UserServicesInterface;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.RestClientDefinitionException;
import org.jboss.logging.Logger;

@ApplicationScoped
public class RestClientsFactory{

    @ConfigProperty(name = "ENV_URI_BASE")
    String ENV_URI_BASE;
    
    private static final Logger LOGGER = Logger.getLogger(RestClientsFactory.class);

    public TaskServicesInterface getTaskServicesClient() { 
        TaskServicesInterface service = null;
        try{
            service = 
            RestClientBuilder.newBuilder().baseUri(new URI(ENV_URI_BASE)).build(TaskServicesInterface.class);
        } catch(IllegalStateException | RestClientDefinitionException | URISyntaxException excp){
            LOGGER.error("RestClientsFactory. getTaskServicesClient - Error: ", excp);
        }
        return service;
    }
    
     
    public UserServicesInterface getUserServicesClient() { 
        UserServicesInterface service = null;
        try{
            service = 
            RestClientBuilder.newBuilder().baseUri(new URI(ENV_URI_BASE)).build(UserServicesInterface.class);
        } catch(IllegalStateException | RestClientDefinitionException | URISyntaxException excp){
            LOGGER.error("RestClientsFactory. getTaskServicesClient - Error: ", excp);
        }
        return service;
    }

}