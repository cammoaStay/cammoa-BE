package com.cammoastay.zzon.chat.repositories;

import com.cammoastay.zzon.chat.entities.ChatroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomRepository extends JpaRepository <ChatroomEntity, Long> {

}
