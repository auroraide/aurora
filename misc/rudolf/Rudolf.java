import aurora.backend.TermVisitor;
import aurora.backend.library.HashLibrary;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Rudolf extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String reply;
            try {
                reply = (new LambdaParser(new HashLibrary()))
                        .parse((new LambdaLexer())
                                .lex(update.getMessage().getText()))
                        .accept(new TermPrinter());
            } catch (SyntaxException e) {
                reply = e.getMessage();
            } catch (SemanticException e) {
                reply = e.getMessage();
            }

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

    @Override
    public String getBotUsername() {
        return "RudolfParserBot";
    }

    @Override
    public String getBotToken() {
        return "527416526:AAEqHHE-HcsDHzuIuHOwTfypZyM3Xw7leBo";
    }

    private class TermPrinter extends TermVisitor<String> {

        @Override
        public String visit(Abstraction abs) {
            return "(\\" + abs.name + "." + abs.body.accept(this) + ")";
        }

        @Override
        public String visit(Application app) {
            return "(" + app.left.accept(this) + " " + app.right.accept(this) + ")";
        }

        @Override
        public String visit(BoundVariable bvar) {
            return "" + bvar.index;
        }

        @Override
        public String visit(FreeVariable fvar) {
            return fvar.name;
        }

        @Override
        public String visit(Function libterm) {
            return "$" + libterm.name;
        }

        @Override
        public String visit(ChurchNumber c) {
            return "c" + c.value;
        }

    }

}
