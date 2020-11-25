package com.bbva.tinfoilhat;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
 
public class TestBot extends TelegramLongPollingBot {
 
	public void onUpdateReceived(final Update update) {
		// Esta función se invocará cuando nuestro bot reciba un mensaje
 
		// Se obtiene el mensaje escrito por el usuario
		final String messageTextReceived = update.getMessage().getText();
 
		// Se obtiene el id de chat del usuario
		final long chatId = update.getMessage().getChatId();
		if (update.getMessage().hasPhoto()) {
			System.out.println("Photo Received");
		};
 
		System.out.println(chatId);
		
		// Se crea un objeto mensaje
		SendMessage message = update.getMessage().hasPhoto()? new SendMessage().setChatId(chatId).setText("Metete la foto por el culo") :new SendMessage().setChatId(chatId).setText(messageTextReceived);
		
		try {
			// Se envía el mensaje
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
 
	public String getBotUsername() {
		// Se devuelve el nombre que dimos al bot al crearlo con el BotFather
		return "EchoBot";
	}
 
	@Override
	public String getBotToken() {
		// Se devuelve el token que nos generó el BotFather de nuestro bot
		return "1457391106:AAFpLGSOF8wKO3gm3Er3P6k1Gc1qmNOSWNY";
    }
}