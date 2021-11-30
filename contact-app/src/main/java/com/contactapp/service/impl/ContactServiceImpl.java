package com.contactapp.service.impl;

import com.contactapp.dto.request.ContactDtoRequest;
import com.contactapp.dto.response.ContactDtoResponse;
import com.contactapp.dto.response.ResponseStatus;
import com.contactapp.entity.ContactEntity;
import com.contactapp.repository.ContactRepository;
import com.contactapp.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    public static final String FAILED = "failed";

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public ResponseEntity<ContactDtoResponse> create(ContactDtoRequest dto) {
        try {
            ContactEntity entity = ContactEntity.builder().name(dto.getName()).phone(dto.getPhone()).build();
            ContactEntity saved = contactRepository.save(entity);
            ContactDtoResponse response = ContactDtoResponse.builder().id(saved.getId()).name(saved.getName())
                    .phone(saved.getPhone()).operationStatus("success").build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error in creation");
            return new ResponseEntity<>(ContactDtoResponse.builder().operationStatus(FAILED).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ContactDtoResponse> update(ContactDtoRequest dto) {
        try {
            Optional<ContactEntity> optional = contactRepository.findById(dto.getId());
            if (optional.isPresent()) {
                ContactEntity fromDb = optional.get();
                fromDb.setName(dto.getName());
                fromDb.setPhone(dto.getPhone());
                ContactEntity saved = contactRepository.save(fromDb);
                ContactDtoResponse response = ContactDtoResponse.builder().name(saved.getName())
                        .phone(saved.getPhone()).operationStatus("success").build();
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(ContactDtoResponse.builder().operationStatus(FAILED).build(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("error in creation");
            return new ResponseEntity<>(ContactDtoResponse.builder().operationStatus(FAILED).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<ContactEntity>> getAll() {
        try {
            return new ResponseEntity<>(contactRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ContactDtoResponse> delete(UUID id) {
        try {
            Optional<ContactEntity> optional = contactRepository.findById(id);
            ContactEntity fromDb = null;
            ContactDtoResponse response = null;
            if (optional.isPresent()) {
                fromDb = optional.get();
                response = ContactDtoResponse.builder().id(fromDb.getId()).name(fromDb.getName()).phone(fromDb.getPhone()).build();
            }
            optional.ifPresent(c -> contactRepository.deleteById(id));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseStatus> getStatus() {
        if (getAll().getStatusCode().equals(HttpStatus.OK)) {
            return new ResponseEntity<>(ResponseStatus.builder().status(HttpStatus.OK.toString()).build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResponseStatus.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.toString()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
