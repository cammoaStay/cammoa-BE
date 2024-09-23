package com.cammoastay.zzon.chat.entities;

import com.cammoastay.zzon.jointest.MemberEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatroomMappingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member chatroom mapping id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")  // member 테이블의 id 컬럼 참조
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    private ChatroomEntity chatroomEntity;

    LocalDateTime lastCheckedAt;

    public void updateLastCheckedAt() {
        this.lastCheckedAt = LocalDateTime.now();
    }

}
