package pro.sky.telegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.configuration.TelegramBotConfiguration;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.exception.IncorrectCreateTaskCommandException;
import pro.sky.telegrambot.service.CommandHandlerService;
import pro.sky.telegrambot.service.NotificationTaskService;
import pro.sky.telegrambot.util.MessageUtil;

import javax.management.Notification;


@RequiredArgsConstructor
@Service
public class CommandHandlerServiceImpl implements CommandHandlerService {

    private final TelegramBotConfiguration config;
    private final NotificationTaskService notificationTaskService;
    private static final String START_COMMAND = "/start";
    private static final String HELP_COMMAND = "/help";

    @Override
    public String handleCommand(Long chatId, String command) {
        if (command.equals(START_COMMAND)) {
            return config.getStartMsg();
        } else if (command.equals(HELP_COMMAND)) {
            return config.getHelpMsg();
        } else {
            return handleCreateTaskCommand(chatId, command);
        }
    }

    public String handleCreateTaskCommand(Long chatId, String command) {

        try {
            NotificationTask notificationTask = MessageUtil.parseCreateCommand(chatId, command);
            notificationTaskService.save(notificationTask);
            return config.getSuccessMsg();
        } catch (IncorrectCreateTaskCommandException e) {
            return config.getErrorMsg();
        }
    }
}
