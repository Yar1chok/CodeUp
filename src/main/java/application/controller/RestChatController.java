package application.controller;

import application.entity.ChatMessage;
import application.entity.ChatRoom;
import application.service.ChatMessageService;
import application.service.ChatRoomService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/communication")
public class RestChatController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    public RestChatController(ChatRoomService chatRoomService, ChatMessageService chatMessageService){
        this.chatRoomService = chatRoomService;
        this.chatMessageService = chatMessageService;
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<String> getRoomBySenderIdRecipientId(@PathVariable Long roomId) {
        ChatRoom chatRoom = chatRoomService.findByRoomId(roomId);
        if (chatRoom != null) {
            return new ResponseEntity<>(makeJSONRoom(chatRoom).toString(), HttpStatus.OK);
        }
        ChatRoom chatRoomFound = chatRoomService.create();
        return new ResponseEntity<>(makeJSONRoom(chatRoomFound).toString(), HttpStatus.OK);
    }


    private JSONObject makeJSONRoom(ChatRoom chatRoomFound){
        JSONObject roomJson = new JSONObject();
        roomJson.put("roomId", chatRoomFound.getRoomId());
        JSONArray messagesArray = new JSONArray();
        List<ChatMessage> chatMessageList = chatRoomFound.getMessages();
        List<ChatMessage> messagesForDelete = new ArrayList<>();
        for (ChatMessage message : chatMessageList) {
            if (message.getTimestamp().isAfter(LocalDateTime.now().minusDays(1))) {
                JSONObject messageJson = new JSONObject();
                messageJson.put("senderEmail", message.getSenderEmail());
                messageJson.put("content", message.getContent());
                messagesArray.put(messageJson);
            } else {
                messagesForDelete.add(message);
            }
        }
        if (!messagesForDelete.isEmpty()){
            for (ChatMessage message: messagesForDelete) {
                List<ChatMessage> newChatMessageList = chatRoomFound.getMessages();
                newChatMessageList.remove(message);
                chatRoomFound.setMessages(newChatMessageList);
                chatMessageService.deleteById(message.getMessageId());
            }
            chatRoomService.save(chatRoomFound);
        }
        roomJson.put("messages", messagesArray);
        return roomJson;
    }
}
