import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.runtime.Quarkus;

import com.bbva.tinfoilhat.TestBot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@QuarkusMain  
public class Main {

    

    public static void main(final String ... args) {
        System.out.println("Initializating Telegram Chatbot ...");

        ApiContextInitializer.init();
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
			// Se registra el bot
			telegramBotsApi.registerBot(new TestBot());
			
		} catch (TelegramApiException excp) {
            System.out.println(String.format("Exception while initializating chat bot: %s", excp.getMessage()));
		}

        Quarkus.run(args); 
    }
}