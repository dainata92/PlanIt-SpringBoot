package com.descodeuses.planit.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.descodeuses.planit.entity.LogDocument;
import com.descodeuses.planit.repository.LogDocumentRepository;

@Service
public class LogDocumentService {
    
    @Autowired
    private LogDocumentRepository repo;
    public void addLog(String text, LocalDate timestamp,String username){
    
    LogDocument doc = new LogDocument();
    Map<String, Object> extras = new HashMap<>();
    extras.put("username", username);
    doc.setExtras(extras);
    doc.setText(text);
    doc.setTimestamp(timestamp);
    repo.save(doc);
}
}
