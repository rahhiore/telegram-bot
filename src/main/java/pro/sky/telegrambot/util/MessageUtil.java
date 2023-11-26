package pro.sky.telegrambot.util;

import org.springframework.util.StringUtils;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.exception.IncorrectCreateTaskCommandException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {
    private static final Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static NotificationTask parseCreateCommand(long chatId, String command) throws IncorrectCreateTaskCommandException {
        if (StringUtils.hasText(command)) {
            Matcher matcher = pattern.matcher(command);

            if(matcher.find()) {
                LocalDateTime dateTime = LocalDateTime.parse(matcher.group(1));
                String taskText = matcher.group(3);

                checkDate(dateTime);
                checkTaskText(taskText);
            }
        }
        throw new IncorrectCreateTaskCommandException("Incorrect command: " + command);
    }


    private static void checkDate(LocalDateTime dateTime) throws IncorrectCreateTaskCommandException{
        if (dateTime == null) {
            throw new IncorrectCreateTaskCommandException("Incorrect task dateTime: " + dateTime);
        } else if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IncorrectCreateTaskCommandException("Incorrect task dateTime: " + dateTime);
        }
    }

    private static void checkTaskText(String taskText) throws IncorrectCreateTaskCommandException{
        if (!StringUtils.hasText(taskText)) {
            throw new IncorrectCreateTaskCommandException("Incorrect task text: " + taskText);
        }
    }

    private static LocalDateTime parse(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
