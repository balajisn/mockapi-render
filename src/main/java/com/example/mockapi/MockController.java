package com.example.mockapi;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockController {

    private static final Logger log = LoggerFactory.getLogger(MockController.class);

    @PostMapping("/create")
    public ResponseEntity<String> create(
            @RequestHeader Map<String, String> headers,
            @RequestBody(required = false) String body) {

        log.info("========== RECEIVED POST /create ==========");
        log.info("Headers: {}", headers);
        log.info("Body: {}", body);

        return ResponseEntity.ok("Received /create");
    }

    @PostMapping("/create/attachments")
    public ResponseEntity<String> createAttachments(
            @RequestHeader Map<String, String> headers,
            @RequestBody(required = false) String body) {

        log.info("========== RECEIVED POST /create/attachments ==========");
        log.info("Headers: {}", headers);
        log.info("Body: {}", body);

        return ResponseEntity.ok("Received /create/attachments");
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("UP");
    }
}