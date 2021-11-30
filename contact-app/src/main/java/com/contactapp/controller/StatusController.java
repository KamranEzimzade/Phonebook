package com.contactapp.controller;

import com.contactapp.dto.response.ResponseStatus;
import com.contactapp.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    private final ContactService contactService;

    public StatusController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping(path = "/status")
    public ResponseEntity<ResponseStatus> getStatus() {
        return contactService.getStatus();
    }
}
