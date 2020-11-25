package com.bbva.tinfoilhat.controller;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;


@ApplicationScoped
public class Controller {

    private static final Logger LOGGER = Logger.getLogger(Controller.class);

    public Controller(){
    }

    public List<SendMessage> performAction(Message messageReceived){
        LOGGER.info("Controller.performAction.in");
        SendMessage message = new SendMessage().setChatId(messageReceived.getChatId());
        Map<String, String> inputMap= this.getTokens(messageReceived);

        switch(inputMap.get("action")){

            //Mensaje por defecto cuando no entendemos la accion
            default:
                LOGGER.warn("Controller.performAction - No Action");
                message.setReplyToMessageId(messageReceived.getMessageId()).setText("Creo que no te he entendido bien");
        }
        LOGGER.info("Controller.performAction.out");
        return message;
    }



    private Map<String, String> getTokens (Message messageReceivedMessage){
        LOGGER.info("Controller.getTokens.in");

        Map<String, String> output = new HashMap<>();
        output.put("action", "NONE");

        LOGGER.info("Controller.getTokens.out");
        return  output;
    }

}