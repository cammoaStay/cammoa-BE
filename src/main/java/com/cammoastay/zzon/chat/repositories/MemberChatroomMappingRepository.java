package com.cammoastay.zzon.chat.repositories;


import com.cammoastay.zzon.chat.entities.ChatroomMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberChatroomMappingRepository extends JpaRepository <ChatroomMappingEntity, Long> {
    Boolean existsByMemberEntityIdAndChatroomEntityId(Long id, Long chatroomId);
    void deleteByMemberEntityIdAndChatroomEntityId(Long id, Long chatroomId);
    List<ChatroomMappingEntity> findAllByMemberEntityId(Long id);
    Optional<ChatroomMappingEntity> findByMemberEntityIdAndChatroomEntityId(Long id , Long chatroomId);
}
