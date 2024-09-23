package com.cammoastay.zzon.chat.controllers;


import com.cammoastay.zzon.chat.dtos.ChatMessageDto;
import com.cammoastay.zzon.chat.dtos.ChatroomDto;
import com.cammoastay.zzon.chat.entities.ChatroomEntity;
import com.cammoastay.zzon.chat.entities.MessageEntity;
import com.cammoastay.zzon.chat.services.AuthenticationService;
import com.cammoastay.zzon.chat.services.ChatService;
import com.cammoastay.zzon.jointest.MemberEntity;
import com.cammoastay.zzon.login.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chats")
@RestController
public class ChatController {

    private final ChatService chatService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public ChatroomDto createChatroom(
            @RequestHeader("Authorization") String token, // Authorization 헤더에서 JWT를 추출
            @RequestParam String title
    )
    {
        Optional<MemberEntity> member = authenticationService.memberFindByToken(token);
        // jwt를 서비스단에 보내 member를 찾아 저장
        ChatroomEntity chatroom = chatService.createChatroom(member.orElse(null), title);
        return ChatroomDto.from(chatroom);
    }

    @PostMapping("/{chatroomId}")
    public Boolean joinChatroom(@RequestHeader("Authorization") String token,
                                @PathVariable Long chatroomId,
                                @RequestParam(required = false) Long currentChatroomId)
    {
        MemberEntity foundMember = authenticationService.memberFindByToken(token).get();
        MemberEntity member = chatService.findByUserId(foundMember.getUserId());
        return chatService.joinChatroom(member, chatroomId, currentChatroomId);
    }

    @DeleteMapping("/{chatroomId}")
    public Boolean leaveChatroom(@RequestHeader("Authorization") String token, @PathVariable Long chatroomId) {
        Optional<MemberEntity> OptionalMember = authenticationService.memberFindByToken(token);
        MemberEntity foundMember = OptionalMember.get();
        MemberEntity member = chatService.findByUserId(foundMember.getUserId());
        return chatService.leaveChatroom(member, chatroomId);
    }

    @GetMapping
    public List<ChatroomDto> getChatroomList(@RequestHeader("Authorization") String token) {

        Optional<MemberEntity> foundMember = authenticationService.memberFindByToken(token);
        MemberEntity member = foundMember.get();
        List<ChatroomEntity> chatroomList = chatService.getChatroomList(member);
        return chatroomList.stream()
                .map(ChatroomDto::from)
                .toList();
    }

    @GetMapping("/{chatroomId}/messages")
    public List<ChatMessageDto> getMessageList(@PathVariable Long chatroomId) {
        List<MessageEntity> messageList = chatService.getMessageList(String.valueOf(chatroomId));
        return messageList.stream()
                .map(message -> new ChatMessageDto(message.getMember().getNickName(), message.getText()))
                .toList();
    }

}
