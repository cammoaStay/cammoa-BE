package com.cammoastay.zzon.chat.entities;

import com.cammoastay.zzon.jointest.MemberEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection= "messages")
public class MessageEntity {

    @Column(name = "message_id")
    @Id
    private String id;
    private String text;
    private LocalDateTime createdAt;
    private String nickName;
    private String chatroomId;

    @DBRef
    MemberEntity member;

    @DBRef
    ChatroomEntity chatroom;
}
