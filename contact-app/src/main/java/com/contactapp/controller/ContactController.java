package com.contactapp.controller;

import com.contactapp.dto.request.ContactDtoRequest;
import com.contactapp.dto.response.ContactDtoResponse;
import com.contactapp.entity.ContactEntity;
import com.contactapp.service.ContactService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/user", name = "User CRUD")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;


    }

    @PostMapping(path = "/add")
    public ResponseEntity<ContactDtoResponse> userAdd(@RequestBody ContactDtoRequest dto) {
        return contactService.create(dto);
    }

    @PostMapping(path = "/edit")
    public ResponseEntity<ContactDtoResponse> userEdit(@RequestBody ContactDtoRequest dto) {
        return contactService.update(dto);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<ContactDtoResponse> userDelete(@RequestParam UUID id) {
        return contactService.delete(id);
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<ContactEntity>> userAll() {
        return contactService.getAll();
    }
}
