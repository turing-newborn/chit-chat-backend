package com.chitchat.service;

import com.chitchat.entity.ChatRoom;
import com.chitchat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository repository;
    public Optional<String> getChatRoomId(String senderId, String recipientId, boolean createChatRoomIfNotExists) {
        return repository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatRoomId)
                .or(() -> {
                    if(createChatRoomIfNotExists) {
                        var chatRoomId = createChatRoomId(senderId, recipientId);
                        return Optional.of(chatRoomId);
                    }

                    return  Optional.empty();
                });
    }

    private String createChatRoomId(String senderId, String recipientId) {
        var chatRoomId = String.format("%s_%s", senderId, recipientId);

        ChatRoom senderRecipient = ChatRoom
                .builder()
                .chatRoomId(chatRoomId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        ChatRoom recipientSender = ChatRoom
                .builder()
                .chatRoomId(chatRoomId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        repository.save(senderRecipient);
        repository.save(recipientSender);

        return chatRoomId;
    }
}
