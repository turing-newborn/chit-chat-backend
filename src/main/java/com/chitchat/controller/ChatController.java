package com.chitchat.controller;

import com.chitchat.entity.ChatMessage;
import com.chitchat.entity.ChatNotification;
import com.chitchat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chitchat")
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService service;

    @GetMapping("/chat/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable String senderId, @PathVariable String recipientId) {
        List<ChatMessage> messages = this.service.findChatMessages(senderId, recipientId);
        return ResponseEntity.ok(messages);
    }

    @MessageMapping("/send")
    public void saveMessageAndPushToQueue(@Payload ChatMessage chatMessage) {
        var savedMessage = this.service.saveChatMessage(chatMessage);

        messagingTemplate.convertAndSendToUser(chatMessage.getRecipientId(), "/queue/messages",
                new ChatNotification(
                        savedMessage.getId(),
                        savedMessage.getSenderId(),
                        savedMessage.getRecipientId(),
                        savedMessage.getContent()
                ));

    }
}
