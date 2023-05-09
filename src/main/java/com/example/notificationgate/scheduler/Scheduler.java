package com.example.notificationgate.scheduler;

import com.example.notificationgate.model.EventUser;
import com.example.notificationgate.model.NotifyStrategy;
import com.example.notificationgate.model.dto.Message;
import com.example.notificationgate.model.enums.Stage;
import com.example.notificationgate.repo.EventUserRepository;
import com.example.notificationgate.scheduler.email.EmailService;
import com.example.notificationgate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class Scheduler {

    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final EventUserRepository eventUserRepository;

    private String url = "http://localhost:8080/events/set-answer/";

    @Scheduled(cron = "0 * * * * *")
    public void performNotify() {
        System.out.println("Begin");
        List<EventUser> eventUsers = eventUserRepository.findAll();
        eventUsers.forEach(e -> {
                    if (e.getNextNotify() != null && e.getNextNotify().isBefore(LocalDateTime.now())) {
                        Message message = Message.builder()
                                .name(e.getEvent().getName())
                                .timestamp(e.getEvent().getTimestamp())
                                .description(e.getEvent().getDescription())
                                .okUrl(url + e.getEvent().getId() + "/OK")
                                .cancelUrl(url + e.getEvent().getId() + "/CANCEL")
                                .build();

                        if (e.getNotifyStrategy().getFirstCorpEmailSendStage().getValue() <= e.getNextStage().getValue()
                                && e.getEvent().getIsCorpEmailSend()) {
                            message.setConsumer(e.getUser().getCorpEmail());
                            kafkaTemplate.send("email", message);
                        }

                        if (e.getNotifyStrategy().getFirstEmailSendStage().getValue() <= e.getNextStage().getValue()
                                && e.getEvent().getIsEmailSend()) {
                            message.setConsumer(e.getUser().getEmail());
                            kafkaTemplate.send("email", message);
                        }

                        if (e.getNotifyStrategy().getFirstSmsSendStage().getValue() <= e.getNextStage().getValue()
                                && e.getEvent().getIsSmsSend()) {
                            message.setConsumer(e.getUser().getPhone());
                            kafkaTemplate.send("sms", message);
                        }

                        if (e.getNotifyStrategy().getFirstTgSendStage().getValue() <= e.getNextStage().getValue()
                                && e.getEvent().getIsTgSend()) {
                            message.setConsumer(e.getUser().getTelegramChatId());
                            kafkaTemplate.send("tg", message);
//                            telegramService.sendMessageToUser(e.getEvent().getName() + "\n" + e.getEvent().getDescription() + "\n" + e.getEvent().getTimestamp()
//                                            + "\nПодтвердить " + getOkLink(e)+ "\nОтменить " + getCancelLink(e),
//                                    e.getUser().getTelegramChatId());

                        }

                        e.setNextStage(getNextStage(e.getNotifyStrategy(), e.getEvent().getTimestamp()));
                        switch (e.getNextStage()) {
                            case FIRST ->
                                    e.setNextNotify(e.getEvent().getTimestamp().minusMinutes(e.getNotifyStrategy().getStageFirstBeforeMinutes()));
                            case SECOND ->
                                    e.setNextNotify(e.getEvent().getTimestamp().minusMinutes(e.getNotifyStrategy().getStageSecondBeforeMinutes()));
                            case FINAL ->
                                    e.setNextNotify(e.getEvent().getTimestamp().minusMinutes(e.getNotifyStrategy().getStageFinalBeforeMinutes()));
                        }
                        eventUserRepository.save(e);
                    }
                }
        );
    }

    private Stage getNextStage(NotifyStrategy notifyStrategy, LocalDateTime timestamp) {
        Stage[] stages = new Stage[]{Stage.FIRST, Stage.SECOND, Stage.FINAL};
        for (Stage stage : stages) {
            if (timestamp.minusMinutes(notifyStrategy.getValueByStage(stage)).isAfter(LocalDateTFime.now())) {
                return stage;
            }
        }
        return null;
    }

}
