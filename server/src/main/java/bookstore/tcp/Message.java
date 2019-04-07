package bookstore.tcp;

import java.io.*;

public class Message implements Serializable{
    public static final String OK = "ok";
    public static final String ERROR = "error";

    private static final String LINE_SEPARATOR =
            System.getProperty("line.separator");

    private String header;
    private String body;
    private Object entity;

    private Message() {
    }

    private Message(String header, String body) {
        this.header = header;
        this.body = body;
        this.entity = null;
    }

    private Message(String header, String body, Object entity) {
        this.header = header;
        this.body = body;
        this.entity = entity;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public Object getEntity() {
        return entity;
    }

    @Override
    public String toString() {
        return "Message{" +
                "header='" + header + '\'' +
                ", body='" + body + '\'' +
                ", entity='" + entity + "\'" +
                '}';
    }

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    public static class MessageBuilder {
        private Message message;

        MessageBuilder() {
            message = new Message();
        }

        public MessageBuilder header(String header) {
            this.message.header = header;
            return this;
        }

        public MessageBuilder body(String body) {
            this.message.body = body;
            return this;
        }

        public MessageBuilder entity(Object entity){
            this.message.entity = entity;
            return this;
        }

        public Message build() {
            return this.message;
        }
    }
}
