package com.learning.courses.service;

import com.learning.courses.dto.*;
import com.learning.courses.exception.EntityNotFoundException;
import com.learning.courses.exception.InvalidRoleException;
import com.learning.courses.mapper.PersonMapper;
import com.learning.courses.model.Contact;
import com.learning.courses.model.Person;
import com.learning.courses.model.enums.Role;
import com.learning.courses.repository.PersonRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

  private final PersonRepository personRepository;
  private final PersonMapper personMapper;

  @Transactional
  public Long createPerson(CreatePersonDTO createPersonDTO) {
    final Person person = personMapper.toEntity(createPersonDTO);

    return personRepository.save(person).getId();
  }

  @Transactional(readOnly = true)
  public PersonDTO getPerson(@NotNull @Positive Long id) {
    return personRepository.findById(id)
            .map(personMapper::toDTO)
            .orElseThrow(() -> new EntityNotFoundException(id, Person.class.getSimpleName()));
  }

  @Transactional(readOnly = true)
  public Person getPersonEntity(@NotNull @Positive Long id) {
    return personRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id, Person.class.getSimpleName()));
  }

  @Transactional
  public PersonDTO updatePerson(Long id, PersonDTO updatedPerson) {
    var person = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Person.class.getSimpleName()));
    person.setRole(updatedPerson.getRole());
    person.setFirstName(updatedPerson.getFirstName());
    person.setLastName(updatedPerson.getLastName());
    person.setIdentityNumber(updatedPerson.getIdentityNumber());
    person = personRepository.save(person);
    return personMapper.toDTO(person);
  }

  @Transactional
  public void addContact(CreateContactDTO createContactDTO) {
    var person = personRepository.findById(createContactDTO.getPerson_id()).orElseThrow(() -> new EntityNotFoundException(createContactDTO.getPerson_id(), Person.class.getSimpleName()));
    if(person.getRole() != Role.STUDENT) {
      throw new InvalidRoleException(person.getId(), Role.STUDENT, person.getRole());
    }
    var contact = Contact.builder()
            .email(createContactDTO.getEmail())
            .address(createContactDTO.getAddress())
            .phoneNr(createContactDTO.getPhoneNr())
            .build();
    person.getContacts().add(contact);
    personRepository.save(person);
  }

  @Transactional(readOnly = true)
  public List<ContactDTO> getStudentContacts(@NotNull @Positive Long id)
  {
    var person = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Person.class.getSimpleName()));
    ArrayList<ContactDTO> contactDTOS = new ArrayList<ContactDTO>();
    for(Contact c: person.getContacts()) {
      var contactDTO = ContactDTO.builder()
              .address(c.getAddress())
              .email(c.getEmail())
              .phoneNr(c.getPhoneNr())
              .build();
      contactDTOS.add(contactDTO);
    }
    return contactDTOS;
  }
}
