package application.controller;

import application.entity.ChatMessage;
import application.service.ChatMessageService;
import org.json.JSONObject;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    private final ChatMessageService chatMessageService;

    public ChatController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    private JSONObject makeJSONMessage(ChatMessage chatMessage){
        JSONObject messageJson = new JSONObject();
        messageJson.put("messageId", chatMessage.getMessageId());
        messageJson.put("senderEmail", chatMessage.getSenderEmail());
        messageJson.put("content", chatMessage.getContent());
        messageJson.put("messageId", chatMessage.getTimestamp().toString());
        messageJson.put("roomId", chatMessage.getRoom().getRoomId());
        return messageJson;
    }
    @MessageMapping("/chat/sendMessage")
    public String sendMessage(@Payload ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessageService.save(chatMessage);
        System.out.println(chatMessage.getSenderEmail());
        System.out.println(chatMessage.getContent());
        return makeJSONMessage(chatMessage).toString();
    }
}
