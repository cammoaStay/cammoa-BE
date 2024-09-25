package com.cammoastay.zzon.chat.entities;

import com.cammoastay.zzon.jointest.MemberEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection= "messages")
public class MessageEntity {

    @Id
    private String id;
    @Field("message_text")
    private String text;
    private LocalDateTime createdAt;
    private String nickName;
    private String chatroomId;

    @DBRef
    MemberEntity member;

    @DBRef
    ChatroomEntity chatroom;
}
