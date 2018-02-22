import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MinBot extends TelegramLongPollingBot {

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
      // text to show off my skill to my friends
      String text = update.getMessage().getText();
      System.out.println("message: " + text);
      if ("대건이 형님".equals(text)) {
        text = "사랑합니다. 형님.";
      } else if ("은교 형수님".equals(text)) {
        text = "형수님을 사랑하면 큰일나죠. 존경합니다 형수님 ^0^!";
      }

      SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
              .setChatId(update.getMessage().getChatId())
              .setText(text);

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
