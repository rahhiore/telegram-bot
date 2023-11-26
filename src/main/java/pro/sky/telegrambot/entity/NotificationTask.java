package pro.sky.telegrambot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name="notification_tasks")
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "chat_id", nullable = false)
    private long chatId;

    @Column(nullable = false)
    private String messageText;

    @Column(name = "notification_date_time", nullable = false)
    private LocalDateTime notificationDateTime;

    public NotificationTask(long chatId, String messageText, LocalDateTime notificationDateTime) {
        this.chatId = chatId;
        this.messageText = messageText;
        this.notificationDateTime = notificationDateTime;
    }


}
