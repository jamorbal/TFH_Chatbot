import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.enterprise.event.Observes;

import com.bbva.tinfoilhat.telegrambot.TinFoilHatBot;
import com.bbva.tinfoilhat.controller.Controller;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

import org.jboss.logging.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@ApplicationScoped
public class AppLifeCicleBean {

    private static final Logger LOGGER = Logger.getLogger("ListenerBean");

    @Inject
    Controller controller;

    void onStart(@Observes StartupEvent ev) {               
        LOGGER.info("The Telegram chat bot is starting...");
        
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
			// Se registra el bot
            telegramBotsApi.registerBot(new TinFoilHatBot(this.controller));

		} catch (TelegramApiException excp) {
            LOGGER.error("Exception while initializating TinFoilHatBot: ", excp);
		}
    }

    void onStop(@Observes ShutdownEvent ev) {               
        LOGGER.info("The Telegram chat bot is stopping...");
    }

}