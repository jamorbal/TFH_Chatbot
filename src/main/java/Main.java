import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.runtime.Quarkus;

import com.bbva.tinfoilhat.TelegramBot.TinFoilHatBot;

import org.jboss.logging.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@QuarkusMain  
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(final String ... args) {
        LOGGER.info("Initializating Telegram Chatbot ...");

        ApiContextInitializer.init();
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
			// Se registra el bot
            telegramBotsApi.registerBot(new TinFoilHatBot());

			
		} catch (TelegramApiException excp) {
            LOGGER.error("Exception while initializating TinFoilHatBot: ", excp);
		}

        Quarkus.run(args); 
    }
}