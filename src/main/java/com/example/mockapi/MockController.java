package com.example.mockapi;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class MockController
{
    private static final Logger log = LoggerFactory.getLogger(MockController.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody(required = false)
    String body)
    {
        log.info("========== START POST /create ==========");
        try
        {
            if (body != null && !body.isBlank())
            {
                JsonNode jsonNode = objectMapper.readTree(body);
                String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
                log.info("Body:\n{}", prettyJson);
            }
            else
            {
                log.info("Body is empty");
            }
        }
        catch (Exception e)
        {
            log.info("Body is not valid JSON. Raw body: {}", body);
        }
        log.info("========== END POST /create ==========");
        return ResponseEntity.ok("Received /create");
    }

    @PostMapping(value = "/create/attachments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createAttachments(HttpServletRequest request, @RequestParam(required = false)
    Map<String, String> formFields, @RequestParam(required = false)
    MultiValueMap<String, MultipartFile> fileMap)
    {
        log.info("========== START POST /create/attachments ==========");
        log.info("Content-Length: {}", request.getContentLengthLong());
        if (formFields != null && !formFields.isEmpty())
        {
            log.info("Form fields count: {}", formFields.size());
            formFields.forEach((key, value) -> log.info("Form field: {} = {}", key, value));
        }
        else
        {
            log.info("No form fields received");
        }
        if (fileMap != null && !fileMap.isEmpty())
        {
            AtomicInteger totalFiles = new AtomicInteger();
            fileMap.forEach((fieldName, files) -> {
                for (MultipartFile file : files)
                {
                    totalFiles.incrementAndGet();
                    log.info("Multipart field: {}, file name: {}, size: {} bytes, content type: {}, empty: {}", fieldName,
                            file.getOriginalFilename(), file.getSize(), file.getContentType(), file.isEmpty());
                }
            });
            log.info("Total files received: {}", totalFiles.get());
        }
        else
        {
            log.info("No attachment files received");
        }
        log.info("========== END POST /create/attachments ==========");
        return ResponseEntity.ok("Received /create/attachments");
    }

    @GetMapping("/health")
    public ResponseEntity<String> health()
    {
        log.info("========== START GET /health ==========: " + new Date());
        return ResponseEntity.ok("UP");
    }
}
