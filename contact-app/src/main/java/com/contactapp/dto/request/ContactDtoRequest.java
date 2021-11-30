package com.contactapp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactDtoRequest {

    @JsonProperty(value = "uuid")
    private UUID id;

    @NotBlank(message = "name cannot be empty")
    @JsonProperty(value = "name")
    private String name;

    @NotBlank(message = "phone cannot be empty")
    @JsonProperty(value = "phone")
    private String phone;
}
