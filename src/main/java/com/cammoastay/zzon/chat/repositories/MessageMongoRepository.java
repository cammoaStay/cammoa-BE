package com.cammoastay.zzon.chat.repositories;

import com.cammoastay.zzon.chat.entities.MessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageMongoRepository extends MongoRepository<MessageEntity, String> {

    List<MessageEntity> findAllByChatroomId(String chatroomId);
    Boolean existsByChatroomIdAndCreatedAtAfter(String chatroomId, LocalDateTime createdAt);
    // 몽고db에서는 Long 타입값이 없어 id값을 String 으로관리한다.
}
