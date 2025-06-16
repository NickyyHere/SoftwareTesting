package com.learning.courses.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Valid
public class CreateContactDTO implements Serializable {
    @NotBlank
    private Long person_id;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String address;
    @NotNull
    @Length(min = 6, max = 15)
    private String phoneNr;
}
