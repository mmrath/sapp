package com.mmrath.sapp;

import java.util.List;

public class ApplicationRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private MessageCollection messageCollection;

    public ApplicationRuntimeException(MessageCollection messageCollection) {
        this.messageCollection = messageCollection;
    }

    public List<Message> getMessages() {
        return messageCollection.getMessages();
    }
}
