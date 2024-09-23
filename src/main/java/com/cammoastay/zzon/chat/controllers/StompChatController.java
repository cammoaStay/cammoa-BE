package com.cammoastay.zzon.chat.controllers;

import com.cammoastay.zzon.chat.dtos.ChatMessageDto;
import com.cammoastay.zzon.chat.entities.MessageEntity;
import com.cammoastay.zzon.chat.services.ChatService;
import com.cammoastay.zzon.jointest.MemberEntity;
import com.cammoastay.zzon.jointest.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StompChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    private final MemberService memberService;

    @MessageMapping("/chats/{chatroomId}")
    @SendTo("/sub/chats/{chatroomId}")
    public ChatMessageDto handleMessage(SimpMessageHeaderAccessor headerAccessor,
                                        @DestinationVariable Long chatroomId,
                                        @Payload Map<String, String> payload) {

        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username == null) {
            log.error("User not authenticated");
            throw new IllegalArgumentException("User not authenticated");
        }
            log.info("Message sent to room {}: {}", chatroomId, payload.get("message"));

        Optional<MemberEntity> member = memberService.getUsername(username);
        MessageEntity message = chatService.saveMessage(member, chatroomId, payload.get("message"));
        messagingTemplate.convertAndSend("/sub/chats/updates", chatService.getChatroom(chatroomId));
        return new ChatMessageDto(username, payload.get("message"));
    }
}




