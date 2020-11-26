package com.bbva.tinfoilhat.telegrambot;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.bbva.tinfoilhat.constants.Constants;

import com.bbva.tinfoilhat.controller.Controller;
import com.bbva.tinfoilhat.dtos.BotMessage;

import org.jboss.logging.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Path("/api/bot")
@Produces(APPLICATION_JSON)
public class TinFoilHatBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = Logger.getLogger(TinFoilHatBot.class);

    Controller controller;

    public TinFoilHatBot(){}

    public TinFoilHatBot(Controller controller ){
        this.controller = controller;
    }

    /**
     * Callback que utiliza el bot cuando recive un mensaje en el chat
     */
	public void onUpdateReceived(final Update update) {
        LOGGER.info("TinFoilHatBot.onUpdateReceived.in");
				
		try {
            //Enviamos los mensajes generados por el controlador
            for (SendMessage currentMessage : controller.performAction(update.getMessage()))
                execute(currentMessage);
            
		} catch (final TelegramApiException excp) {
			LOGGER.error("TinFoilHatBot.onUpdateReceived - Exception: ", excp);
        }
        LOGGER.info("TinFoilHatBot.onUpdateReceived.out");
	}
 
    // Se devuelve el nombre que dimos al bot al crearlo con el BotFather
	public String getBotUsername() {
		return Constants.BOT_NAME;
	}
 
    // Se devuelve el token que nos generó el BotFather de nuestro bot
	@Override
	public String getBotToken() {
		return Constants.BOT_TOKEN;
    }

    /**
     * Servicio para la publicación de mensajes en el chat
     */
    @POST
    @Path("/publishMessage")
    public Response publishMessage(@Valid final BotMessage message){
        LOGGER.info("TestBot.publishMessage.in");
        final SendMessage telegramMessage = 
                new SendMessage().setChatId(message.getConversationId()).setText(message.getText());
        try{
            
            execute(telegramMessage);
        } catch (final TelegramApiException excp){
            LOGGER.error("TestBot.publishMessage - Exception: ", excp);
            return Response.serverError().build();
        }
        LOGGER.info("TestBot.publishMessage.out");
        return Response.ok(telegramMessage).build();
    }

}