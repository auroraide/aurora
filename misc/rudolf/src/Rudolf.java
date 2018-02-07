import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.library.HashLibrary;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Rudolf extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String query = update.getMessage().getText();

            if (!update.getMessage().isUserMessage()) {
                if (update.getMessage().getText().matches("/parse@RudolfParserBot\\s.*")) {
                    query = update.getMessage().getText().substring(23);
                } else {
                    return;
                }
            }

            String reply = this.parseMessage(query);

            System.out.println("< " + query);
            System.out.println("> " + reply);

            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText(reply);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String parseMessage(String query) {
        try {
            return new HighlightableLambdaExpression(new LambdaParser(new HashLibrary())
                    .parse(new LambdaLexer().lex(query))).toString();
        } catch (SyntaxException | SemanticException e) {
            return e.getMessage();
        }
    }

    @Override
    public String getBotUsername() {
        return "RudolfParserBot";
    }

    @Override
    public String getBotToken() {
        return "527416526:AAEqHHE-HcsDHzuIuHOwTfypZyM3Xw7leBo";
    }

}
