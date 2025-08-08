package com.descodeuses.planit.entity;

import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "LogDocument")
public class LogDocument {
    @Id
    private String id;
    private String text;
    private String method;

    private Map<String, Object> extras = new HashMap<>();

    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    private String fileName;
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    private int lineNumber;
    public int getLineNumber() {
        return lineNumber;
    }
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
    private LocalDateTime timestamp;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }


    public Map<String, Object> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
    }

}
