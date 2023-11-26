package pro.sky.telegrambot.service;

import java.sql.Struct;

public interface CommandHandlerService {
    String handleCommand(Long chatId, String command);
}
