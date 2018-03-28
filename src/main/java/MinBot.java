import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MinBot extends TelegramLongPollingBot {

  final Logger logger = LoggerFactory.getLogger( MinBot.class );

  private String token;
  private String botName;

  public MinBot() {
    String resourceName = "application.properties";
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    try (InputStream input = loader.getResourceAsStream(resourceName)) {
      Properties properties = new Properties();
      // load a properties file
      properties.load(input);

      // get the property value and print it out
      token = properties.getProperty("minbot.token");
      botName = properties.getProperty("minbot.name");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void onUpdateReceived(Update update) {
// We check if the update has a message and the message has text
    if (update.hasMessage() && update.getMessage().hasText()) {
      String text = update.getMessage().getText();
      logger.info( text );
      SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
              .setChatId(update.getMessage().getChatId())
              .setText(update.getMessage().getText());
      try {
        execute(message); // Call method to send the message
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }
  }

  public String getBotUsername() {
    return botName;
  }

  public String getBotToken() {
    return token;
  }
}
