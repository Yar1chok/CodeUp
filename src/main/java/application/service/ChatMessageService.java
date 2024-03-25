package application.service;

import application.entity.ChatMessage;
import application.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public void save(ChatMessage message){
        chatMessageRepository.save(message);
    }

    public void deleteById(Long id){
        chatMessageRepository.deleteByMessageId(id);
    }
}
