package com.bbva.tinfoilhat.rest.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.bbva.tinfoilhat.dtos.User;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

public interface UserServicesInterface {

    @GET
    @Path("/children/{userid}")
    @Produces("application/json")
    List<User> getUserInfoById(@PathParam String userid);


    @PUT
    @Path("/children/{userid}/chatbot")
    void updateUserChatBotId(@PathParam String userid, @QueryParam("chatbotid") String chatBotId);

    @GET
    @Path("/children")
    @Produces("application/json")
    List<User> getUserInfoByChatId(@QueryParam("chatbotId") String chatBotId);

    
}