package com.chitchat.service;

import com.chitchat.entity.ChatMessage;
import com.chitchat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository repository;

    public ChatMessage saveChatMessage(ChatMessage chatMessage) {
        chatMessage = this.repository.save(chatMessage);
        return chatMessage;
    }


    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        List<ChatMessage> messages = this.repository.findBySenderIdAndRecipientId(senderId, recipientId);
        return messages;
    }
}
