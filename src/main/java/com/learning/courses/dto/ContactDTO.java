package com.learning.courses.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Valid
public class ContactDTO implements Serializable {
    private String email;
    private String address;
    private String phoneNr;
}
