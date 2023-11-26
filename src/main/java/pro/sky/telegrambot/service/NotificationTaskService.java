package pro.sky.telegrambot.service;

import pro.sky.telegrambot.entity.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationTaskService {
    void save(NotificationTask task);
    void delete(NotificationTask task);
    List<NotificationTask> findAllByNotificationDateTime(LocalDateTime localDateTime);
}
