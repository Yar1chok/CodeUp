package application.service;

import application.entity.ChatRoom;
import application.entity.Gamer;
import application.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @Transactional
    public void save(ChatRoom room){
        chatRoomRepository.save(room);
    }

    public List<ChatRoom> findAll(){
        return chatRoomRepository.findAll();
    }
    public ChatRoom findByRoomId(Long id){
        return chatRoomRepository.findByRoomId(id);
    }

    public ChatRoom create() {
        ChatRoom chatRoom = new ChatRoom();
        chatRoomRepository.save(chatRoom);
        return chatRoomRepository.findFirstByOrderByRoomIdDesc();
    }
}
