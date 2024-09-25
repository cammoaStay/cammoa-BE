package com.cammoastay.zzon.chat.services;


import com.cammoastay.zzon.chat.customexception.MappingCustomException;
import com.cammoastay.zzon.chat.dtos.ChatroomDto;
import com.cammoastay.zzon.chat.entities.ChatroomEntity;
import com.cammoastay.zzon.chat.entities.ChatroomMappingEntity;
import com.cammoastay.zzon.chat.entities.MessageEntity;
import com.cammoastay.zzon.chat.repositories.ChatroomRepository;
import com.cammoastay.zzon.chat.repositories.MemberChatroomMappingRepository;
import com.cammoastay.zzon.chat.repositories.MessageMongoRepository;
import com.cammoastay.zzon.jointest.MemberEntity;
import com.cammoastay.zzon.jointest.SaveTestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ChatService {

    private final ChatroomRepository chatroomRepository;
    private final MemberChatroomMappingRepository memberChatroomMappingRepository;
    private final MessageMongoRepository messageMongoRepository;
    private final SaveTestRepository saveTestRepository;

// 반환된 채팅방 객체는 저장된 후 사용자에게 보여주기 위해 반환됩니다.
    public ChatroomEntity createChatroom(MemberEntity member, String title) {

        ChatroomEntity chatroomEntity = ChatroomEntity.builder()
                .title(title)
                .createAt(LocalDateTime.now())
                .build();

        chatroomEntity = chatroomRepository.save(chatroomEntity);

        // 채팅방에 멤버 추가
        ChatroomMappingEntity chatroomMappingEntity = chatroomEntity.addMember(member);
        chatroomMappingEntity = memberChatroomMappingRepository.save(chatroomMappingEntity);

        return chatroomEntity;
    }

    // 사용자가 chatroom을 입장했을떄 아래 메서드 발생
    // 어떠한 데이터를 반환하기보다는 입장과 입장실패를 위해 boolean 값으로 반환하여 성공실패를 반환
    public Boolean joinChatroom(MemberEntity memberEntity, Long newChatroomId, Long currentChatroomId) {
        if (currentChatroomId != null) {
            updateLastCheckedAt(memberEntity, currentChatroomId);
        }
        // 사용자가 현재 참여중인 채팅방의 ID 가 null이 아닌 경우
        // 해당 채팅방에서 사용자가 마지막으로 확인한 시간을 업데이트 하는 메서드를 호출
        if (memberChatroomMappingRepository.existsByMemberEntityIdAndChatroomEntityId(memberEntity.getId(), newChatroomId)) {
            log.info("이미 참여한 채팅방입니다.");
            return false;
        }
        // 매핑된채팅방에서 아이디와 새로운 chatid를 확인하여 이미 참여한채팅방인지 확인
        ChatroomEntity chatroomEntity = chatroomRepository.findById(newChatroomId).get();
        // Optional은 값이 존재할 수도 있고, 존재하지 않을 수도 있으므로,
        // get() 메서드를 사용하여 실제 Chatroom 객체를 가져옵니다.
        // 그러나 실제 코드에서는 get()을 바로 사용하기보다는, Optional이 비어있는 경우를 처리하는 것이 더 안전합니다.
        ChatroomMappingEntity chatroomMappingEntity = ChatroomMappingEntity.builder()
                .memberEntity(memberEntity)
                .chatroomEntity(chatroomEntity)
                .build();
        chatroomMappingEntity = memberChatroomMappingRepository.save(chatroomMappingEntity);
        // 새로운 사용자를 해당 채팅방에 매핑하여 매핑 정보를 저장하고, 입장이 성공했음을 의미하는 true를 반환합니다.
        return true;
    }

  private void updateLastCheckedAt(MemberEntity memberEntity, Long currentChatroomId) {
       if (currentChatroomId == null) {
           // currentChatroomId가 null인 경우 재차확인
           log.warn("currentChatroomId is null. Skipping updateLastCheckedAt.");
           return;
       }
        ChatroomMappingEntity chatroomMappingEntity = memberChatroomMappingRepository.findByMemberEntityIdAndChatroomEntityId(memberEntity.getId(), currentChatroomId)
                .orElseThrow(() -> new MappingCustomException("An error occurred while processing the request."));
        // 마지막 확인 시간을 업데이트, 해당되는 데이터없을시 customException 발생
        chatroomMappingEntity.updateLastCheckedAt();
        // 변경 사항을 저장
        memberChatroomMappingRepository.save(chatroomMappingEntity);
        // 특정사용자 채팅방 마지막확인시간 저장, 활용하여 새로운 메시지 알림 기능구현가능
    }
    // 트랜잭션적용하여 삭제작업중 문제발생하면 자동으로 롤백
    public Boolean leaveChatroom(MemberEntity memberEntity, Long chatroomId) {
        if(!memberChatroomMappingRepository.existsByMemberEntityIdAndChatroomEntityId(memberEntity.getId(), chatroomId)) {
            log.info("참여하지 않은 방입니다.");
            return false;
        }
        // 2번 쿼리실행으로 인한 개선 방향성
        memberChatroomMappingRepository.deleteByMemberEntityIdAndChatroomEntityId(memberEntity.getId(), chatroomId);
        return true;
        // 하나의쿼리로 개선 , 아무것도 삭제하지않으면 false 하는 구조로 변경 , 조회결과를 바로삭제 하는방법
    }

    @Transactional(readOnly = true)
    public List<ChatroomEntity> getChatroomList(MemberEntity memberEntity){
        List<ChatroomMappingEntity> chatroomMappingEntityList = memberChatroomMappingRepository.findAllByMemberEntityId(memberEntity.getId());

        return chatroomMappingEntityList.stream()
                .map(chatroomMappingEntity -> {
                    ChatroomEntity chatroomEntity = chatroomMappingEntity.getChatroomEntity();
                    chatroomEntity.setHasNewMessage(messageMongoRepository.existsByChatroomIdAndCreatedAtAfter(String.valueOf(chatroomEntity.getId()), chatroomMappingEntity.getLastCheckedAt()
                        )
                    );
                    return chatroomEntity;
                })
                .toList();
        // 접속한 id가 입장하고있는 모든 연관된 채팅방 정보를 모두 리스트로 불러와 저장합니다.
    }

    public MessageEntity saveMessage(Optional<MemberEntity> optionalMemberEntity, Long chatroomId, String text) {
        ChatroomEntity chatroomEntity = chatroomRepository.findById(chatroomId).get();

        MemberEntity memberEntity = optionalMemberEntity
                .orElseThrow(() -> new IllegalArgumentException("MemberEntity is missing"));

        MessageEntity message = MessageEntity.builder()
                .text(text)
                .nickName(memberEntity.getNickName())
                .chatroomId(String.valueOf(chatroomEntity.getId()))
                .createdAt(LocalDateTime.now())
                .build();
                return messageMongoRepository.save(message);

    }

    @Transactional(readOnly = true)
   public List<MessageEntity> getMessageList(String chatroomId) {
        return messageMongoRepository.findAllByChatroomId(chatroomId);
   }
    @Transactional(readOnly = true)
    public ChatroomDto getChatroom(Long chatroomId) {
        ChatroomEntity chatroomEntity = chatroomRepository.findById(chatroomId).get();
        return ChatroomDto.from(chatroomEntity);
   }

    public MemberEntity findByUserId(String username) {
        return saveTestRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException ("User not found with username: " + username));
    }
}
