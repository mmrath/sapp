package com.mmrath.sapp;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    // message summery
    private String text;

    // detailed message, may contain further details about the message
    private String detail;

    private String field;

    private Severity severity;

    public Message() {
    }

    public Message(Severity severity, String message) {
        this.severity = severity;
        this.text = message;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
