package com.bbva.tinfoilhat.TelegramBot;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.inject.Inject;

import com.bbva.tinfoilhat.controller.Controller;
import com.bbva.tinfoilhat.dtos.BotMessage;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Path("/api/bot")
@Produces(APPLICATION_JSON)
public class TinFoilHatBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = Logger.getLogger(TinFoilHatBot.class);

    @ConfigProperty(name = "tinfoilbot.token")
    String botToken;

    @ConfigProperty(name = "tinfoilbot.name")
    String botName;

    Controller controller = new Controller();

    /**
     * Callback que utiliza el bot cuando recive un mensaje en el chat
     */
	public void onUpdateReceived(final Update update) {
        LOGGER.info("TinFoilHatBot.onUpdateReceived.in");
		// Esta función se invocará cuando nuestro bot reciba un mensaje
        

        //Message messageReceived = update.getMessage();
		// Se obtiene el mensaje escrito por el usuario
		final String messageTextReceived = update.getMessage().getText();
 
		// Se obtiene el id de chat del usuario
		//final long chatId = update.getMessage().getChatId();
		//if (update.getMessage().hasPhoto()) {
		//	LOGGER.warn("Photo Received");
		//};
 
        //LOGGER.info("ChatId: " + chatId);
        //LOGGER.info("Message: " + messageTextReceived);
		
		// Se crea un objeto mensaje
		//final SendMessage message = update.getMessage().hasPhoto()? new SendMessage().setChatId(chatId).setText("Metete la foto por el culo") :new SendMessage().setChatId(chatId).setText(messageTextReceived);
		
		try {
            // Enviamos el mensaje que genere el controlador
            execute(controller.performAction(update.getMessage()));
            
		} catch (final TelegramApiException excp) {
			LOGGER.error("TinFoilHatBot.onUpdateReceived - Exception: ", excp);
        }
        LOGGER.info("TinFoilHatBot.onUpdateReceived.out");
	}
 
    // Se devuelve el nombre que dimos al bot al crearlo con el BotFather
	public String getBotUsername() {
		return "EchoBot";
	}
 
    // Se devuelve el token que nos generó el BotFather de nuestro bot
	@Override
	public String getBotToken() {
		return "1457391106:AAFpLGSOF8wKO3gm3Er3P6k1Gc1qmNOSWNY";
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
            LOGGER.error("TestBot.publishMessage - Exception: {}", excp);
            return Response.serverError().build();
        }
        LOGGER.info("TestBot.publishMessage.out");
        return Response.ok(telegramMessage).build();
    }

}