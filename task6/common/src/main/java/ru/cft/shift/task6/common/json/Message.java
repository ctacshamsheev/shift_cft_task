package ru.cft.shift.task6.common.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
    private MessageType type;
    private String sender;
    private String message;
    private List<String> users = new ArrayList<>();

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    public static class MessageBuilder {
        private MessageType type;
        private String sender;
        private String message;
        private List<String> users;

        MessageBuilder() {
        }

        public MessageBuilder type(MessageType type) {
            this.type = type;
            return this;
        }

        public MessageBuilder sender(String sender) {
            this.sender = sender;
            return this;
        }

        public MessageBuilder message(String message) {
            this.message = message;
            return this;
        }

        public MessageBuilder users(List<String> users) {
            this.users = users;
            return this;
        }

        public Message build() {
            return new Message(type, sender, message, users);
        }

        public String toString() {
            return "Message.MessageBuilder(type=" + this.type + ", sender=" + this.sender + ", message=" + this.message + ", users=" + this.users + ")";
        }
    }
}