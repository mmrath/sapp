package com.mmrath.sapp;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MessageCollection implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<Message> messages;

  public MessageCollection() {
    messages = new ArrayList<>();
  }

  public List<Message> getMessages() {
    return messages;
  }

  public void info(String message, Object... params) {
    addMessage(Severity.INFO, message, params);
  }

  public void info(Message message, Object... params) {
    message.setSeverity(Severity.INFO);
    messages.add(message);
  }


  public void warn(String message, Object... params) {
    addMessage(Severity.WARN, message, params);
  }

  public void warn(Message message) {
    message.setSeverity(Severity.WARN);
    messages.add(message);
  }

  public void error(String message, Object... params) {
    addMessage(Severity.ERROR, message, params);
  }

  public void error(Message message) {
    message.setSeverity(Severity.ERROR);
    messages.add(message);
  }


  public void fetal(String message, Object... params) {
    addMessage(Severity.FETAL, message, params);
  }

  public void fetal(Message message) {
    message.setSeverity(Severity.FETAL);
    messages.add(message);
    ;
  }

  public void clearMessages() {
    messages.clear();
  }

  private void addMessage(Severity severity, String message, Object[] params) {
    MessageFormat temp = new MessageFormat(message);
    String formattedMessage = temp.format(params);
    Message messageObj = new Message(severity, formattedMessage);
    messages.add(messageObj);
  }

}
