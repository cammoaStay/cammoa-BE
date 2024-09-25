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
@Controller
public class StompChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    private final MemberService memberService;

    public StompChatController(SimpMessagingTemplate messagingTemplate, ChatService chatService, MemberService memberService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
        this.memberService = memberService;
    }

    @MessageMapping("/chats/{chatroomId}")
    @SendTo("/sub/chats/{chatroomId}")
    public ChatMessageDto handleMessage(SimpMessageHeaderAccessor headerAccessor,
                                        @DestinationVariable Long chatroomId,
                                        @Payload Map<String, String> payload) {

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        // jwt인터셉트에서 토큰에서 추출한 username을 세션으로보낸것을 받아 username을 저장합니다.
        // -> 웹소켓 연결(upgrade) 이후 따로 커스텀헤더를 쓸수없어 연결전에 인터셉터 후 세션으로 보내 username을 받아옵니다.
        if (username == null) {
            log.error("User not authenticated");
            throw new IllegalArgumentException("User not authenticated");
        }
            log.info("Message sent to room {}: {}", chatroomId, payload.get("message"));

        Optional<MemberEntity> member = memberService.getUsername(username);
        MessageEntity message = chatService.saveMessage(member, chatroomId, payload.get("message"));
        messagingTemplate.convertAndSend("/sub/chats/updates", chatService.getChatroom(chatroomId));
        return new ChatMessageDto(username, payload.get("message"));
        // 해당 username으로 DB에서 해당 username의 정보를 받아와 메시지저장, 방정보를 /updates로 보내고
        // sender 의 이름, payload로 들어온 메시지를 리턴합니다.
    }
}




