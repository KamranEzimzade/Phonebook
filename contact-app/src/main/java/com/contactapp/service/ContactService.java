package com.contactapp.service;

import com.contactapp.dto.request.ContactDtoRequest;
import com.contactapp.dto.response.ContactDtoResponse;
import com.contactapp.dto.response.ResponseStatus;
import com.contactapp.entity.ContactEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public interface ContactService {

    ResponseEntity<ContactDtoResponse> create(ContactDtoRequest dto);

    ResponseEntity<ContactDtoResponse> update(ContactDtoRequest dto);

    ResponseEntity<List<ContactEntity>> getAll();

    ResponseEntity<ContactDtoResponse> delete(UUID id);

    ResponseEntity<ResponseStatus> getStatus();
}
