package application.controller;

import application.entity.ChatMessage;
import application.entity.ChatRoom;
import application.entity.Gamer;
import application.service.ChatMessageService;
import application.service.ChatRoomService;
import application.service.GamerService;
import org.json.JSONObject;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Set;

@Controller
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final GamerService gamerService;

    public ChatController(ChatMessageService chatMessageService, ChatRoomService chatRoomService, GamerService gamerService) {
        this.chatMessageService = chatMessageService;
        this.chatRoomService = chatRoomService;
        this.gamerService = gamerService;
    }

    private JSONObject makeJSONMessage(ChatMessage chatMessage){
        JSONObject messageJson = new JSONObject();
        messageJson.put("messageId", chatMessage.getMessageId());
        messageJson.put("senderEmail", chatMessage.getSenderEmail());
        messageJson.put("content", chatMessage.getContent());
        messageJson.put("messageTime", chatMessage.getTimestamp().toString());
        return messageJson;
    }
    @MessageMapping("/chat/sendMessage/{roomId}")
    public String sendMessage(@Payload ChatMessage chatMessage, @DestinationVariable Long roomId) {
        chatMessage.setTimestamp(LocalDateTime.now());
        ChatRoom chatRoom = chatRoomService.findByRoomId(roomId);
        Set<Gamer> gamerSet = chatRoom.getGamers();
        Gamer gamer = gamerService.findGamerByEmail(chatMessage.getSenderEmail());
        if (!gamerSet.contains(gamer)) {
            gamerSet.add(gamer);
            chatRoom.setGamers(gamerSet);
            chatRoomService.save(chatRoom);
        }
        chatMessage.setRoom(chatRoom);
        chatMessageService.save(chatMessage);
        System.out.println(chatMessage.getSenderEmail());
        System.out.println(chatMessage.getContent());
        return makeJSONMessage(chatMessage).toString();
    }
}
