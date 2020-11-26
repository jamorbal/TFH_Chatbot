package com.bbva.tinfoilhat.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.bbva.tinfoilhat.actions.Actions;
import com.bbva.tinfoilhat.utilities.Utilities;
import org.jboss.logging.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;


@ApplicationScoped
public class Controller {

    private static final Logger LOGGER = Logger.getLogger(Controller.class);

    @Inject
    Actions actions;

    /**
     * Metodo que ejecuta la accion recibida en el mensaje de entrada
     * y devuelve la respuesta 
     * @param messageReceived mensaje de entrada
     * @return respuesta generada por la accion
     */
    public List<SendMessage> performAction(Message messageReceived){
        LOGGER.info("Controller.performAction.in");

        Map<String, List<String>> inputMap= this.getTokens(messageReceived);

        switch(inputMap.get("action").get(0)){
            case Actions.LOGGIN:
                return this.actions.loginAs(inputMap.get("args"), messageReceived);
            
            case Actions.LOGOFF:
                return this.actions.logOff(messageReceived);

            case Actions.SHOWMETHEMONEY:
                return this.actions.showMeTheMoney(messageReceived);

            case Actions.GETALLTASKS:
                return this.actions.getAllTasks(messageReceived, false);

            case Actions.GETMYTASKS:
                return this.actions.getAllTasks(messageReceived, true);

            case Actions.ASSIGNTASK:
                return this.actions.assignTask(inputMap.get("args"), messageReceived);
            
            case Actions.TASKDONE:
                return this.actions.taskDone(inputMap.get("args"), messageReceived);
                
            default:
                return this.actions.action_unknown(messageReceived);
        }
    }


    /**
     * Metodo que recibe el mensaje y lo tokeniza para obtener la accion y los paramentros
     * @param messageReceived
     * @return Mapa que contiene la accion y una lista de parametros
     */
    private Map<String, List<String>> getTokens (Message messageReceived){
        LOGGER.info("Controller.getTokens.in");

        Map<String, List<String>> output = new HashMap<>();
        output.put("action", Arrays.asList("NONE")); //Por defecto
        output.put("args", new ArrayList<>());

        List<String> splittedText = Arrays.asList(messageReceived.getText().trim().split(" "));
        if (!Utilities.isEmpty(splittedText) && !Utilities.isEmpty(splittedText.get(0)) && splittedText.get(0).startsWith("/")){
            output.put("action", Arrays.asList(splittedText.get(0).toUpperCase()));
        }
        if (splittedText.size()>1){
            //Vamos a aplanar todos los paramentros en un string separado por espacio
            output.put("args", Arrays.asList(String.join(" ", splittedText.subList(1, splittedText.size()))));    
        }

        LOGGER.info("Controller.getTokens.out");
        return  output;
    }


}