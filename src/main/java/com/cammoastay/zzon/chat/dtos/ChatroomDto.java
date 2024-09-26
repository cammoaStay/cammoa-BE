package com.cammoastay.zzon.chat.dtos;


import com.cammoastay.zzon.chat.entities.ChatroomEntity;

import java.time.LocalDateTime;

public record ChatroomDto(
        Long id,
        String title,
        Boolean hasNewMessage,
        Integer memberCount,
        LocalDateTime createAt) {

    public static ChatroomDto from(ChatroomEntity chatroomEntity) {
        return new ChatroomDto(chatroomEntity.getId(), chatroomEntity.getTitle(), chatroomEntity.getHasNewMessage()
                ,chatroomEntity.getChatroomMappingEntitySet().size(), chatroomEntity.getCreateAt());
    }


}
