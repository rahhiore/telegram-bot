package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import pro.sky.telegrambot.service.*;

@Service
@RequiredArgsConstructor
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;
    private CommandHandlerService commandHandlerService;
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.stream().filter(update -> update.message() != null)
                    .forEach(this::processUpdate);
        } catch (Exception e) {
            logger.info("Error during processing telegram update ", e);
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
    private void processUpdate(Update update) {
        logger.info("Processing update: {}", update);
        String messageText = update.message().text();
        Long chatId = update.message().chat().id();
        String rs = commandHandlerService.handleCommand(chatId, messageText);

        sendMessage(chatId, rs);
    }

    private void sendMessage(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }
}
