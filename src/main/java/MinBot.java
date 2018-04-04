import com.minbot.model.Token;
import com.minbot.util.TokenConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class MinBot extends TelegramLongPollingBot {

  final Logger LOGGER = LoggerFactory.getLogger(MinBot.class);
  private final Token token;

  public MinBot() {
    token = TokenConfig.loadProperties();
  }

  public void onUpdateReceived(Update update) {
// We check if the update has a message and the message has text
    if (update.hasMessage() && update.getMessage().hasText()) {
      String text = update.getMessage().getText();
      String userName = update.getMessage().getFrom().getUserName();
      LOGGER.info("[{}]: {}", userName, text);
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
    return token.getBotName();
  }

  public String getBotToken() {
    return token.getBotToken();
  }
}
